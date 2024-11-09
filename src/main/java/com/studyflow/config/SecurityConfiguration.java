package com.studyflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

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
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeHttpRequests().anyRequest().permitAll();
//	        HttpSecurity.csrf().disable();
		return http.build();
	}
}
