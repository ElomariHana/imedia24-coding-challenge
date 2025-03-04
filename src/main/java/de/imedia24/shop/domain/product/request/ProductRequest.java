package de.imedia24.shop.domain.product.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductRequest {
    @NotBlank(message = "SKU cannot be blank")
    private String sku;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String description;
    @NotNull(message = "Price is required")
    private BigDecimal price;


}
