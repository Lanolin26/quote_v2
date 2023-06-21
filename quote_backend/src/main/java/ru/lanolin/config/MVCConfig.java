package ru.lanolin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

@Configuration
@EnableWebMvc
public class MVCConfig implements WebMvcConfigurer {

    private static final Map<String, String> resourceAndPath = Map.of(
            "/js/**", "classpath:/static/js/",
            "/css/**", "classpath:/static/css/",
            "/fonts/**", "classpath:/static/fonts/",
            "/favicon.ico", "classpath:/static/favicon.ico"
    );

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        resourceAndPath.forEach((k, v) -> registry.addResourceHandler(k).addResourceLocations(v));
    }

    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}
