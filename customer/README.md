# Customer Service
ini adalah contoh service mengimplement microservice architechture,
menyediakan data customer service untuk memvalidasi order baru.
lalu menerapkan juga Service Registry `Eureka`, 
dan mengimplement asynchronous process messaging pattern menggunakan `Kafka`

## Dependencies
- Order Service
- Eureka Client
- Kafka

## Endpoint

Host: http://localhost:7100

Method | URI | Desc
--- | --- | ---
GET | `/customers` | Mengambil data list customer
GET | `/customers/{id}` | Mengambil data customer berdasarkan `customer id`
GET | `/customers/server-info` | Mengambil informasi service
