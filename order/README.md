# Order Service
ini adalah contoh service mengimplement microservice architechture,
mengambil data dari customer service untuk memvalidasi customer, menggunakan `OpenFeignClient` sebagai http client.
lalu menerapkan juga Service Registry `Eureka`, 
dan mengimplement asynchronous process messaging pattern menggunakan `Kafka`

## Dependencies
- Customer Service
- Eureka Client
- Kafka
- Open Feign Client

## Endpoint

Host: http://localhost:7200

Method | URI | Desc
--- | --- | ---
GET | `/orders?uid={uid}` | Mengambil data order berdasarkan `User Id`
POST | `/orders/create` | Membuat data order baru
POST | `/orders/create-async` | Membuat data order baru menerapkan konsep async service
POST | `/orders/create-async-messaging` | Membuat data order baru menerapkan konsep async kafka
