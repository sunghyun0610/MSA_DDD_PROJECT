package com.example.product.domain.model;

/**
 * 상품 애그리거트 루트(Aggregate Root).
 * - 재고 차감 같은 비즈니스 규칙(불변식)은 반드시 애그리거트 내부 메서드를 통해서만 변경한다.
 * - setter를 열어두지 않는다 → 도메인 규칙이 여러 곳으로 흩어지는 것을 막는다.
 */
public class Product {

    private final Long id;
    private final String name;
    private final Money price;
    private int stockQuantity;

    public Product(Long id, String name, Money price, int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("재고는 0 이상이어야 합니다.");
        }
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    /** 도메인 규칙: 재고보다 많은 수량은 차감할 수 없다. */
    public void decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("차감 수량은 1 이상이어야 합니다.");
        }
        if (stockQuantity < quantity) {
            throw new IllegalStateException(
                    "재고가 부족합니다. (현재 재고: %d, 요청 수량: %d)".formatted(stockQuantity, quantity));
        }
        this.stockQuantity -= quantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
}
