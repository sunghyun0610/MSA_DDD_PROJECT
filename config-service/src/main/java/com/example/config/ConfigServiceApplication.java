package com.example.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 중앙 설정 서버(Spring Cloud Config Server).
 * - 각 서비스에 흩어져 있던 설정을 한 곳에서 관리한다.
 * - 서비스들은 기동 시 spring.application.name으로 자기 설정을 받아간다.
 *   예) order-service → GET http://localhost:8888/order-service/default
 * - 이 예제는 classpath의 config-repo 폴더를 사용하지만, 실무에서는 Git 저장소를 쓴다.
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServiceApplication.class, args);
    }
}
