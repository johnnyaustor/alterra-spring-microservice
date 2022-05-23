package com.example.order;

import com.example.order.client.CustomerClient;
import com.example.order.messaging.producers.OrderProducer;
import com.example.order.model.Customer;
import com.example.order.model.Order;
import com.example.order.model.OrderDTO;
import com.example.order.model.ServerInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.order.Data.orders;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final OrderAsyncService orderAsyncService;
    private final OrderProducer orderProducer;

    public List<Order> list() {
        return orders;
    }

    public List<Order> filter(Integer uid) {
        return orders.stream().filter(order -> Objects.equals(order.getCustomer().getId(), uid))
                .collect(Collectors.toList());
    }

    public ServerInfo getCustomerServerInfo() {
        ResponseEntity<ServerInfo> serverInfo = customerClient.getServerInfo();
        if (!serverInfo.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("failed get customer server info, response: " + serverInfo.getStatusCode());
        }
        return serverInfo.getBody();
    }

    public Order create(OrderDTO dto) throws InterruptedException, RuntimeException {
        log.info("action create order");
        // Validate
        ResponseEntity<Customer> customerResponseEntity = customerClient.get(dto.getUid());
        if (customerResponseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Customer Not Valid");
        }

        var order = new Order(dto.getName(), dto.getUid(), customerResponseEntity.getBody());
        log.info("order {}", order);
        orders.add(order);
        Thread.sleep(1_000);
        return order;
    }

    public Order createAsync(OrderDTO dto) throws InterruptedException, RuntimeException {
        // Validate
        log.info("action create order with async service");
        var order = new Order(dto.getName(), dto.getQty(), Order.OrderStatus.PENDING, new Customer(dto.getUid(), null));
        log.info("order {}", order);
        orders.add(order);

        // process async for validation customer
        orderAsyncService.validateCustomer(dto.getUid());
        Thread.sleep(1_000);
        return order;
    }

    public Order createAsyncMessaging(OrderDTO dto) throws InterruptedException, RuntimeException {
        // Validate
        log.info("action create order with async messaging");
        var order = new Order(dto.getName(), dto.getQty(), Order.OrderStatus.PENDING, new Customer(dto.getUid(), null));
        log.info("order {}", order);
        orders.add(order);

        // process async for validation customer
        orderProducer.sendValidateCustomer(dto.getUid());
        Thread.sleep(1_000);
        return order;
    }

}
