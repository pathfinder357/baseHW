package com.sparta.basehw.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparta.basehw.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
	@Query(value = "SELECT o FROM Order o JOIN FETCH o.product",
		countQuery = "SELECT count(o) FROM Order o")
	Page<Order> findAllWithProduct(Pageable pageable);

}
