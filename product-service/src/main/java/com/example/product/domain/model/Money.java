package com.example.product.domain.model;

import java.math.BigDecimal;

/**
 * 값 객체(Value Object).
 * - 불변(immutable)이며 식별자가 없다. 값이 같으면 같은 객체로 취급한다.
 * - 원시 타입(BigDecimal)을 그대로 쓰지 않고 도메인 개념(돈)으로 감싼다.
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
