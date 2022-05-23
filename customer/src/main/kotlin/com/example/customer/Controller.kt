package com.example.customer

import com.example.customer.messaging.producers.CustomerProducer
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(
    private val customerService: CustomerService,
    private val customerProducer: CustomerProducer,
    private val env: Environment,
) {

    @GetMapping
    fun list() = ResponseEntity.ok(this.customerService.list())

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int) = try {
        ResponseEntity.ok(this.customerService.get(id))
    } catch (ex: Exception) {
        ResponseEntity.notFound().build()
    }

    @GetMapping("/server-info")
    fun getServerInfo(): ResponseEntity<ServerInfo> {
        return ResponseEntity.ok(
            ServerInfo(
                name = env.getProperty("spring.application.name"),
                port = env.getProperty("server.port", Int::class.java),
                contextPath = env.getProperty("server.servlet.context-path")
            )
        )
    }

    @GetMapping("/produce")
    fun produce(): ResponseEntity<Any> {
        customerProducer.sendResultValidateCustomer(Customer(1, "abc", "def"))
        return ResponseEntity.ok().build()
    }
}