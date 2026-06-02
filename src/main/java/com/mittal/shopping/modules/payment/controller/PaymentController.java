package com.mittal.shopping.modules.payment.controller;

import com.mittal.shopping.modules.payment.dto.PaymentResponse;
import com.mittal.shopping.modules.payment.service.PaymentService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long paymentId) {

        PaymentResponse response = paymentService.getPaymentById(paymentId);

        return ResponseEntity.ok(response);

    }

    @GetMapping()
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {

        List<PaymentResponse> response = paymentService.getAllPayments();

        return ResponseEntity.ok(response);

    }

    @PostMapping("/pay/{orderId}")
    public ResponseEntity<PaymentResponse> createPayment(@PathVariable Long orderId) {

        PaymentResponse response = paymentService.createPayment(orderId);

        return ResponseEntity.ok(response);

    }

}
