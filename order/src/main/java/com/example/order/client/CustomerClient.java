package com.example.order.client;

import com.example.order.model.Customer;
import com.example.order.model.ServerInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "CUSTOMER",
        url = "http://localhost:7100"
)
public interface CustomerClient {

    @GetMapping("/customers/{id}")
    ResponseEntity<Customer> get(@PathVariable Integer id);

    @GetMapping("/customers/server-info")
    ResponseEntity<ServerInfo> getServerInfo();
}
