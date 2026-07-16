package com.example.order.application;

import com.example.order.domain.event.OrderPlacedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 도메인 이벤트 핸들러.
 * - 주문 완료 후의 부가 작업(알림 발송 등)을 주문 유스케이스와 분리한다.
 * - OrderApplicationService는 이 핸들러의 존재를 전혀 모른다 → 느슨한 결합.
 */
@Component
public class OrderEventHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);

    @EventListener
    public void handle(OrderPlacedEvent event) {
        // 실무에서는 여기서 알림 발송, 통계 집계, 타 서비스로 메시지 발행 등을 수행
        log.info("[주문 완료 이벤트 수신] orderId={}, customerId={}, totalAmount={}",
                event.orderId(), event.customerId(), event.totalAmount());
    }
}
