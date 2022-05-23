package com.example.customer.messaging.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.IntegerDeserializer
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer


@EnableKafka
@Configuration
class KafkaConfig

@Configuration
class KafkaConsumerConfig {

    @Value("\${spring.kafka.bootstrap-servers:localhost:9092}")
    val bootstrapServer = ""

    @Value("\${spring.kafka.consumer.group-id:learn-group}")
    val groupId = ""

    @Bean
    fun consumerFactory() = DefaultKafkaConsumerFactory<String, Any>(consumerConfig())

    @Bean
    fun stringConsumerFactory(): ConsumerFactory<String, String> {
        val props = consumerConfig()
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun intConsumerFactory(): ConsumerFactory<String, Int> {
        val props = consumerConfig()
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = IntegerDeserializer::class.java
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun intContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Int> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Int>()
        factory.consumerFactory = intConsumerFactory()
        return factory
    }

    fun consumerConfig(): MutableMap<String, Any> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServer
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        return props
    }
}

@Configuration
class KafkaProducerConfig {

    @Value("\${spring.kafka.bootstrap-servers:localhost:9092}")
    val bootstrapServer = ""

    @Bean
    fun kafkaTemplate() = KafkaTemplate(producerFactory())

    @Bean
    fun stringKafkaTemplate() = KafkaTemplate(stringProducerFactory())

    @Bean
    fun intKafkaTemplate() = KafkaTemplate(intProducerFactory())

    @Bean
    fun producerFactory() = DefaultKafkaProducerFactory<String, Any>(producerConfig())

    @Bean
    fun stringProducerFactory(): ProducerFactory<String, String> {
        val props = producerConfig()
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        return DefaultKafkaProducerFactory(props)
    }

    @Bean
    fun intProducerFactory(): ProducerFactory<String, Int> {
        val props = producerConfig()
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = IntegerSerializer::class.java
        return DefaultKafkaProducerFactory(props)
    }

    fun producerConfig() = mutableMapOf<String, Any>(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServer,
        ProducerConfig.LINGER_MS_CONFIG to 10,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
    )
}