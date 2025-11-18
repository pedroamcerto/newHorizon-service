package com.newhorizon_service.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Por enquanto retorna "system", mas pode ser integrado com Spring Security
        // para retornar o usuário autenticado
        return Optional.of("system");
    }
}
