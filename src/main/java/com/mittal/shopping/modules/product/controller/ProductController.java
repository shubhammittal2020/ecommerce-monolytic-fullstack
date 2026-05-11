package com.mittal.shopping.modules.product.controller;

import com.mittal.shopping.common.response.ApiResponse;
import com.mittal.shopping.modules.product.dto.ProductCreateRequest;
import com.mittal.shopping.modules.product.dto.ProductResponse;
import com.mittal.shopping.modules.product.dto.ProductUpdateRequest;
import com.mittal.shopping.modules.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);

        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();

        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        String result = productService.createProduct(request);

        return ResponseEntity.ok(
            new ApiResponse<>(
                    true,
                    "Product Created Successfully",
                    result
            )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest request) {
        ProductResponse result = productService.updateProduct(id, request);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProductById(@PathVariable Long id) {
        String result = productService.deleteProduct(id);

        return ResponseEntity.ok(
            new ApiResponse<>(
                    true,
                    "Product Deleted Successfully",
                    result
            )
        );
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable String category) {
        List<ProductResponse> products = productService.getProductsByCategory(category);

        return ResponseEntity.ok(products);
    }

}
