package com.mittal.shopping.modules.payment.entity;

import com.mittal.shopping.modules.order.entity.Order;
import com.mittal.shopping.modules.payment.enums.PaymentMethod;
import com.mittal.shopping.modules.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Positive
    private BigDecimal amount;

    //@Enumerated(EnumType.STRING)
    //private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String transactionId;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
