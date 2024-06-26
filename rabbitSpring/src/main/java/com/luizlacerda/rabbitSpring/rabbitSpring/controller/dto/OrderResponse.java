package com.luizlacerda.rabbitSpring.rabbitSpring.controller.dto;

import com.luizlacerda.rabbitSpring.rabbitSpring.entity.OrderEntity;

import java.math.BigDecimal;

public record OrderResponse(Long orderId,
                            Long customerId,
                            BigDecimal total) {

    public static OrderResponse fromEntity(OrderEntity entity){
        return new OrderResponse(entity.getOrderId(),entity.getCustumerId(), entity.getTotal());
    }
}
