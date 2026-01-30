package com.severalabs.ias.security.jwt;

import com.severalabs.ias.security.config.IasUserDetails;
import com.severalabs.ias.security.config.IasUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final IasUserDetailsService iasUserDetailsService;

    @Override
    protected void doFilterInternal( @NonNull
            HttpServletRequest request, @NonNull HttpServletResponse response,
                                     @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = jwtService.getJwtTokenFromRequest(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String email = jwtService.getUsernameFromToken(token);

        if (jwtService.isTokenValid(token, email)) {
            IasUserDetails currentUser =
                    (IasUserDetails) iasUserDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authUser =
                    new UsernamePasswordAuthenticationToken(
                            currentUser, null, currentUser.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authUser);
        }
        filterChain.doFilter(request, response);
    }
}
