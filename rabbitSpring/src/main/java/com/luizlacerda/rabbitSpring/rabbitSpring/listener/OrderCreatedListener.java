package com.luizlacerda.rabbitSpring.rabbitSpring.listener;

import com.luizlacerda.rabbitSpring.rabbitSpring.service.OrderService;
import com.luizlacerda.rabbitSpring.rabbitSpring.listener.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


import static com.luizlacerda.rabbitSpring.rabbitSpring.config.RabbitMqConfig.ORDER_CREATED_QUEUE;

@RequiredArgsConstructor
@Component
public class OrderCreatedListener {


    private final OrderService orderService;
    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEvent> message){
        logger.info("message consumed: {}", message);
        orderService.save(message.getPayload());
    }
}
