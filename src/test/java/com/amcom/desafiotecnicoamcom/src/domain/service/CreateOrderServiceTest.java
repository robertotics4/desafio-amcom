package com.amcom.desafiotecnicoamcom.src.domain.service;

import com.amcom.desafiotecnicoamcom.src.config.jackson.JsonConverter;
import com.amcom.desafiotecnicoamcom.src.domain.dto.CreateOrderDTO;
import com.amcom.desafiotecnicoamcom.src.domain.dto.ProductOrderDTO;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Product;
import com.amcom.desafiotecnicoamcom.src.domain.enumeration.OrderStatus;
import com.amcom.desafiotecnicoamcom.src.domain.exception.BusinessException;
import com.amcom.desafiotecnicoamcom.src.infra.contract.IProducer;
import com.amcom.desafiotecnicoamcom.src.infra.repository.OrderRepository;
import com.amcom.desafiotecnicoamcom.src.infra.repository.ProductRepository;
import com.amcom.desafiotecnicoamcom.src.support.constants.BrokerConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateOrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private IProducer producer;

    @Mock
    private JsonConverter jsonConverter;

    @InjectMocks
    private CreateOrderService sut;

    private CreateOrderDTO createOrderDTO;
    private UUID externalId;

    @BeforeEach
    void setUp() {
        externalId = UUID.randomUUID();
        createOrderDTO = new CreateOrderDTO(externalId, List.of(new ProductOrderDTO(UUID.randomUUID(), 2)));
        lenient().when(jsonConverter.getJsonOfObject(any())).thenReturn("{\"mocked\": \"json\"}");
    }

    @Test
    void shouldSendToDlqWhenOrderAlreadyExists() {
        when(orderRepository.existsByExternalId(externalId)).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> sut.execute(createOrderDTO));
        assertEquals("Pedido já existente ID " + externalId, exception.getMessage());

        verify(producer).produce(eq(BrokerConstants.Topics.EXTERNAL_AVAILABLE_ORDER_TOPIC + ".dlq"), anyString(), anyString());
    }

    @Test
    void shouldSendToDlqWhenOrderHasNoProducts() {
        CreateOrderDTO emptyOrderDTO = new CreateOrderDTO(externalId, List.of());

        BusinessException exception = assertThrows(BusinessException.class, () -> sut.execute(emptyOrderDTO));
        assertEquals("Pedido sem produtos ID " + externalId, exception.getMessage());

        verify(producer).produce(eq(BrokerConstants.Topics.EXTERNAL_AVAILABLE_ORDER_TOPIC + ".dlq"), anyString(), anyString());
    }

    @Test
    void shouldSendToDlqWhenOrderContainsInvalidProducts() {
        when(productRepository.findAllById(any())).thenReturn(List.of());

        BusinessException exception = assertThrows(BusinessException.class, () -> sut.execute(createOrderDTO));
        assertEquals("Pedido contém produtos inválidos ID " + externalId, exception.getMessage());

        verify(producer).produce(eq(BrokerConstants.Topics.EXTERNAL_AVAILABLE_ORDER_TOPIC + ".dlq"), anyString(), anyString());
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        Product product = new Product();
        product.setId(createOrderDTO.products().get(0).id());
        product.setPrice(BigDecimal.TEN);

        when(orderRepository.existsByExternalId(externalId)).thenReturn(false);
        when(productRepository.findAllById(any())).thenReturn(List.of(product));

        Order order = sut.execute(createOrderDTO);

        assertNotNull(order);
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(1, order.getOrderProducts().size());
        assertEquals(BigDecimal.valueOf(20), order.getTotalPrice());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
