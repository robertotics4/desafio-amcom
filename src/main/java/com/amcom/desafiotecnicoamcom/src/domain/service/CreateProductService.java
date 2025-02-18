package com.amcom.desafiotecnicoamcom.src.domain.service;

import com.amcom.desafiotecnicoamcom.src.domain.contract.ICreateProductService;
import com.amcom.desafiotecnicoamcom.src.domain.dto.CreateProductDTO;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Product;
import com.amcom.desafiotecnicoamcom.src.infra.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateProductService implements ICreateProductService {
    private final ProductRepository productRepository;

    @Override
    public Product execute(CreateProductDTO dto) {
        log.info("Dto {}", dto.toString());
        Product product = Product.builder()
                .name(dto.name())
                .price(dto.price())
                .build();

        log.info("Creating product with name {} and price {}", product.getName(), product.getPrice());
        return this.productRepository.save(product);
    }
}
