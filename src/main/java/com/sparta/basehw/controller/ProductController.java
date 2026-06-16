package com.sparta.basehw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.sparta.basehw.dto.ProductRequestDto;
import com.sparta.basehw.dto.ProductResponseDto;
import com.sparta.basehw.service.ProductService;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	// 상품 등록 API
	@PostMapping
	public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto requestDto) {
		return ResponseEntity.ok(productService.createProduct(requestDto));
	}

	// 상품 단건 조회 API
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
		return ResponseEntity.ok(productService.getProduct(id));
	}

	// 상품 목록 조회 API
	@GetMapping
	public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	}

	// 상품 수정 API
	@PatchMapping("/{id}")
	public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto requestDto) {
		return ResponseEntity.ok(productService.updateProduct(id, requestDto));
	}

	// 상품 삭제 API
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.ok("상품 삭제 완료");
	}
}
