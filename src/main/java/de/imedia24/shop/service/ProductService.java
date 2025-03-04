package de.imedia24.shop.service;

import de.imedia24.shop.db.entity.ProductEntity;
import de.imedia24.shop.db.repository.ProductRepository;
import de.imedia24.shop.domain.product.request.ProductRequest;
import de.imedia24.shop.domain.product.request.UpdateProductRequest;
import de.imedia24.shop.domain.product.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse findProductBySku(String sku) {
        ProductEntity productEntity = productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToProductResponse(productEntity);
    }
    public List<ProductResponse> findProductBySkus(List<String> skus) {
        List<ProductEntity> productEntities = productRepository.findBySkuIn(skus);

        return productEntities.stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsBySku(request.getSku())) {
            throw new RuntimeException("Product with SKU " + request.getSku() + " already exists.");
        }

        ProductEntity productEntity = ProductEntity.builder()
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        productEntity = productRepository.save(productEntity);

        return mapToProductResponse(productEntity);
    }

    public ProductResponse updateProduct(String sku, UpdateProductRequest request) {
        ProductEntity productEntity = productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (request.getName() != null) {
            productEntity.setName(request.getName());
        }
        if (request.getDescription() != null) {
            productEntity.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            productEntity.setPrice(request.getPrice());
        }

        productEntity = productRepository.save(productEntity);

        return mapToProductResponse(productEntity);
    }

    private ProductResponse mapToProductResponse(ProductEntity productEntity) {
        return ProductResponse.builder()
                .sku(productEntity.getSku())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .createdAt(productEntity.getCreatedAt())
                .updatedAt(productEntity.getUpdatedAt())
                .build();
    }


}
