package com.amcom.desafiotecnicoamcom.src.domain.model;

import com.amcom.desafiotecnicoamcom.src.domain.enumeration.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String externalId;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
