package com.sparta.basehw.dto;

import com.sparta.basehw.entity.Product;

import lombok.Getter;

@Getter
public class ProductResponseDto {
	private Long id;
	private String name;
	private int price;

	public ProductResponseDto(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
	}
}