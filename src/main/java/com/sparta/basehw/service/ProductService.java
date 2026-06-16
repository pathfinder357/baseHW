package com.sparta.basehw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import com.sparta.basehw.dto.ProductRequestDto;
import com.sparta.basehw.dto.ProductResponseDto;
import com.sparta.basehw.entity.Product;
import com.sparta.basehw.repository.ProductRepository;

@Service
@RequiredArgsConstructor // final이 붙은 필드의 생성자를 자동으로 만들어줍니다. (의존성 주입)
public class ProductService {

	private final ProductRepository productRepository;

	// 1. 상품 등록 (Create)
	@Transactional // DB를 조작하는 작업은 트랜잭션 안에서 안전하게 처리합니다.
	public ProductResponseDto createProduct(ProductRequestDto requestDto) {
		Product product = Product.builder()
			.name(requestDto.getName())
			.price(requestDto.getPrice())
			.stock(requestDto.getStock())
			.build();

		Product savedProduct = productRepository.save(product); // DB에 저장
		return new ProductResponseDto(savedProduct);
	}

	// 2. 단건 조회 (Read)
	@Transactional(readOnly = true) // 조회만 할 때는 readOnly=true를 주면 성능이 좋아집니다.
	public ProductResponseDto getProduct(Long id) {
		Product product = productRepository.findById(id)
			// 만약 상품을 못 찾으면 예외를 던집니다.
			.orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다. ID: " + id));
		return new ProductResponseDto(product);
	}

	// 3. 목록 조회 (Read)
	@Transactional(readOnly = true)
	public List<ProductResponseDto> getAllProducts() {
		return productRepository.findAll().stream()
			.map(ProductResponseDto::new) // 찾아온 Product 뭉치를 DTO 뭉치로 바꿔줍니다.
			.collect(Collectors.toList());
	}

	// 4. 상품 수정 (Update)
	@Transactional // 트랜잭션이 있기 때문에 따로 save()를 안 해도 메서드가 끝날 때 DB에 변경사항이 자동 반영됩니다. (Dirty Checking 기능)
	public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다. ID: " + id));

		product.updateInfo(requestDto.getName(), requestDto.getPrice());
		return new ProductResponseDto(product);
	}

	// 5. 상품 삭제 (Delete)
	@Transactional
	public void deleteProduct(Long id) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다. ID: " + id));

		productRepository.delete(product);
	}
}
