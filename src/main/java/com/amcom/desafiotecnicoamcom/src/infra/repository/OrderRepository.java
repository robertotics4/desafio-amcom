package com.amcom.desafiotecnicoamcom.src.infra.repository;

import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    boolean existsByExternalId(UUID externalId);
    Order findByExternalId(UUID externalId);
}
