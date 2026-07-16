package com.example.order.domain.event;

/**
 * 도메인 이벤트 발행 포트(Port).
 * - 도메인/응용 계층은 "이벤트를 발행한다"는 개념만 알고,
 *   전송 수단(Spring 이벤트, Kafka, RabbitMQ...)은 infrastructure 어댑터가 결정한다.
 */
public interface DomainEventPublisher {

    void publish(Object event);
}
