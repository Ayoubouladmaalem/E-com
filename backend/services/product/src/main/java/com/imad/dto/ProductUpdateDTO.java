package com.imad.dto;

import com.imad.enums.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


// DTO pour la mise à jour d'un produit
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDTO {

    @Size(max = 200, message = "Le nom ne peut pas dépasser 200 caractères")
    private String name;

    @Size(max = 2000, message = "La description ne peut pas dépasser 2000 caractères")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal discountPrice;

    @Min(value = 0, message = "La quantité en stock ne peut pas être négative")
    private Integer stockQuantity;

    @Min(value = 0)
    private Integer lowStockThreshold;

    private ProductStatus status;

    private Long categoryId;

    @Size(max = 100)
    private String brand;

    @Size(max = 100)
    private String supplier;

    private List<String> images;
}