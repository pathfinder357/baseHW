package com.sparta.basehw.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sparta.basehw.dto.OrderRequestDto;
import com.sparta.basehw.dto.OrderResponseDto;
import com.sparta.basehw.entity.Order;
import com.sparta.basehw.entity.Product;
import com.sparta.basehw.repository.OrderRepository;
import com.sparta.basehw.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;

	@Transactional
	public OrderResponseDto createOrder(OrderRequestDto requestDto) {
		Product product = productRepository.findByIdWithLock(requestDto.getProductId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
		product.removeStock(requestDto.getQuantity());
		Order newOrder = new Order(product, requestDto.getQuantity());
		Order savedOrder = orderRepository.save(newOrder);
		return new OrderResponseDto(savedOrder);
	}

	@Transactional(readOnly = true)
	public OrderResponseDto getOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
		return new OrderResponseDto(order);
	}

	public Page<OrderResponseDto> getOrderList(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Order> orderPage = orderRepository.findAllWithProduct(pageable);
		return orderPage.map(OrderResponseDto::from);
	}

	@Transactional
	public OrderResponseDto createOrder(Long productId, int quantity) {
		// 1. 상품 조회 (락 걸림!) -> 다른 요청은 여기서 대기하게 됨
		Product product = productRepository.findByIdWithLock(productId)
			.orElseThrow(() -> new IllegalArgumentException("없는 상품입니다."));

		// 2. 재고 차감 (재고 부족하면 위에서 만든 예외 터짐)
		product.removeStock(quantity);

		// 3. 주문 객체 생성 및 저장
		Order order = new Order(product, quantity);
		orderRepository.save(order);

		return OrderResponseDto.from(order);
		// 여기까지 완료되면 트랜잭션 종료 -> 락 해제 -> 다음 대기자 입장
	}
}
