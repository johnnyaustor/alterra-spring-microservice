package com.example.order;

import com.example.order.model.Customer;
import com.example.order.model.Order;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public static final List<Order> orders = new ArrayList<>();
    static {
        orders.add(new Order("laptop", 1,  new Customer(1, "angel@email.com")));
        orders.add(new Order("hp", 1, new Customer(1, "angel@email.com")));
        orders.add(new Order("meja", 1, new Customer(2, "berlin@email.com")));
        orders.add(new Order("kursi", 4, new Customer(2, "berlin@email.com")));
    }
}
