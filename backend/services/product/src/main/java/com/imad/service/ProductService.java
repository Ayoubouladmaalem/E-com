package com.imad.service;



import com.imad.dto.*;
import com.imad.entity.Category;
import com.imad.entity.Product;
import com.imad.enums.ProductStatus;
import com.imad.exception.*;
import com.imad.mapper.ProductMapper;
import com.imad.repository.CategoryRepository;
import com.imad.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponseDTO createProduct(ProductCreateDTO dto) {
        log.info("Création du produit avec SKU: {}", dto.getSku());

        // Vérifier si le SKU existe déjà
        if (productRepository.existsBySku(dto.getSku())) {
            throw new DuplicateResourceException("Un produit avec le SKU '" + dto.getSku() + "' existe déjà");
        }

        // Valider le prix de réduction
        if (dto.getDiscountPrice() != null && dto.getDiscountPrice().compareTo(dto.getPrice()) >= 0) {
            throw new InvalidOperationException("Le prix réduit doit être inférieur au prix normal");
        }

        Product product = productMapper.toEntity(dto);

        // Associer la catégorie si fournie
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée avec l'ID: " + dto.getCategoryId()));
            product.setCategory(category);
        }

        // Définir le statut par défaut
        if (product.getStatus() == null) {
            product.setStatus(ProductStatus.ACTIVE);
        }

        // Définir le seuil de stock faible par défaut
        if (product.getLowStockThreshold() == null) {
            product.setLowStockThreshold(10);
        }

        Product savedProduct = productRepository.save(product);
        log.info("Produit créé avec succès, ID: {}", savedProduct.getId());

        return productMapper.toResponseDTO(savedProduct);
    }

    public ProductResponseDTO getProductById(Long id) {
        log.info("Récupération du produit avec ID: {}", id);
        Product product = findProductById(id);
        return productMapper.toResponseDTO(product);
    }

    public ProductResponseDTO getProductBySku(String sku) {
        log.info("Récupération du produit avec SKU: {}", sku);
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec le SKU: " + sku));
        return productMapper.toResponseDTO(product);
    }

    public Page<ProductSummaryDTO> getAllProducts(Pageable pageable) {
        log.info("Récupération de tous les produits, page: {}", pageable.getPageNumber());
        return productRepository.findAll(pageable)
                .map(productMapper::toSummaryDTO);
    }

    public Page<ProductSummaryDTO> getProductsByStatus(ProductStatus status, Pageable pageable) {
        log.info("Récupération des produits par statut: {}", status);
        return productRepository.findByStatus(status, pageable)
                .map(productMapper::toSummaryDTO);
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

    public Page<ProductSummaryDTO> searchProducts(String keyword, Pageable pageable) {
        log.info("Recherche de produits avec le mot-clé: {}", keyword);
        return productRepository.searchProducts(keyword, pageable)
                .map(productMapper::toSummaryDTO);
    }

    public Page<ProductSummaryDTO> filterProducts(
            Long categoryId,
            ProductStatus status,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String brand,
            Pageable pageable) {

        log.info("Filtrage des produits avec les critères: catégorie={}, status={}, prix={}-{}, marque={}",
                categoryId, status, minPrice, maxPrice, brand);

        return productRepository.filterProducts(categoryId, status, minPrice, maxPrice, brand, pageable)
                .map(productMapper::toSummaryDTO);
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductUpdateDTO dto) {
        log.info("Mise à jour du produit avec ID: {}", id);

        Product product = findProductById(id);

        // Valider le prix de réduction si fourni
        BigDecimal newPrice = dto.getPrice() != null ? dto.getPrice() : product.getPrice();
        if (dto.getDiscountPrice() != null && dto.getDiscountPrice().compareTo(newPrice) >= 0) {
            throw new InvalidOperationException("Le prix réduit doit être inférieur au prix normal");
        }

        // Mettre à jour la catégorie si fournie
        if (dto.getCategoryId() != null) {
            if (dto.getCategoryId() == 0) {
                product.setCategory(null);
            } else {
                Category category = categoryRepository.findById(dto.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée avec l'ID: " + dto.getCategoryId()));
                product.setCategory(category);
            }
        }

        productMapper.updateEntityFromDTO(dto, product);
        Product updatedProduct = productRepository.save(product);

        log.info("Produit mis à jour avec succès, ID: {}", id);
        return productMapper.toResponseDTO(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        log.info("Suppression du produit avec ID: {}", id);

        Product product = findProductById(id);
        productRepository.delete(product);

        log.info("Produit supprimé avec succès, ID: {}", id);
    }

    @Transactional
    public ProductResponseDTO updateProductStatus(Long id, ProductStatus status) {
        log.info("Mise à jour du statut du produit {} vers {}", id, status);

        Product product = findProductById(id);
        product.setStatus(status);
        Product updatedProduct = productRepository.save(product);

        return productMapper.toResponseDTO(updatedProduct);
    }

    @Transactional
    public ProductResponseDTO updateStock(Long id, Integer quantity) {
        log.info("Mise à jour du stock du produit {} vers {}", id, quantity);

        if (quantity < 0) {
            throw new InvalidOperationException("La quantité ne peut pas être négative");
        }

        Product product = findProductById(id);
        product.setStockQuantity(quantity);

        // Mettre à jour automatiquement le statut si nécessaire
        if (quantity == 0 && product.getStatus() == ProductStatus.ACTIVE) {
            product.setStatus(ProductStatus.OUT_OF_STOCK);
        } else if (quantity > 0 && product.getStatus() == ProductStatus.OUT_OF_STOCK) {
            product.setStatus(ProductStatus.ACTIVE);
        }

        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(updatedProduct);
    }

    public List<ProductResponseDTO> getLowStockProducts(Integer threshold) {
        log.info("Récupération des produits avec stock faible (seuil: {})", threshold);

        List<Product> products = productRepository.findByStockQuantityLessThanEqualAndStatus(
                threshold, ProductStatus.ACTIVE);

        return productMapper.toResponseDTOList(products);
    }

    public List<String> getAllBrands() {
        log.info("Récupération de toutes les marques");
        return productRepository.findAllBrands();
    }

    // Méthode utilitaire privée
    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'ID: " + id));
    }
}