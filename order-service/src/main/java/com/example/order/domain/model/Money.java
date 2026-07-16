package com.example.order.domain.model;

import java.math.BigDecimal;

/**
 * 값 객체(Value Object).
 * 주의: product-service의 Money와 코드가 비슷하지만 "일부러" 공유하지 않는다.
 * MSA에서는 바운디드 컨텍스트(서비스)마다 자기만의 모델을 가진다. (공유 커널 최소화)
 */
public record Money(BigDecimal amount) {

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money {
        if (amount == null || amount.signum() < 0) {
            throw new IllegalArgumentException("금액은 0 이상이어야 합니다.");
        }
    }

    public static Money of(long amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    public Money multiply(int quantity) {
        return new Money(amount.multiply(BigDecimal.valueOf(quantity)));
    }

    public Money plus(Money other) {
        return new Money(amount.add(other.amount));
    }
}
