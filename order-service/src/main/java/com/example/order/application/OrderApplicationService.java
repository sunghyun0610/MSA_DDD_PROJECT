package com.example.order.application;

import com.example.order.application.dto.PlaceOrderCommand;
import com.example.order.application.port.ProductClient;
import com.example.order.domain.event.DomainEventPublisher;
import com.example.order.domain.event.OrderPlacedEvent;
import com.example.order.domain.model.Money;
import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderLine;
import com.example.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 주문 유스케이스를 담당하는 응용 서비스.
 *
 * 흐름: 상품 조회(원격) → 재고 차감(원격) → Order 애그리거트 생성 → 저장 → 이벤트 발행
 *
 * 이 클래스는 인터페이스(포트)에만 의존한다:
 *   OrderRepository, ProductClient, DomainEventPublisher
 * → 구현 기술이 바뀌어도 유스케이스 코드는 변하지 않는다.
 */
@Service
public class OrderApplicationService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final DomainEventPublisher eventPublisher;

    public OrderApplicationService(OrderRepository orderRepository,
                                   ProductClient productClient,
                                   DomainEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.eventPublisher = eventPublisher;
    }

    public Order placeOrder(PlaceOrderCommand command) {
        // 1. 상품 서비스에서 상품 정보를 조회하고 재고를 차감한다 (서비스 간 동기 호출)
        List<OrderLine> orderLines = command.items().stream()
                .map(item -> {
                    ProductClient.ProductInfo product = productClient.getProduct(item.productId());
                    productClient.deductStock(item.productId(), item.quantity());
                    return new OrderLine(
                            product.id(),
                            product.name(),
                            new Money(product.price()),
                            item.quantity()
                    );
                })
                .toList();

        // 2. 주문 애그리거트 생성 (총액 계산, 불변식 검증은 도메인이 담당)
        Order order = Order.place(UUID.randomUUID().toString(), command.customerId(), orderLines);

        // 3. 저장
        orderRepository.save(order);

        // 4. 도메인 이벤트 발행 → 알림/통계 등 후속 처리는 이벤트 핸들러가 담당
        eventPublisher.publish(new OrderPlacedEvent(
                order.getId(),
                order.getCustomerId(),
                order.getTotalAmount().amount(),
                LocalDateTime.now()
        ));

        return order;
    }

    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. id=" + orderId));
    }
}
