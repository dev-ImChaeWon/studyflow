package com.studyflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	// WebSecurityConfigurerAdapter대신, SecurityFilterChain 을 활용하는 방식으로 변경
//	@Bean
//	public SecurityFilterChain tmp(HttpSecurity http) throws Exception {
//
//		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/student-homework/**").permitAll()
//				.requestMatchers("/api/teacher").permitAll().requestMatchers("/api/homework-comment").permitAll()
//				.anyRequest().authenticated()).csrf((csrf) -> csrf.disable());
//
//		// http보안 에서 Request관련 설정을 한다 그리고 csrf관련 설정을 한다.
//		return http.build();
//	}
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")  // /api/** 경로에 대해 CORS 설정
                    .allowedOrigins("http://localhost:8088")  // 허용할 출처
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 허용할 HTTP 메소드
                    .allowedHeaders("Authorization", "Content-Type")  // 허용할 헤더
                    .allowCredentials(true);  // 자격 증명(쿠키 포함)을 허용
            }
        };
    }
    

//	@Bean
//	public SecurityFilterChain tmp(HttpSecurity http) throws Exception {
//
//		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/student-homework/**")
//				.permitAll().requestMatchers("/api/teacher").authenticated()).csrf((csrf) -> csrf.disable());
//		return http.build();
//
//		// http보안 에서 Request관련 설정을 한다 그리고 csrf관련 설정을 한다.
//	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		// JWT 인증 필터 등록
        http.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		http.authorizeHttpRequests().requestMatchers("/api/auth/signup", "/api/auth/signin").permitAll().requestMatchers("/api/**").authenticated();
//	        HttpSecurity.csrf().disable();

		return http.build();
	}
}
