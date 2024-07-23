package com.alura.foro.Security;

import com.alura.foro.Usuario.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        final String[] AUTH_WHITELIST = {
                "/usuarios/**",
                "/api-docs/**",
                "/swagger-ui/**",
        };
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                //csrf es una configuracion de seguridad que previene el ataque a las request para extraer informacion sensible
                //configuramos los endpoint segun el tipo de acceso segun el rol
                .authorizeHttpRequests(auth -> {
                    //auth.requestMatchers(HttpMethod.POST,"/usuarios/registro").permitAll();
                    auth.requestMatchers(AUTH_WHITELIST).permitAll();
                    auth.anyRequest().authenticated();
                })
                //configuramos si el servidor guarda o no informacion de los usuarios unas vez logueados
                .sessionManagement( session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                //filtro para el jwt
                .addFilterBefore(new JWTFilter(tokenService,userAuthenticationService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
