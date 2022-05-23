package com.example.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String name;
    private Integer qty;
    private OrderStatus status = OrderStatus.APPROVED;
    private Customer customer;

    public Order(String name, Integer qty, Customer customer) {
        this.name = name;
        this.qty = qty;
        this.customer = customer;
    }

    public enum OrderStatus {
        PENDING, APPROVED, REJECTED
    }
}
