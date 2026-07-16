package com.example.order.infrastructure.event;

import com.example.order.domain.event.DomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * DomainEventPublisher 포트의 Spring 이벤트 어댑터.
 * - 실무 MSA에서는 이 부분을 KafkaDomainEventPublisher 등으로 교체해
 *   다른 서비스가 이벤트를 구독하게 만든다.
 */
@Component
public class SpringDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher springPublisher;

    public SpringDomainEventPublisher(ApplicationEventPublisher springPublisher) {
        this.springPublisher = springPublisher;
    }

    @Override
    public void publish(Object event) {
        springPublisher.publishEvent(event);
    }
}
