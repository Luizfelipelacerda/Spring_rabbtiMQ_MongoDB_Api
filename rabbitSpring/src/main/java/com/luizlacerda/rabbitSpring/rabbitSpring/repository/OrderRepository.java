package com.luizlacerda.rabbitSpring.rabbitSpring.repository;


import com.luizlacerda.rabbitSpring.rabbitSpring.controller.dto.OrderResponse;
import com.luizlacerda.rabbitSpring.rabbitSpring.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, Long> {
    Page<OrderEntity> findAllByCustumerId(Long customerId, PageRequest pageRequest);
}
