package com.mittal.shopping.modules.payment.service;

import com.mittal.shopping.common.exception.BusinessException;
import com.mittal.shopping.common.exception.ResourceNotFoundException;
import com.mittal.shopping.modules.order.entity.Order;
import com.mittal.shopping.modules.order.enums.OrderStatus;
import com.mittal.shopping.modules.order.repository.OrderRepository;
import com.mittal.shopping.modules.payment.dto.PaymentResponse;
import com.mittal.shopping.modules.payment.entity.Payment;
import com.mittal.shopping.modules.payment.enums.PaymentStatus;
import com.mittal.shopping.modules.payment.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    public PaymentResponse getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("No payment found with the id: " + id)
                );

        return mapToResponse(payment);

    }

    public List<PaymentResponse> getAllPayments() {

        List<Payment> payments = paymentRepository.findAll();

        List<PaymentResponse> responses = new ArrayList<>();

        for(Payment payment : payments) {
            responses.add(mapToResponse(payment));
        }

        return responses;

    }

    @Transactional
    public PaymentResponse createPayment(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Order Not Found, ID: " + orderId)
                );

        Optional<Payment> existingPayment = paymentRepository.findByOrder(order);

        if (existingPayment.isPresent()) {
            throw new BusinessException("Payment already exists, Order ID: " + orderId);
        }

        Payment payment = Payment
                .builder()
                .order(order)
                .amount(order.getTotalAmount())
                //.paymentMethod()
                .paymentStatus(PaymentStatus.SUCCESS)
                .transactionId(UUID.randomUUID().toString())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        order.setStatus(OrderStatus.CONFIRMED);

        orderRepository.save(order);

        return mapToResponse(savedPayment);

    }


    public PaymentResponse mapToResponse(Payment payment) {

        return PaymentResponse
                .builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrder().getId())
                .amount(payment.getAmount())
                .paymentStatus(payment.getPaymentStatus())
                .transactionId(payment.getTransactionId())
                .message("Payment fetched successfully")
                .build();

    }

}
