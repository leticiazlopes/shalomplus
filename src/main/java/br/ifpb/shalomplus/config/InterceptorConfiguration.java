package br.ifpb.shalomplus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.ifpb.shalomplus.interceptor.AuthInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
        .addInterceptor(authInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns(
            "/auth/**",
            "/css/**",
            "/js/**",
            "/img/**",
            "/imagens/**",
            "/home"
        );

    }
}
