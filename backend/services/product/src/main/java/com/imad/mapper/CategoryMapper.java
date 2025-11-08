package com.imad.mapper;



import com.imad.dto.*;
import com.imad.entity.Category;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", defaultValue = "true")
    Category toEntity(CategoryCreateDTO dto);

    @Mapping(source = "productCount", target = "productCount")
    CategoryResponseDTO toResponseDTO(Category category);

    CategorySummaryDTO toSummaryDTO(Category category);

    List<CategoryResponseDTO> toResponseDTOList(List<Category> categories);

    List<CategorySummaryDTO> toSummaryDTOList(List<Category> categories);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(CategoryUpdateDTO dto, @MappingTarget Category category);
}