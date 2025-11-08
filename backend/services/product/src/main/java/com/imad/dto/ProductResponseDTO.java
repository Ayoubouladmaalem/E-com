package com.imad.dto;

import com.imad.enums.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// DTO pour la réponse
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String sku;
    private String description;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private BigDecimal effectivePrice;
    private Integer stockQuantity;
    private Integer lowStockThreshold;
    private ProductStatus status;
    private CategorySummaryDTO category;
    private String brand;
    private String supplier;
    private List<String> images;
    private BigDecimal rating;
    private Integer reviewCount;
    private Boolean isLowStock;
    private Boolean isOutOfStock;
    private Boolean hasDiscount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
