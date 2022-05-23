package com.example.customer

import org.springframework.stereotype.Service
import java.lang.RuntimeException

val customers = mutableListOf(
    Customer(1, "Angel", "angel@email.com"),
    Customer(2, "Berlin", "berlin@email.com"),
)

@Service
class CustomerService {

    fun get(id: Int): Customer {
        Thread.sleep(2_000)
        return customers.firstOrNull { it.id == id } ?: throw RuntimeException("not found")
    }

    fun list(): List<Customer> {
        return customers
    }
}