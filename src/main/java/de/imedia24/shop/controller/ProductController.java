package de.imedia24.shop.controller;

import de.imedia24.shop.db.entity.ProductEntity;
import de.imedia24.shop.domain.product.request.ProductRequest;
import de.imedia24.shop.domain.product.request.UpdateProductRequest;
import de.imedia24.shop.domain.product.response.ProductResponse;
import de.imedia24.shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @Operation(summary = "Find a product by SKU", description = "Retrieve product details using a specific SKU.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{sku}")
    public ResponseEntity<ProductResponse> findProductBySku(@PathVariable String sku) {
        logger.info("Request for product {}", sku);
        try {
            ProductResponse productResponse = productService.findProductBySku(sku);
            logger.info("Product with sku {} found", sku);
            return ResponseEntity.ok(productResponse);
        } catch (RuntimeException e) {
            logger.error("Product with sku {} not found. Error: {}", sku, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Find products by SKUs", description = "Retrieve multiple products using a list of SKUs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "404", description = "No products found for given SKUs")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findProductBySkus(@RequestParam List<String> skus) {
        logger.info("Request product for skus {}", skus);
        List<ProductResponse> entities = productService.findProductBySkus(skus);
        return ResponseEntity.ok(entities);
    }
    @Operation(summary = "Create a new product", description = "Create a new product entry in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        ProductResponse savedProduct = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @Operation(
            summary = "Update a product",
            description = "This endpoint allows you to update a product based on its SKU",
            tags = {"Product"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid SKU or request body"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PatchMapping("/{sku}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String sku, @RequestBody UpdateProductRequest updateProductRequest) {
        logger.info("Request to update product for sku {}", sku);
        ProductResponse updatedProduct = productService.updateProduct(sku, updateProductRequest);
        return ResponseEntity.ok(updatedProduct);
    }
}
