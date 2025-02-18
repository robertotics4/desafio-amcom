package com.amcom.desafiotecnicoamcom.src.controller.rest;

import com.amcom.desafiotecnicoamcom.src.controller.api.ProductResource;
import com.amcom.desafiotecnicoamcom.src.domain.dto.CreateProductDTO;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Product;
import com.amcom.desafiotecnicoamcom.src.domain.service.CreateProductService;
import com.amcom.desafiotecnicoamcom.src.domain.service.ListProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductController implements ProductResource {
    private final ListProductsService listProductsService;
    private final CreateProductService createProductService;

    @Override
    public ResponseEntity<List<Product>> list() {
        List<Product> products = this.listProductsService.execute();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> create(@RequestBody CreateProductDTO dto) {
        Product createProduct = this.createProductService.execute(dto);
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
    }
}
