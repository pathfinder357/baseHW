package com.sparta.basehw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.sparta.basehw.dto.OrderRequestDto;
import com.sparta.basehw.dto.OrderResponseDto;
import com.sparta.basehw.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;


	@PostMapping
	public OrderResponseDto createOrder(@RequestBody OrderRequestDto requestDto) {
		return orderService.createOrder(requestDto);
	}


	@GetMapping("/{orderId}")
	public OrderResponseDto getOrder(@PathVariable Long orderId) {
		return orderService.getOrder(orderId);
	}
}
