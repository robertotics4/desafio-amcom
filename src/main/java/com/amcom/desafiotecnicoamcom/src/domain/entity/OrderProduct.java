package com.amcom.desafiotecnicoamcom.src.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "order_product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Relacionamento entre pedido e produto")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Schema(description = "Identificador único do relacionamento entre pedido e produto", example = "123e4567-e89b-12d3-a456-426614174003")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @Schema(description = "Pedido ao qual o produto está associado")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @Schema(description = "Produto associado ao pedido")
    private Product product;

    @Column(name = "quantity", nullable = false)
    @Schema(description = "Quantidade do produto no pedido", example = "2")
    private int quantity;
}
