package com.sparta.basehw.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private int stock;


	@Builder
	public Product(String name, int price, int stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
	}


	public void updateInfo(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public void removeStock(int quantity) {
		int restStock = this.stock - quantity;
		if (restStock < 0) {
			throw new IllegalArgumentException("재고가 부족합니다.");
		}
		this.stock = restStock;
	}
}