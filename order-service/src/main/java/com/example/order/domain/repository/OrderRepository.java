package com.example.order.domain.repository;

import com.example.order.domain.model.Order;

import java.util.Optional;

/**
 * 도메인 계층의 리포지토리 인터페이스 (구현체는 infrastructure에).
 */
public interface OrderRepository {

    Optional<Order> findById(String id);

    Order save(Order order);
}
