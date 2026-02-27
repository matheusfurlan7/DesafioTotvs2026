package com.totvs.contas_api.infrastructure.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class InMemoryUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {

        if (!"admin@totvs.com".equals(username)) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        // hash gerada previamente para "123"
        return User.withUsername(username)
                .password("$2a$10$oEwCoHI5LWRz8egSeaumnub4Ox7K68VkUyFoOef/Jpc6f5L2wMcRu")
                .roles("ADMIN")
                .build();
    }
}