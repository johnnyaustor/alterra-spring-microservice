package com.example.order;

import com.example.order.model.Order;
import com.example.order.model.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> list(
            @RequestParam(value = "uid", required = false, defaultValue = "-1") Integer uid
    ) {
        if (uid < 0) {
            return ResponseEntity.ok(orderService.list());
        }
        return ResponseEntity.ok(orderService.filter(uid));
    }

    @GetMapping("/customer-server-info")
    public ResponseEntity<?> customerServerInfo() {
        try {
            return ResponseEntity.ok(orderService.getCustomerServerInfo());
        } catch (Exception ex) {
            log.error("error when get customer server info", ex);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody OrderDTO dto) {
        try {
            return ResponseEntity.ok(orderService.create(dto));
        } catch (Exception ex) {
            log.error("error when create order", ex);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create-async")
    public ResponseEntity<?> createAsync(@RequestBody OrderDTO dto) {
        try {
            return ResponseEntity.ok(orderService.createAsync(dto));
        } catch (Exception ex) {
            log.error("error when create order", ex);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create-async-messaging")
    public ResponseEntity<?> createAsyncMessaging(@RequestBody OrderDTO dto) {
        try {
            return ResponseEntity.ok(orderService.createAsyncMessaging(dto));
        } catch (Exception ex) {
            log.error("error when create order", ex);
            return ResponseEntity.internalServerError().build();
        }
    }
}
