package com.imad.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;


// DTO pour la réponse complète
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Boolean active;
    private Integer productCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}