package com.amcom.desafiotecnicoamcom.src.infra.seed;

import com.amcom.desafiotecnicoamcom.src.domain.entity.Product;
import com.amcom.desafiotecnicoamcom.src.infra.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductSeederTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductSeeder sut;

    @BeforeEach
    void setUp() {}

    @Test
    void shouldInsertProductsWhenNoProductsExist() {
        when(productRepository.count()).thenReturn(0L);

        sut.run();

        List<Product> expectedProducts = List.of(
                new Product(null, "Notebook", BigDecimal.valueOf(3500.00)),
                new Product(null, "Mouse Gamer", BigDecimal.valueOf(150.00)),
                new Product(null, "Teclado Mecânico", BigDecimal.valueOf(300.00)),
                new Product(null, "Monitor 27''", BigDecimal.valueOf(1200.00))
        );

        verify(productRepository, times(1)).saveAll(expectedProducts);
    }

    @Test
    void shouldNotInsertProductsWhenProductsExist() {
        when(productRepository.count()).thenReturn(1L);

        sut.run();

        verify(productRepository, times(0)).saveAll(anyList());
    }
}
