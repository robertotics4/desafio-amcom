package com.amcom.desafiotecnicoamcom.src.controller.rest;

import com.amcom.desafiotecnicoamcom.src.domain.dto.CreateProductDTO;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Product;
import com.amcom.desafiotecnicoamcom.src.domain.service.CreateProductService;
import com.amcom.desafiotecnicoamcom.src.domain.service.ListProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Mock
    private ListProductsService listProductsService;

    @Mock
    private CreateProductService createProductService;

    @InjectMocks
    private ProductController productController;

    private Product product;
    private CreateProductDTO createProductDTO;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Notebook");
        product.setPrice(BigDecimal.valueOf(3500.00));

        createProductDTO = new CreateProductDTO("Notebook", BigDecimal.valueOf(3500.00));
    }

    @Test
    void shouldListProducts() {
        when(listProductsService.execute()).thenReturn(List.of(product));

        ResponseEntity<List<Product>> response = productController.list();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Notebook", response.getBody().get(0).getName());
        verify(listProductsService, times(1)).execute();
    }

    @Test
    void shouldCreateProduct() {
        when(createProductService.execute(createProductDTO)).thenReturn(product);

        ResponseEntity<Product> response = productController.create(createProductDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Notebook", response.getBody().getName());
        assertEquals(BigDecimal.valueOf(3500.00), response.getBody().getPrice());
        verify(createProductService, times(1)).execute(createProductDTO);
    }
}
