package com.amcom.desafiotecnicoamcom.src.domain.contract;

import com.amcom.desafiotecnicoamcom.src.domain.dto.CreateOrderDTO;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;

public interface ICreateOrderService {
    Order execute(CreateOrderDTO dto);
}
