package com.example.order;

import com.example.order.client.CustomerClient;
import com.example.order.model.Customer;
import com.example.order.model.Order;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.example.order.Data.orders;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderAsyncService {

    private final CustomerClient customerClient;

    @Async
    public void validateCustomer(Integer uid) throws InterruptedException {
        log.info("action get customer with uid: {}", uid);
        try {
            ResponseEntity<Customer> customerResponseEntity = customerClient.get(uid);
            TimeUnit.SECONDS.sleep(10);
            if (!customerResponseEntity.getStatusCode().is2xxSuccessful()) {
                log.info("customer notfound");
                this.updateStatus(uid, Order.OrderStatus.REJECTED);
            } else {
                log.info("update status order");
                this.updateOrder(customerResponseEntity.getBody());
            }
        } catch (FeignException e) {
            log.error("order rejected! because internal server error", e);
            TimeUnit.SECONDS.sleep(5);
            this.updateStatus(uid, Order.OrderStatus.REJECTED);
        }
        log.info("end action get customer");
    }

    public void updateOrder(Customer customer) {
        orders.stream().filter(order ->
                        Objects.equals(order.getCustomer().getId(), customer.getId()) &&
                                order.getStatus() == Order.OrderStatus.PENDING)
                .findFirst().ifPresentOrElse(order -> {
                    order.setStatus(Order.OrderStatus.APPROVED);
                    order.setCustomer(customer);
                }, ()->{});
    }

    public void updateStatus(Integer uid, Order.OrderStatus status) {
        orders.stream().filter(order ->
                        Objects.equals(order.getCustomer().getId(), uid) &&
                                order.getStatus() == Order.OrderStatus.PENDING)
                .findFirst().ifPresentOrElse(order -> order.setStatus(status), ()->{});
    }
}
