package com.example.customer

import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

val customers = mutableListOf(
    Customer(1, "Angel", "angel@email.com"),
    Customer(2, "Berlin", "berlin@email.com"),
)

@Service
class CustomerService {

    fun get(id: Int): Customer {
        TimeUnit.SECONDS.sleep(2)
        return customers.firstOrNull { it.id == id } ?: throw RuntimeException("not found")
    }

    fun list(): List<Customer> {
        return customers
    }
}