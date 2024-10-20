package com.studyflow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
				.allowedOrigins("*") // 모든 출처 허용 (개발용, 필요시 특정 도메인으로 제한 가능)
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메소드
				.allowedHeaders("*") // 모든 헤더 허용
				.allowCredentials(false); // 쿠키/인증 정보 전송 허용 여부
	}
}