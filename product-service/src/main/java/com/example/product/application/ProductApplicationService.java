package com.example.product.application;

import com.example.product.domain.model.Product;
import com.example.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

/**
 * 응용 서비스(Application Service).
 * - 유스케이스 단위로 도메인 객체를 조율(orchestration)하는 얇은 계층.
 * - 비즈니스 규칙 자체는 도메인 모델(Product)에 있고, 여기서는 흐름만 담당한다.
 */
@Service
public class ProductApplicationService {

    private final ProductRepository productRepository;

    public ProductApplicationService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + productId));
    }

    /** 유스케이스: 재고 차감 (주문 서비스가 호출) */
    public Product deductStock(Long productId, int quantity) {
        Product product = getProduct(productId);
        product.decreaseStock(quantity); // 규칙 검증은 애그리거트가 수행
        return productRepository.save(product);
    }
}
