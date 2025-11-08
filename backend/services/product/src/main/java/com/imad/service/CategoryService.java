package com.imad.service;



import com.imad.dto.*;
import com.imad.entity.Category;
import com.imad.exception.DuplicateResourceException;
import com.imad.exception.InvalidOperationException;
import com.imad.exception.ResourceNotFoundException;
import com.imad.mapper.CategoryMapper;
import com.imad.mapper.ProductMapper;
import com.imad.repository.CategoryRepository;
import com.imad.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;

    @Transactional
    public CategoryResponseDTO createCategory(CategoryCreateDTO dto) {
        log.info("Création de la catégorie: {}", dto.getName());

        // Vérifier si le nom existe déjà
        if (categoryRepository.existsByName(dto.getName())) {
            throw new DuplicateResourceException("Une catégorie avec le nom '" + dto.getName() + "' existe déjà");
        }

        Category category = categoryMapper.toEntity(dto);
        Category savedCategory = categoryRepository.save(category);

        log.info("Catégorie créée avec succès, ID: {}", savedCategory.getId());
        return categoryMapper.toResponseDTO(savedCategory);
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        log.info("Récupération de la catégorie avec ID: {}", id);
        Category category = findCategoryById(id);
        return categoryMapper.toResponseDTO(category);
    }

    public CategoryResponseDTO getCategoryByName(String name) {
        log.info("Récupération de la catégorie avec nom: {}", name);
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée avec le nom: " + name));
        return categoryMapper.toResponseDTO(category);
    }

    public Page<CategoryResponseDTO> getAllCategories(Pageable pageable) {
        log.info("Récupération de toutes les catégories, page: {}", pageable.getPageNumber());
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toResponseDTO);
    }

    public List<CategoryResponseDTO> getActiveCategories() {
        log.info("Récupération de toutes les catégories actives");
        return categoryMapper.toResponseDTOList(categoryRepository.findByActiveTrue());
    }

    public Page<CategoryResponseDTO> getCategoriesByStatus(Boolean active, Pageable pageable) {
        log.info("Récupération des catégories par statut actif: {}", active);
        return categoryRepository.findByActive(active, pageable)
                .map(categoryMapper::toResponseDTO);
    }

    public Page<ProductSummaryDTO> getProductsByCategory(Long categoryId, Pageable pageable) {
        log.info("Récupération des produits de la catégorie: {}", categoryId);

        // Vérifier que la catégorie existe
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Catégorie non trouvée avec l'ID: " + categoryId);
        }

        return productRepository.findByCategoryId(categoryId, pageable)
                .map(productMapper::toSummaryDTO);
    }

    public List<CategoryResponseDTO> getCategoriesOrderedByProductCount() {
        log.info("Récupération des catégories triées par nombre de produits");
        return categoryMapper.toResponseDTOList(categoryRepository.findAllOrderByProductCountDesc());
    }

    @Transactional
    public CategoryResponseDTO updateCategory(Long id, CategoryUpdateDTO dto) {
        log.info("Mise à jour de la catégorie avec ID: {}", id);

        Category category = findCategoryById(id);

        // Vérifier si le nouveau nom existe déjà (sauf pour cette catégorie)
        if (dto.getName() != null && !dto.getName().equals(category.getName())) {
            if (categoryRepository.existsByNameAndIdNot(dto.getName(), id)) {
                throw new DuplicateResourceException("Une catégorie avec le nom '" + dto.getName() + "' existe déjà");
            }
        }

        categoryMapper.updateEntityFromDTO(dto, category);
        Category updatedCategory = categoryRepository.save(category);

        log.info("Catégorie mise à jour avec succès, ID: {}", id);
        return categoryMapper.toResponseDTO(updatedCategory);
    }

    @Transactional
    public void deleteCategory(Long id) {
        log.info("Suppression de la catégorie avec ID: {}", id);

        Category category = findCategoryById(id);

        // Vérifier si la catégorie contient des produits
        long productCount = productRepository.countByCategoryId(id);
        if (productCount > 0) {
            throw new InvalidOperationException(
                    "Impossible de supprimer la catégorie car elle contient " + productCount + " produit(s). " +
                            "Veuillez d'abord supprimer ou déplacer les produits.");
        }

        categoryRepository.delete(category);
        log.info("Catégorie supprimée avec succès, ID: {}", id);
    }

    @Transactional
    public CategoryResponseDTO toggleCategoryStatus(Long id) {
        log.info("Basculement du statut de la catégorie: {}", id);

        Category category = findCategoryById(id);
        category.setActive(!category.getActive());
        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toResponseDTO(updatedCategory);
    }

    public long getProductCountByCategory(Long categoryId) {
        log.info("Comptage des produits de la catégorie: {}", categoryId);

        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Catégorie non trouvée avec l'ID: " + categoryId);
        }

        return productRepository.countByCategoryId(categoryId);
    }

    // Méthode utilitaire privée
    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée avec l'ID: " + id));
    }
}