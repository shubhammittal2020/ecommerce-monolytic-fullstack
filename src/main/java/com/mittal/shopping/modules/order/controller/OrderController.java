package com.mittal.shopping.modules.order.controller;

import com.mittal.shopping.modules.order.dto.OrderResponse;
import com.mittal.shopping.modules.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@NotNull HttpServletRequest request) {

        String email = (String)request.getAttribute("email");

        OrderResponse response = orderService.createOrder(email);

        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders(@NotNull HttpServletRequest request) {

        String email = (String)request.getAttribute("email");

        List<OrderResponse> response = orderService.getMyOrder(email);

        return ResponseEntity.ok(response);

    }

}
