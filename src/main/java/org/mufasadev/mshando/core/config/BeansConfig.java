package org.mufasadev.mshando.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
@RequiredArgsConstructor
public class BeansConfig {
    @Bean
    public AuditorAware<Integer> auditorProvider() {
        return new ApplicationAuditAware();
    }
}