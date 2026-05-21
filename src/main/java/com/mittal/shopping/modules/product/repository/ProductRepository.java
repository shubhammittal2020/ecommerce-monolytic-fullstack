package com.mittal.shopping.modules.product.repository;

import com.mittal.shopping.modules.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    // Category filter
    List<Product> findByCategoryIgnoreCase(String category);

    // Search by title
    List<Product> findByTitleContainingIgnoreCase(String keyword);

    // Price range filter
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

}
