package com.example.loc.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "DELETE", "PATCH", "OPTIONS") // 허용하는 메서드
                .allowedHeaders("*") // 헤더
                .allowCredentials(true) // 인증 정보
                .maxAge(60*60*24); // 하루
    }
}
