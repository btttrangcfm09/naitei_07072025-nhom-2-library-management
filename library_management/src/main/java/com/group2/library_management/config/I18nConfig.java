package com.group2.library_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.lang.NonNull;
import java.util.Locale;

@Configuration
public class I18nConfig implements WebMvcConfigurer {
    // Bean này sẽ quản lý ngôn ngữ của ứng dụng.
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // Thiết lập ngôn ngữ mặc định nếu không có ngôn ngữ nào được chọn. Ở đây là tiếng Việt.
        slr.setDefaultLocale(Locale.forLanguageTag("vi"));
        return slr;
    }

    // Bean này sẽ cho phép thay đổi ngôn ngữ thông qua tham số trong URL.
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        // Tham số này sẽ được sử dụng trong URL để thay đổi ngôn ngữ.
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
