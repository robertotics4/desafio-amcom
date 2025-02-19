package com.amcom.desafiotecnicoamcom.src.domain.contract;

import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;

import java.util.UUID;

public interface ICompleteOrderService {
    Order execute(UUID orderId);
}
