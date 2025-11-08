package com.imad.mapper;



import com.imad.dto.*;
import com.imad.entity.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "reviewCount", constant = "0")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductCreateDTO dto);

    @Mapping(source = "category", target = "category")
    @Mapping(expression = "java(product.isLowStock())", target = "isLowStock")
    @Mapping(expression = "java(product.isOutOfStock())", target = "isOutOfStock")
    @Mapping(expression = "java(product.hasDiscount())", target = "hasDiscount")
    @Mapping(expression = "java(product.getEffectivePrice())", target = "effectivePrice")
    ProductResponseDTO toResponseDTO(Product product);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(expression = "java(getMainImage(product))", target = "mainImage")
    ProductSummaryDTO toSummaryDTO(Product product);

    List<ProductResponseDTO> toResponseDTOList(List<Product> products);

    List<ProductSummaryDTO> toSummaryDTOList(List<Product> products);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sku", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "reviewCount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(ProductUpdateDTO dto, @MappingTarget Product product);

    default String getMainImage(Product product) {
        return product.getImages() != null && !product.getImages().isEmpty()
                ? product.getImages().get(0)
                : null;
    }
}