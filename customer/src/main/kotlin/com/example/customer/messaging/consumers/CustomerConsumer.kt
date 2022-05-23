package com.example.customer.messaging.consumers

import com.example.customer.Customer
import com.example.customer.customers
import com.example.customer.messaging.producers.CustomerProducer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private val log: Logger = LoggerFactory.getLogger(CustomerConsumer::class.java)

@Component
class CustomerConsumer(
    private val customerProducer: CustomerProducer
) {

    @KafkaListener(topics = ["order.validate-customer"], containerFactory = "intContainerFactory")
    fun listenValidateCustomer(uid: Int) {
        log.info("listen validate customer with uid: $uid")
        val customer = customers.find { customer -> customer.id == uid } ?: Customer(uid, "", "")
        log.info("customer: $customer")
        customerProducer.sendResultValidateCustomer(customer)
    }
}

