package com.mittal.shopping.modules.order.service;

import com.mittal.shopping.modules.cart.entity.Cart;
import com.mittal.shopping.modules.cart.entity.CartItem;
import com.mittal.shopping.modules.cart.repository.CartRepository;
import com.mittal.shopping.modules.order.dto.OrderItemResponse;
import com.mittal.shopping.modules.order.dto.OrderResponse;
import com.mittal.shopping.modules.order.entity.Order;
import com.mittal.shopping.modules.order.entity.OrderItem;
import com.mittal.shopping.modules.order.enums.OrderStatus;
import com.mittal.shopping.modules.order.repository.OrderRepository;
import com.mittal.shopping.modules.user.entity.User;
import com.mittal.shopping.modules.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse createOrder(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("No user found")
                );

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(
                        () -> new RuntimeException("No cart found for the user: " + user)
                );

        List<CartItem> cartItems = cart.getCartItems();

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getProduct().getPrice())
                    .build();

            totalAmount = totalAmount.add(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PLACED);

        Order savedOrder = orderRepository.save(order);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return mapToResponse(savedOrder);

    }

    public List<OrderResponse> getMyOrder(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("No user found")
                );

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream().map(this::mapToResponse).toList();

    }

    private OrderResponse mapToResponse(@NotNull Order order) {

        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItemResponses.add(mapToResponse(orderItem));
        }

        return OrderResponse.builder()
                .orderId(order.getId())
                .items(orderItemResponses)
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .build();

    }

    private OrderItemResponse mapToResponse(@NotNull OrderItem orderItem) {

        return OrderItemResponse.builder()
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getTitle())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .subTotal(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .build();

    }

}
