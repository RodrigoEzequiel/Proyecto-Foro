package com.alura.foro.Security;

import com.alura.foro.Usuario.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
        return httpSecurity.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
                //csrf es una configuracion de seguridad que previene el ataque a las request para extraer informacion sensible
                //lo deshabilitamos para poder usar el postman, pero cuando tengamos un servidor con formularios de fronend
                // hay que quitar esta linea y configurar el origen de las peticiones CORS
                //configuramos los endpoint segun el tipo de acceso segun el rol
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST,"/usuarios/registro").permitAll();
                    auth.requestMatchers(HttpMethod.POST,"/usuarios/login").permitAll();
                    auth.anyRequest().authenticated();
                })
                //configuramos si el servidor guarda o no informacion de los usuarios unas vez logueados
                .sessionManagement( session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // este setea la autorizacion basica
                //cuando se implemente JWT esta linea debe quitarse
                .addFilterBefore(new SecurityFilter(tokenService,userAuthenticationService), UsernamePasswordAuthenticationFilter.class)
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
