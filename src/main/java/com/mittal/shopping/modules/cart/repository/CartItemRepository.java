package com.mittal.shopping.modules.cart.repository;

import com.mittal.shopping.modules.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<Cart, Long> {

}
