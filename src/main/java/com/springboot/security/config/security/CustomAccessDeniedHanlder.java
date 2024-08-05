package com.springboot.security.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHanlder implements AccessDeniedHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHanlder.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
        LOGGER.info("[handle] 접근이 막혔을 경우 경로 리다이렉트");
        response.sendRedirect("/sign-api/exception");
    }
}
