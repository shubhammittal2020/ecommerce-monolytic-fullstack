package com.mittal.shopping.modules.cart.controller;

import com.mittal.shopping.modules.cart.dto.AddToCartRequest;
import com.mittal.shopping.modules.cart.dto.CartItemResponse;
import com.mittal.shopping.modules.cart.dto.CartSummaryResponse;
import com.mittal.shopping.modules.cart.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {

    public final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@Valid @RequestBody AddToCartRequest cartRequest, HttpServletRequest httpRequest) {
        String email = (String) httpRequest.getAttribute("email");

        String result = cartService.addToCart(cartRequest, email);

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<CartSummaryResponse> getMyCart(HttpServletRequest httpRequest) {
        String email = (String) httpRequest.getAttribute("email");

        CartSummaryResponse result = cartService.getCartByEmail(email);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CartSummaryResponse>> getAllCarts() {
        List<CartSummaryResponse> result = cartService.getAllCarts();

        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<CartSummaryResponse> updateCart(@Valid @RequestBody AddToCartRequest request, HttpServletRequest httpRequest) {
        String email = (String) httpRequest.getAttribute("email");

        CartSummaryResponse result = cartService.updateQuantity(request, email);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long cartId, @PathVariable Long product) {
        String message = cartService.removeProductFromCart(cartId, product);

        return ResponseEntity.ok(message);
    }

}
