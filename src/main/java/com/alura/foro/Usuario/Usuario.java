package com.alura.foro.Usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Email
        @Column(name = "email", unique = true,nullable = false)
        private String login;

        @NotBlank
        @Size(min = 8, max = 256)
        @Column(name = "password",nullable = false)
        private String clave;

        @NotBlank
        @Column(nullable = false)
        private String nombre;

        @NotBlank
        @Column(nullable = false)
        private String apellido;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of( new SimpleGrantedAuthority("ROLE_USER"));
        }

        @Override
        public String getPassword() {
            return clave;
        }

        @Override
        public String getUsername() {
            return login;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

