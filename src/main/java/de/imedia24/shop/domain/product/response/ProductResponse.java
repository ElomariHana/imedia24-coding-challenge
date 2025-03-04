package de.imedia24.shop.domain.product.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponse {
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
