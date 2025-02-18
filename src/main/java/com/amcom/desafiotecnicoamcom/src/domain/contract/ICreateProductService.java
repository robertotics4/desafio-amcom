package com.amcom.desafiotecnicoamcom.src.domain.contract;

import com.amcom.desafiotecnicoamcom.src.domain.entity.Product;

public interface ICreateProductService {
    Product execute(Product product);
}
