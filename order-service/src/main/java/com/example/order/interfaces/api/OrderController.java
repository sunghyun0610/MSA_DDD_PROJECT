package com.example.order.interfaces.api;

import com.example.order.application.OrderApplicationService;
import com.example.order.application.dto.PlaceOrderCommand;
import com.example.order.domain.model.Order;
import com.example.order.interfaces.api.dto.OrderResponse;
import com.example.order.interfaces.api.dto.PlaceOrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 표현 계층: HTTP 요청/응답 <-> 유스케이스 변환만 담당.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderApplicationService orderService;

    public OrderController(OrderApplicationService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder(@RequestBody PlaceOrderRequest request) {
        PlaceOrderCommand command = request.toCommand();
        Order order = orderService.placeOrder(command);
        return OrderResponse.from(order);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable String id) {
        return OrderResponse.from(orderService.getOrder(id));
    }
}
