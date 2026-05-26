package com.mittal.shopping.modules.order.repository;

import com.mittal.shopping.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<User, Long> {

    //List<OrderItemRepository> findByUser(User user);

}
