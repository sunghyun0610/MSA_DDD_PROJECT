package com.example.order.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 도메인 이벤트: "주문이 완료되었다"는 과거에 일어난 사실.
 * - 이벤트를 발행하면 알림, 통계, 배송 준비 같은 후속 처리를
 *   주문 로직과 결합하지 않고 분리할 수 있다.
 * - 실무 MSA에서는 이 이벤트를 Kafka 등 메시지 브로커로 발행해
 *   다른 서비스가 구독하게 한다. (이 예제는 Spring 내부 이벤트로 단순화)
 */
public record OrderPlacedEvent(
        String orderId,
        Long customerId,
        BigDecimal totalAmount,
        LocalDateTime occurredAt
) {
}
