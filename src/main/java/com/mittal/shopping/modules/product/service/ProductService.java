package com.mittal.shopping.modules.product.service;

import com.mittal.shopping.common.exception.ResourceNotFoundException;
import com.mittal.shopping.modules.product.dto.ProductCreateRequest;
import com.mittal.shopping.modules.product.dto.ProductResponse;
import com.mittal.shopping.modules.product.dto.ProductUpdateRequest;
import com.mittal.shopping.modules.product.entity.Product;
import com.mittal.shopping.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    private ProductResponse mapToResponse(Product product) {

        ProductResponse response = ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .active(product.getActive())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();

        return response;

    }

    public String createProduct(ProductCreateRequest request) {

        Product product = Product.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .category(request.getCategory())
                .imageUrl(request.getImageUrl())
                .active(true)
                .build();

        productRepository.save(product);

        return "Product Created Successfully";

    }

    public ProductResponse getProductById(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product Not Found, ID: " + productId)
                );

        return mapToResponse(product);
    }

    public List<ProductResponse> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(this::mapToResponse)
                .toList();

    }

    public Page<ProductResponse> getAllProducts(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products = productRepository.findAll(pageable);

        return products.map(this::mapToResponse);

    }

    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product Not Found, ID: " + id)
                );

        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());

        Product updatedProduct = productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    public String deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product Not Found, ID: " + productId)
                );

        productRepository.delete(product);

        return "Product delete successfully";
    }

    public List<ProductResponse> getProductsByCategory(String category) {

        List<Product> products = productRepository.findByCategoryIgnoreCase(category);

        return products.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ProductResponse> searchProducts(String keyword) {
        List<Product> products = productRepository.findByTitleContainingIgnoreCase(keyword);

        return products.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ProductResponse> filterByPrice(BigDecimal min, BigDecimal max) {
        List<Product> products = productRepository.findByPriceBetween(min, max);

        return products.stream()
                .map(this::mapToResponse)
                .toList();
    }

}
