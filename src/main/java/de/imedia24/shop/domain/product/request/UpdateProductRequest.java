package de.imedia24.shop.domain.product.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
}
