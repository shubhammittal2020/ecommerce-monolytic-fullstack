package com.mittal.shopping.modules.payment.repository;

import com.mittal.shopping.modules.order.entity.Order;
import com.mittal.shopping.modules.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrder(Order order);

}
