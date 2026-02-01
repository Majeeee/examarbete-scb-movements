package se.maje.scb_movements_backend.config;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationConverter {

    public Mono<AbstractAuthenticationToken> convert(String token, JwtUtil jwtUtil) {

        Claims claims = jwtUtil.extractAllClaims(token);

        String email = claims.getSubject();
        List<String> roles = claims.get("roles", List.class);

        Collection<GrantedAuthority> authorities =
                roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()); // <- FIX

        return Mono.just(
                new UsernamePasswordAuthenticationToken(email, token, authorities)
        );
    }
}
