package com.studyflow.config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.studyflow.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	 @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	            throws ServletException, IOException{
	        String token = request.getHeader("Authorization");

	        if (token != null && token.startsWith("Bearer ")) {
	            token = token.substring(7); // "Bearer " 부분을 제거

	            try {
	                String username = JwtUtil.extractUsername(token);
	                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	                    // JWT가 유효하면 인증 처리
	                    UsernamePasswordAuthenticationToken authentication =
	                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
	                    SecurityContextHolder.getContext().setAuthentication(authentication);
	                }
	            } catch (ExpiredJwtException e) {
	                logger.warn("Expired JWT token", e);
	            } catch (Exception e) {
	                logger.warn("Invalid JWT token", e);
	            }
	        }
	        filterChain.doFilter(request, response);
	    }


}
