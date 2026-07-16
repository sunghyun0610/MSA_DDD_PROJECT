package com.example.order.infrastructure.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    /**
     * @LoadBalanced: "http://product-service" 같은 서비스 이름 URL을
     * Eureka 레지스트리에서 조회한 실제 인스턴스 주소로 변환한다.
     * 인스턴스가 여러 개면 클라이언트 사이드 로드밸런싱이 일어난다.
     */
    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }
}
