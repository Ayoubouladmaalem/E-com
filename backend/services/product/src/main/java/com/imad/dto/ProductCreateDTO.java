package com.imad.dto;

import com.imad.enums.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// DTO pour la création d'un produit
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDTO {

    @NotBlank(message = "Le nom du produit est obligatoire")
    @Size(max = 200, message = "Le nom ne peut pas dépasser 200 caractères")
    private String name;

    @NotBlank(message = "La référence SKU est obligatoire")
    @Size(max = 50, message = "La référence SKU ne peut pas dépasser 50 caractères")
    private String sku;

    @Size(max = 2000, message = "La description ne peut pas dépasser 2000 caractères")
    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
    @Digits(integer = 10, fraction = 2, message = "Le prix doit avoir au maximum 10 chiffres entiers et 2 décimales")
    private BigDecimal price;

    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix réduit doit être supérieur à 0")
    @Digits(integer = 10, fraction = 2, message = "Le prix réduit doit avoir au maximum 10 chiffres entiers et 2 décimales")
    private BigDecimal discountPrice;

    @NotNull(message = "La quantité en stock est obligatoire")
    @Min(value = 0, message = "La quantité en stock ne peut pas être négative")
    private Integer stockQuantity;

    @Min(value = 0, message = "Le seuil de stock faible ne peut pas être négatif")
    private Integer lowStockThreshold;

    private ProductStatus status;

    private Long categoryId;

    @Size(max = 100, message = "La marque ne peut pas dépasser 100 caractères")
    private String brand;

    @Size(max = 100, message = "Le fournisseur ne peut pas dépasser 100 caractères")
    private String supplier;

    private List<String> images;
}
