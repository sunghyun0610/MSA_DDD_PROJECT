package com.example.order.application.port;

/**
 * 상품 서비스 호출 포트(Port) - 헥사고날 아키텍처의 출력 포트.
 * - 응용 계층은 "상품 정보를 가져온다/재고를 차감한다"는 계약만 알고,
 *   HTTP인지 gRPC인지 메시징인지는 infrastructure의 어댑터가 결정한다.
 * - 다른 서비스의 모델을 그대로 들여오지 않고 우리 컨텍스트에 맞는
 *   ProductInfo로 번역한다 → 부패 방지 계층(Anti-Corruption Layer) 역할.
 */
public interface ProductClient {

    ProductInfo getProduct(Long productId);

    void deductStock(Long productId, int quantity);

    record ProductInfo(Long id, String name, java.math.BigDecimal price, int stockQuantity) {
    }
}
