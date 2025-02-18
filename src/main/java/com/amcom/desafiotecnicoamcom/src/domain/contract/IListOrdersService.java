package com.amcom.desafiotecnicoamcom.src.domain.contract;

import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;

import java.util.List;

public interface IListOrdersService {
    List<Order> execute();
}
