package com.imad.controller;



import com.imad.dto.*;
import com.imad.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "API de gestion des catégories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Créer une nouvelle catégorie")
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryCreateDTO dto) {
        CategoryResponseDTO response = categoryService.createCategory(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une catégorie par ID")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        CategoryResponseDTO response = categoryService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Récupérer une catégorie par nom")
    public ResponseEntity<CategoryResponseDTO> getCategoryByName(@PathVariable String name) {
        CategoryResponseDTO response = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Récupérer toutes les catégories avec pagination")
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        Sort.Direction direction = sortDirection.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<CategoryResponseDTO> response = categoryService.getAllCategories(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    @Operation(summary = "Récupérer toutes les catégories actives")
    public ResponseEntity<List<CategoryResponseDTO>> getActiveCategories() {
        List<CategoryResponseDTO> response = categoryService.getActiveCategories();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    @Operation(summary = "Récupérer les catégories par statut")
    public ResponseEntity<Page<CategoryResponseDTO>> getCategoriesByStatus(
            @RequestParam Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryResponseDTO> response = categoryService.getCategoriesByStatus(active, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/products")
    @Operation(summary = "Récupérer les produits d'une catégorie")
    public ResponseEntity<Page<ProductSummaryDTO>> getProductsByCategory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductSummaryDTO> response = categoryService.getProductsByCategory(id, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ordered-by-products")
    @Operation(summary = "Récupérer les catégories triées par nombre de produits")
    public ResponseEntity<List<CategoryResponseDTO>> getCategoriesOrderedByProductCount() {
        List<CategoryResponseDTO> response = categoryService.getCategoriesOrderedByProductCount();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une catégorie")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryUpdateDTO dto) {
        CategoryResponseDTO response = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/toggle-status")
    @Operation(summary = "Basculer le statut actif/inactif d'une catégorie")
    public ResponseEntity<CategoryResponseDTO> toggleCategoryStatus(@PathVariable Long id) {
        CategoryResponseDTO response = categoryService.toggleCategoryStatus(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une catégorie")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/product-count")
    @Operation(summary = "Compter les produits d'une catégorie")
    public ResponseEntity<Long> getProductCountByCategory(@PathVariable Long id) {
        long count = categoryService.getProductCountByCategory(id);
        return ResponseEntity.ok(count);
    }
}