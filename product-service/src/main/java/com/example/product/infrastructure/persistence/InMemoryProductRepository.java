package com.example.product.infrastructure.persistence;

import com.example.product.domain.model.Money;
import com.example.product.domain.model.Product;
import com.example.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 리포지토리 구현체(어댑터)는 infrastructure 계층에 둔다.
 * - 예제 단순화를 위해 인메모리 Map 사용. 실제로는 JpaProductRepository 등으로 교체된다.
 * - 도메인 계층은 이 클래스의 존재를 모른다.
 */
@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final Map<Long, Product> store = new ConcurrentHashMap<>();

    public InMemoryProductRepository() {
        // 예제용 초기 데이터
        store.put(1L, new Product(1L, "무선 키보드", Money.of(45000), 100));
        store.put(2L, new Product(2L, "게이밍 마우스", Money.of(38000), 50));
        store.put(3L, new Product(3L, "27인치 모니터", Money.of(320000), 10));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Product save(Product product) {
        store.put(product.getId(), product);
        return product;
    }
}
