package com.example.customer.messaging.producers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

private val log: Logger = LoggerFactory.getLogger(CustomerProducer::class.java)

@Component
class CustomerProducer(
    private val template: KafkaTemplate<String, Any>
) {

    fun sendResultValidateCustomer(value: Any) {
        send("customer.result-validate-customer", value)
    }

    fun send(topic: String, value: Any) {
        log.info("send message with topic: $topic and value: $value")
        template.send(topic, value)
    }
}