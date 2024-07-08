package com.alura.foro.Security;

import com.alura.foro.Usuario.UserAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.io.IOException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {
        //OBETENER EL TOKEN
        String token = request.getHeader("Authorization");
        String usuario = null;
        if (token != null) {
            token = token.replace("Bearer ", "");
            usuario = tokenService.getSubject(token);
            System.out.println("Validando usuario...");
            System.out.println("token: " + token);
            System.out.println("usuario: " + usuario);
        }

        if (usuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //registramos el usuario logueado en el contexto de spring
            UserDetails userDetails = userAuthenticationService.loadUserByUsername(usuario);
            UsernamePasswordAuthenticationToken springToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            springToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(springToken);
            System.out.println("usuario logueado exitosamente");
        }
        filterChain.doFilter(request, response);
    }
}