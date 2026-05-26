package com.mittal.shopping.modules.order.repository;

import com.mittal.shopping.modules.order.entity.Order;
import com.mittal.shopping.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

}
