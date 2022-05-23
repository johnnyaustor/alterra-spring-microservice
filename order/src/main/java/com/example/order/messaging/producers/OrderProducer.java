package com.example.order.messaging.producers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, Integer> kafkaTemplate;

    public void sendValidateCustomer(Integer uid) {
        log.info("send message with uid: {}", uid);
        kafkaTemplate.send("order.validate-customer", uid)
                .addCallback(
                        result -> log.info("success sent message {}", result.getProducerRecord().topic()),
                        ex -> log.error("error sent message ", ex)
                );
    }
}
