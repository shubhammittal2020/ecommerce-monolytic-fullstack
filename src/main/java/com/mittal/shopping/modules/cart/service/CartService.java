package com.mittal.shopping.modules.cart.service;

import com.mittal.shopping.common.exception.ResourceNotFoundException;
import com.mittal.shopping.modules.cart.dto.AddToCartRequest;
import com.mittal.shopping.modules.cart.dto.CartItemResponse;
import com.mittal.shopping.modules.cart.dto.CartSummaryResponse;
import com.mittal.shopping.modules.cart.entity.Cart;
import com.mittal.shopping.modules.cart.entity.CartItem;
import com.mittal.shopping.modules.cart.repository.CartItemRepository;
import com.mittal.shopping.modules.cart.repository.CartRepository;
import com.mittal.shopping.modules.product.entity.Product;
import com.mittal.shopping.modules.product.repository.ProductRepository;
import com.mittal.shopping.modules.user.entity.User;
import com.mittal.shopping.modules.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {

    public final UserRepository userRepository;

    public final ProductRepository productRepository;

    public final CartRepository cartRepository;

    public final CartItemRepository cartItemRepository;

    public String addToCart(AddToCartRequest cartRequest, String email) {

        // Find User
        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );

        // Find Existing Cart
        Optional<Cart> existingCart = cartRepository.findByUser(user);

        // Find Product
        Product product = productRepository.findById(cartRequest.getProductId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product not found")
                );

        // If no cart exist
        if (existingCart.isEmpty()) {
            Cart cart = new Cart();

            cart.setUser(user);

            CartItem cartItem = new CartItem();

            cartItem.setProduct(product);
            cartItem.setQuantity(cartRequest.getQuantity());
            cartItem.setCart(cart);

            List<CartItem> cartItems = new ArrayList<>();
            cartItems.add(cartItem);
            cart.setCartItems(cartItems);

            cartRepository.save(cart);

            return "Product added to the new cart";
        }

        // If cart exist
        Cart cart = existingCart.get();
        List<CartItem> cartItems = cart.getCartItems();

        // If cart and product exist
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getId().equals(product.getId())) {
                cartItem.setQuantity(cartItem.getQuantity() + cartRequest.getQuantity());

                cartRepository.save(cart);

                return "Cart quantity updated";
            }
        }

        // If cart exist but product not found in cart
        CartItem newCartItem = new CartItem();

        newCartItem.setProduct(product);
        newCartItem.setQuantity(cartRequest.getQuantity());
        newCartItem.setCart(cart);

        cartItems.add(newCartItem);

        cartRepository.save(cart);

        return "New product added to the existing cart";
    }

    public CartSummaryResponse getCartByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Cart Not Found")
                );


        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Cart Not Found")
                );

        return mapToSummaryResponse(cart);
    }

    public List<CartSummaryResponse> getAllCarts() {

        List<Cart> carts = cartRepository.findAll();

        List<CartSummaryResponse> responses = new ArrayList<>();

        for (Cart cart : carts) {
            responses.add(mapToSummaryResponse(cart));
        }

        return responses;
    }

    public CartSummaryResponse updateQuantity(AddToCartRequest cartRequest, String email)  {
        // Find User
        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );

        // Find Existing Cart
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Cart not found")
                );

        // Find Product
        Product product = productRepository.findById(cartRequest.getProductId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product not found")
                );

        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getProduct().getId().equals(product.getId())) {
                cartItem.setQuantity(cartRequest.getQuantity());

                Cart updatedCart = cartRepository.save(cart);

                return mapToSummaryResponse(updatedCart);
            }
        }

        throw new RuntimeException(
            "Product not found in the cart"
        );

    }

    public String removeProductFromCart(Long cartId, Long productId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Cart not found")
                );

        List<CartItem> cartItems = cart.getCartItems();

        boolean removed = cartItems.removeIf(
                item -> item.getProduct()
                        .getId()
                        .equals(productId)
        );

        if (!removed) {
            throw new ResourceNotFoundException(
                    "Product not found in cart"
            );
        }

        cartRepository.save(cart);

        return "Product removed from cart";
    }

    private CartItemResponse mapToItemResponse(CartItem cartItem) {

        Product product = cartItem.getProduct();

        CartItemResponse response = CartItemResponse.builder()
                .productId(product.getId())
                .productName(product.getTitle())
                .price(product.getPrice())
                .quantity(cartItem.getQuantity())
                .subtotal(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .build();

        return response;
    }

    private CartSummaryResponse mapToSummaryResponse(Cart cart) {

        List<CartItem> cartItems = cart.getCartItems();

        List<CartItemResponse> cartItemResponses = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            CartItemResponse response = mapToItemResponse(cartItem);
            cartItemResponses.add(response);
            totalAmount = totalAmount.add(response.getSubtotal());
        }

        CartSummaryResponse response = new CartSummaryResponse();

        response.setItems(cartItemResponses);
        response.setTotalAmount(totalAmount);

        return response;
    }

}
