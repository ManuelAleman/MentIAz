package com.mentiaz.mentiaz.config;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mentiaz.mentiaz.auth.repository.UserRepository;
import com.mentiaz.mentiaz.auth.security.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String userId = jwtService.extractUserId(token);
            UUID id = UUID.fromString(userId);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                userRepository.findById(id).ifPresent(user -> {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
                            Collections.emptyList());

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                });
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Invalid token: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error processing token: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

}
