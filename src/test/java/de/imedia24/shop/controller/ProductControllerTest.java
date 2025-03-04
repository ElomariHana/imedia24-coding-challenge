package de.imedia24.shop.controller;

import de.imedia24.shop.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import de.imedia24.shop.domain.product.response.ProductResponse;
import de.imedia24.shop.domain.product.request.UpdateProductRequest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testFindProductsBySkus() throws Exception {
        List<String> skus = Arrays.asList("123", "4567", "8901");
        List<ProductResponse> productResponses = Arrays.asList(
                new ProductResponse("123", "Product 1", "Description 1", BigDecimal.valueOf(100), ZonedDateTime.now(), ZonedDateTime.now()),
                new ProductResponse("4567", "Product 2", "Description 2", BigDecimal.valueOf(200),ZonedDateTime.now(), ZonedDateTime.now())
        );

        Mockito.when(productService.findProductBySkus(skus)).thenReturn(productResponses);

        mockMvc.perform(get("/products?skus=123,4567"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].sku").value("123"))
                .andExpect(jsonPath("$[1].sku").value("4567"));
    }


    @Test
    public void testUpdateProduct() throws Exception {
        UpdateProductRequest updateRequest = new UpdateProductRequest("Updated Name", "Updated Description", BigDecimal.valueOf(200));
        ProductResponse updatedProduct = new ProductResponse("123", "Updated Name", "Updated Description", BigDecimal.valueOf(400), ZonedDateTime.now(), ZonedDateTime.now());

        Mockito.when(productService.updateProduct(Mockito.anyString(), Mockito.any(UpdateProductRequest.class))).thenReturn(updatedProduct);

        mockMvc.perform(MockMvcRequestBuilders.patch("/products/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Name\", \"description\": \"Updated Description\", \"price\": 200}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("123"))
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

}
