package com.alura.foro.Controller;

import com.alura.foro.Security.DatosJWTToken;
import com.alura.foro.Security.TokenService;
import com.alura.foro.Usuario.DatosAutenticacionUsuarios;
import com.alura.foro.Usuario.UserRepository;
import com.alura.foro.Usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuarios datosAutenticacionUsuarios) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuarios.login(),datosAutenticacionUsuarios.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTToken));
    }
    @PostMapping("/registro")
    public ResponseEntity registrarUsuario(@RequestBody @Valid DatosAutenticacionUsuarios datosAutenticacionUsuarios){
        String contraseñaEncriptada = passwordEncoder.encode(datosAutenticacionUsuarios.clave());
        Usuario nuevoUsuario = new Usuario(null,datosAutenticacionUsuarios.login(),contraseñaEncriptada);
        //TODO: verificar q el nombre de usuario ya exista!
        nuevoUsuario = userRepository.save(nuevoUsuario);
        //TODO: usar un dto para q no devuelva todos los datos
        return ResponseEntity.ok(nuevoUsuario) ;
    }
    @GetMapping("/test")
    public ResponseEntity test(){
        return ResponseEntity.ok("hola mundo");
    }
}
