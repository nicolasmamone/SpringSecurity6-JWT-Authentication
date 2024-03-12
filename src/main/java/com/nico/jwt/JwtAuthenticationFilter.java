package com.nico.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//OncePerRequestFilter -> se utiliza para crear filtros personalizados
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    //metodo que va realizar todos los filtros relacionados al token
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String token = getTokenFromRequest(request);

        if (token == null){ // Si es null hacemos un registro
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
    //Metodo que nos va a devolver el token
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader= request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }
        return null;
    }
}
