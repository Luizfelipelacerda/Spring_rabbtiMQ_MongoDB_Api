package com.luizlacerda.rabbitSpring.rabbitSpring.service;

import com.luizlacerda.rabbitSpring.rabbitSpring.controller.dto.OrderResponse;
import com.luizlacerda.rabbitSpring.rabbitSpring.entity.OrderEntity;
import com.luizlacerda.rabbitSpring.rabbitSpring.entity.OrderItem;
import com.luizlacerda.rabbitSpring.rabbitSpring.listener.dto.OrderCreatedEvent;
import com.luizlacerda.rabbitSpring.rabbitSpring.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final MongoTemplate mongoTemplate;

    public void save(OrderCreatedEvent event){

        var entity = new OrderEntity();
        entity.setOrderId(event.codigoPedido());
        entity.setCustumerId(event.codigoClient());
        entity.setItems(getOrderItems(event));
        entity.setTotal(getTotal(event));

        orderRepository.save(entity);

    }

    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest){

        var orders = orderRepository.findAllByCustumerId (customerId, pageRequest);

        return orders.map(OrderResponse::fromEntity);
    }


    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.itens().stream()
                .map(i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private static List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.itens().stream()
                .map(i -> new OrderItem(i.produto(), i.quantidade(), i.preco()))
                .toList();
    }
}
