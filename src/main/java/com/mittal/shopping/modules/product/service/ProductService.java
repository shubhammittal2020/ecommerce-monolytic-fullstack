package com.mittal.shopping.modules.product.service;

import com.mittal.shopping.modules.product.dto.ProductCreateRequest;
import com.mittal.shopping.modules.product.dto.ProductResponse;
import com.mittal.shopping.modules.product.dto.ProductUpdateRequest;
import com.mittal.shopping.modules.product.entity.Product;
import com.mittal.shopping.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                        () -> new RuntimeException("Product Not Found")
                );

        return mapToResponse(product);
    }

    public List<ProductResponse> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(this::mapToResponse)
                .toList();

    }

    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Product Not Found")
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
                        () -> new RuntimeException("Product Not Found")
                );

        productRepository.delete(product);

        return "Product delete successfully";
    }

    public List<ProductResponse> getProductsByCategory(String category) {

        List<Product> products = productRepository.findByCategory(category);

        return products.stream()
                .map(this::mapToResponse)
                .toList();

    }

}
