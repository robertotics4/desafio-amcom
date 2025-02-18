package com.amcom.desafiotecnicoamcom.src.domain.entity;

import com.amcom.desafiotecnicoamcom.src.domain.enumeration.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa um pedido realizado")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Schema(description = "Identificador único do pedido", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Column(name = "external_id", nullable = false)
    @Schema(description = "Identificador externo do pedido", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID externalId;

    @Column(name = "total_price")
    @Schema(description = "Preço total do pedido", example = "99.99")
    private BigDecimal totalPrice;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @Schema(description = "Status atual do pedido")
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de produtos associados ao pedido")
    private List<OrderProduct> orderProducts;
}
