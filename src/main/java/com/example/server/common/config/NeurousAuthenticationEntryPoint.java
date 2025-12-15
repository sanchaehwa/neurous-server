package com.example.server.common.config;

import com.example.server.global.NeurousApiResponse;
import com.example.server.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class NeurousAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		NeurousApiResponse<?> apiResponse = NeurousApiResponse.fail(ErrorCode.UNAUTHORIZED_ACCESS);

		log.info(authException.getMessage(), ErrorCode.UNAUTHORIZED_ACCESS.getMessage());
		objectMapper.writeValue(response.getWriter(), apiResponse);
	}
}
