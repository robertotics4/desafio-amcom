package com.amcom.desafiotecnicoamcom.src.domain.contract;

import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;

public interface IReceiveOrderService {
    Order execute(Order order);
}
