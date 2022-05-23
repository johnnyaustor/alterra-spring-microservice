package com.example.customer

data class Customer(
    val id: Int,
    val name: String,
    val email: String,
)

data class ServerInfo(
    val name: String?,
    val port: Int?,
    val contextPath: String?
)