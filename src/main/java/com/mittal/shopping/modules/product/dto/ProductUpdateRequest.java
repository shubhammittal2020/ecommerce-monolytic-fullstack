package com.mittal.shopping.modules.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {

    private String title;

    private String description;

    @Positive
    private BigDecimal price;

    @PositiveOrZero
    private Integer stockQuantity;

    private String category;

    private String imageUrl;

}
