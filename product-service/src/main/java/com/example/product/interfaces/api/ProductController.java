package com.example.product.interfaces.api;

import com.example.product.application.ProductApplicationService;
import com.example.product.domain.model.Product;
import com.example.product.interfaces.api.dto.DeductStockRequest;
import com.example.product.interfaces.api.dto.ProductResponse;
import org.springframework.web.bind.annotation.*;

/**
 * 표현 계층(Interfaces / Presentation).
 * - HTTP <-> 유스케이스 변환만 담당. 비즈니스 로직 없음.
 * - 도메인 객체를 그대로 노출하지 않고 DTO로 변환해서 반환한다.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductApplicationService productService;

    public ProductController(ProductApplicationService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        return ProductResponse.from(productService.getProduct(id));
    }

    /** order-service가 주문 시 호출하는 재고 차감 API */
    @PostMapping("/{id}/deduct-stock")
    public ProductResponse deductStock(@PathVariable Long id, @RequestBody DeductStockRequest request) {
        Product product = productService.deductStock(id, request.quantity());
        return ProductResponse.from(product);
    }
}
