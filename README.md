# Alterra Spring Microservice

ini adalah contoh simulasi menerapkan microservice menggunakan spring framework yang di selenggarakan oleh alterra academy.

## List Project

- Customer Service (Kotlin)
- Order Service (Java)
- Gateway Server
- Registry Server

## Required

membutuhkan kafka running di local. jika sudah ada docker di computer, bisa buka di folder `docs/docker/` 
lalu jalankan menggunakan docker-compose

```shell
docker-compose up -d
```

## Cara Menjalankan
1. Start `Registry Server` folder registry
2. Start semua service (customer, order, gateway)

## Endpoint

### Gateway

Host: http://localhost:8080

selebihnya mengikuti endpoint sesuai service dibawah

### Order

Host: http://localhost:7200

Method | URI | Desc
--- | --- | ---
GET | `/orders?uid={uid}` | Mengambil data order berdasarkan `User Id`
POST | `/orders/create` | Membuat data order baru
POST | `/orders/create-async` | Membuat data order baru menerapkan konsep async service
POST | `/orders/create-async-messaging` | Membuat data order baru menerapkan konsep async kafka

### Customer

Host: http://localhost:7100

Method | URI | Desc
--- | --- | ---
GET | `/customers` | Mengambil data list customer
GET | `/customers/{id}` | Mengambil data customer berdasarkan `customer id`
GET | `/customers/server-info` | Mengambil informasi service
