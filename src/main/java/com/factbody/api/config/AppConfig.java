package com.factbody.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    private final RateLimitInterceptor rateLimitInterceptor;

    public AppConfig(RateLimitInterceptor rateLimitInterceptor) {
        this.rateLimitInterceptor = rateLimitInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor)
                .addPathPatterns("/api/facts/generate");
    }

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }

    @Bean
    public OpenAPI bodyFactsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Body Facts API")
                        .description("API that generates random human body facts using DeepSeek and Gemini AI")
                        .version("1.0.0"));
    }
}
