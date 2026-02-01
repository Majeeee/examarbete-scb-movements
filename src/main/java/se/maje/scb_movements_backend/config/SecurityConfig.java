package se.maje.scb_movements_backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtSecurityContextRepository jwtSecurityContextRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // 👇 RÄTT SÄTT FÖR JWT I WEBFLUX
                .securityContextRepository(jwtSecurityContextRepository)

                .authorizeExchange(ex -> ex
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/movements/**")
                        .hasAuthority("ROLE_USER")
                        .pathMatchers("/api/admin/**")
                        .hasAuthority("ROLE_ADMIN")
                        .anyExchange().authenticated()
                )

                .build();
    }
}
