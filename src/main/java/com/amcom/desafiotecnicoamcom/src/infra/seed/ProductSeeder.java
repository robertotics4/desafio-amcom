package com.amcom.desafiotecnicoamcom.src.infra.seed;

import com.amcom.desafiotecnicoamcom.src.domain.entity.Product;
import com.amcom.desafiotecnicoamcom.src.infra.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductSeeder implements CommandLineRunner {
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (productRepository.count() == 0) {
            List<Product> products = List.of(
                    new Product(null, "Notebook", BigDecimal.valueOf(3500.00)),
                    new Product(null, "Mouse Gamer", BigDecimal.valueOf(150.00)),
                    new Product(null, "Teclado Mecânico", BigDecimal.valueOf(300.00)),
                    new Product(null, "Monitor 27''", BigDecimal.valueOf(1200.00))
            );

            productRepository.saveAll(products);
            System.out.println("Produtos inseridos no banco.");
        }
    }
}
