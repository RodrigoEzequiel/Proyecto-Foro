package com.alura.foro.Controller;

import com.alura.foro.Errores.BadRequestException;
import com.alura.foro.Errores.ErrorDto;
import com.alura.foro.Security.LoginResponse;
import com.alura.foro.Security.TokenService;
import com.alura.foro.Usuario.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuario Controller", description = "Todos los metodos para trabajar con usuarios")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Endpoint para loguear un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario logueo exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Registro incorrecto",
                    content = @Content), })
    @PostMapping("/login")
    public ResponseEntity autenticarUsuario(@RequestBody @Valid loginDto loginDto) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDto.login()
                , loginDto.clave());
        try{
            var usuarioAutenticado = authenticationManager.authenticate(authToken);
            var JWTToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
            return ResponseEntity.ok(new LoginResponse(loginDto.login(),JWTToken));
        }catch(AuthenticationException ex) {
            System.out.println(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("registro incorrecto"));
    }

    @PostMapping("/registro")
    public ResponseEntity registrarUsuario(@RequestBody @Valid NewUserDto nuevoUsuario) throws BadRequestException {
        String contraseñaEncriptada = passwordEncoder.encode(nuevoUsuario.password());
        Usuario registrado = new Usuario(null, nuevoUsuario.email(),contraseñaEncriptada,nuevoUsuario.nombre(),nuevoUsuario.apellido());
        try {
            registrado = userRepository.save(registrado);
        } catch (Exception e) {
            throw new BadRequestException("ocurrio un error registrando un usuario");
        }
        UserDto datosUsuario = UserDto.convertirUsuarioEnDto(registrado);
        return ResponseEntity.ok(datosUsuario) ;
    }

}
