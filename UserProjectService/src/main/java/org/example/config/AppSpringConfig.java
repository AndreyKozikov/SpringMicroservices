package org.example.config;

import lombok.AllArgsConstructor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Конфигурация приложения Spring, включая настройки MVC и Thymeleaf.
 */
@Configuration
@ComponentScan("org.example")
@EnableWebMvc
@EnableTransactionManagement
@AllArgsConstructor

public class AppSpringConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;


    /**
     * Создает фильтр для обработки скрытых методов HTTP.
     *
     * @return HiddenHttpMethodFilter, который позволяет использовать скрытые методы
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
    }

}
