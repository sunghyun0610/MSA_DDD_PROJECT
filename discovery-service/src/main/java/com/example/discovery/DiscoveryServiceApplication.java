package com.example.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 서비스 디스커버리(Eureka Server).
 * - 각 마이크로서비스가 기동 시 자기 위치(host:port)를 여기에 등록한다.
 * - 호출하는 쪽은 "product-service" 같은 서비스 이름만 알면 되고,
 *   실제 주소는 Eureka 레지스트리에서 찾는다 → 주소 하드코딩 제거, 스케일아웃 가능.
 * - 대시보드: http://localhost:8761
 */
@EnableEurekaServer
@SpringBootApplication
public class DiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServiceApplication.class, args);
    }
}
