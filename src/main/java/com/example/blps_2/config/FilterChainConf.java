package com.example.blps_2.config;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.blps_2.security.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class FilterChainConf extends OncePerRequestFilter {
    private final JWTProvider jwtUtil;
    private final UserDetailsService userDetailsService;

    @Value("${BEARER}")
    private String JWT_START;

    @Autowired
    public FilterChainConf(JWTProvider jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (authHeader.isEmpty() || !authHeader.startsWith(JWT_START)) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = authHeader.substring(JWT_START.length()+1);
        System.out.println(jwt);
        System.out.println(jwt.isBlank());

        if (jwt.isBlank()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
            return;
        }

        try {
            var data = jwtUtil.validateTokenAndRetrieveClaim(jwt);
            var username = data.get("username");
            System.out.println(data);

            var userDetails = userDetailsService.loadUserByUsername(username);
            var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

            if (SecurityContextHolder.getContext().getAuthentication() == null)
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (JWTVerificationException | UsernameNotFoundException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
            System.out.println(jwt);
            return;
        }

        filterChain.doFilter(request, response);
    }
}

