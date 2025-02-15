package com.luizlacerda.rabbitSpring.rabbitSpring.controller;

import com.luizlacerda.rabbitSpring.rabbitSpring.controller.dto.PaginationResponse;
import com.luizlacerda.rabbitSpring.rabbitSpring.service.OrderService;
import com.luizlacerda.rabbitSpring.rabbitSpring.controller.dto.ApiResponse;
import com.luizlacerda.rabbitSpring.rabbitSpring.controller.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(@PathVariable("customerId") Long customerId,
                                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page,pageSize));

        return ResponseEntity.ok(new ApiResponse<>(
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }
}
