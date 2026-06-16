package com.sparta.basehw.dto;

import com.sparta.basehw.entity.Order;

import lombok.Getter;

@Getter
public class OrderResponseDto {
	private Long orderId;
	private String productName;

	public OrderResponseDto(Order order) {
		this.orderId = order.getId();
		this.productName = order.getProduct().getName();
	}

	public static OrderResponseDto from(Order order) {
		return new OrderResponseDto(order);
	}
}