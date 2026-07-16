package com.example.order.infrastructure.client;

import com.example.order.application.port.ProductClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Map;

/**
 * ProductClient 포트의 HTTP 어댑터.
 * - base-url은 "http://product-service" (Eureka 서비스 이름).
 *   @LoadBalanced RestClient.Builder가 실제 인스턴스 주소로 변환한다.
 * - 응답을 우리 컨텍스트의 모델(ProductInfo)로 번역한다.
 * - 나중에 gRPC나 메시징으로 바꿔도 이 클래스만 교체하면 된다.
 */
@Component
public class ProductRestClient implements ProductClient {

    private final RestClient restClient;

    public ProductRestClient(RestClient.Builder loadBalancedRestClientBuilder,
                             @Value("${services.product.base-url}") String productBaseUrl) {
        this.restClient = loadBalancedRestClientBuilder.baseUrl(productBaseUrl).build();
    }

    @Override
    public ProductInfo getProduct(Long productId) {
        ProductApiResponse response = restClient.get()
                .uri("/products/{id}", productId)
                .retrieve()
                .body(ProductApiResponse.class);
        return toProductInfo(response);
    }

    @Override
    public void deductStock(Long productId, int quantity) {
        restClient.post()
                .uri("/products/{id}/deduct-stock", productId)
                .body(Map.of("quantity", quantity))
                .retrieve()
                .toBodilessEntity();
    }

    private ProductInfo toProductInfo(ProductApiResponse response) {
        return new ProductInfo(response.id(), response.name(), response.price(), response.stockQuantity());
    }

    /** product-service API 응답 전용 DTO (외부 모델을 도메인에 직접 노출하지 않음) */
    private record ProductApiResponse(Long id, String name, BigDecimal price, int stockQuantity) {
    }
}
