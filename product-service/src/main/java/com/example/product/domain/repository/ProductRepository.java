package com.example.product.domain.repository;

import com.example.product.domain.model.Product;

import java.util.Optional;

/**
 * 리포지토리 인터페이스는 "도메인 계층"에 둔다 (의존성 역전, DIP).
 * - 도메인은 저장 기술(JPA, MongoDB, 인메모리...)을 알지 못한다.
 * - 실제 구현체는 infrastructure 계층에 위치한다.
 */
public interface ProductRepository {

    Optional<Product> findById(Long id);

    Product save(Product product);
}
