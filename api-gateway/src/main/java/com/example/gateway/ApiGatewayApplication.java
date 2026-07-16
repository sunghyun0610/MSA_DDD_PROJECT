package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * API Gateway (Spring Cloud Gateway).
 * - 클라이언트의 단일 진입점(Single Entry Point). 클라이언트는 8000 포트만 알면 된다.
 * - 경로 기반 라우팅: /orders/** → order-service, /products/** → product-service
 *   (라우팅 규칙은 application.yml에 선언)
 * - 실무에서는 여기서 인증(JWT 검증), rate limiting, CORS, 로깅 등
 *   공통 관심사를 처리한다.
 */
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
