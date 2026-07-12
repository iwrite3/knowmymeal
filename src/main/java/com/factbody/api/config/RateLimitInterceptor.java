package com.factbody.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.factbody.api.model.ApiResponse;
import com.factbody.api.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimiterService rateLimiterService;
    private final ObjectMapper objectMapper;

    public RateLimitInterceptor(RateLimiterService rateLimiterService,
                                ObjectMapper objectMapper) {
        this.rateLimiterService = rateLimiterService;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String ip = request.getRemoteAddr();

        if (!rateLimiterService.isAllowed(ip)) {
            response.setStatus(429);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ApiResponse<Void> body = ApiResponse.error("Rate limit exceeded. Try again later.");
            response.getWriter().write(objectMapper.writeValueAsString(body));
            return false;
        }

        return true;
    }
}
