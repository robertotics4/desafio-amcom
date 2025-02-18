package com.amcom.desafiotecnicoamcom.src.domain.contract;

import com.amcom.desafiotecnicoamcom.src.domain.entity.Product;

import java.util.List;

public interface IListProductsService {
    List<Product> execute();
}
