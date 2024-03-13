package com.nico.config;

import com.nico.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig { //Clase que va a contener la cadena de filtros


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;


    //Método a fin de restringir el acceso a las rutas
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Este método retorna el http siempre y cuando pase por una serie de filtros
        return http
                //desabilitamos la conf csrf que habilita por defecto spring boot
                //Cross-Site Request Forgery Es una medida de seguridad que se utiliza para agregar
                // a las solicitudes Post una autentificacion basada en un token csrf valido
                .csrf(csrf -> csrf.disable())
                //1er filtro --> determina rutas privadas o protegidas
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                // Todos los request que macheen con la ruta /auth van a ser publicos
                                .requestMatchers("/auth/**").permitAll()
                                // A cualquier otro request le voy a pedir autentificacion
                                .anyRequest().authenticated()
                        //A continuacion invocamos al login por defecto de springSecurity
               // .formLogin(withDefaults())
                )
                // inhabilitamos las sesiones
                .sessionManagement(sessionManager ->
                        //que no utilice la politica de gestion de sesiones
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) //agregamos el filtro relacionado a jwtAuthenticationFilter
                .build();
    }
}
