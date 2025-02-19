package com.amcom.desafiotecnicoamcom.src.domain.service;

import com.amcom.desafiotecnicoamcom.src.domain.dto.CreateProductDTO;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Product;
import com.amcom.desafiotecnicoamcom.src.infra.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductService sut;

    private CreateProductDTO createProductDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        createProductDTO = new CreateProductDTO("Sample Product", BigDecimal.valueOf(100));
        product = Product.builder()
                .name(createProductDTO.name())
                .price(createProductDTO.price())
                .build();
    }

    @Test
    void shouldCreateAndReturnProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = sut.execute(createProductDTO);

        assertNotNull(result);
        assertEquals(createProductDTO.name(), result.getName());
        assertEquals(createProductDTO.price(), result.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }
}
