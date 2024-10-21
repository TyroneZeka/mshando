package org.mufasadev.mshando.core.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mufasadev.mshando.core.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        logger.debug("AuthTokenFilter called for URI: {}", request.getRequestURI());
        if(request.getRequestURI().startsWith("api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug("AuthTokenFilter: No Bearer in request {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);
        logger.debug("AuthTokenFilter: Username: {}", username);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            logger.debug("AuthTokenFilter: Token: {}", jwt);
            if(jwtService.isTokenValid(jwt, userDetails)) {
                try {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                catch(Exception e) {
                    logger.error("AuthTokenFilter: UsernamePasswordError {}", e.getMessage());
                    throw new LockedException(e.getMessage());
                }
                logger.debug("AuthTokenFilter: Authentication success");
            }
        }
        filterChain.doFilter(request, response);
        //TODO Catch token expired exception.
    }

}