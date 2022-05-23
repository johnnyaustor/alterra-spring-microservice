package com.example.order.messaging.consumers;

import com.example.order.OrderAsyncService;
import com.example.order.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderAsyncService orderAsyncService;

    @KafkaListener(topics = "customer.result-validate-customer", containerFactory = "customerContainerFactory")
    public void resultValidateCustomer(Customer customer) throws InterruptedException {
        log.info("result validate customer: {}", customer);
        Thread.sleep(10_000);

        orderAsyncService.updateOrder(customer);
    }
}
