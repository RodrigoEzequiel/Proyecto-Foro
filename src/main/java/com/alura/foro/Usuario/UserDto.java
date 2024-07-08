package com.alura.foro.Usuario;

public record UserDto(String nombre,
                      String apellido,
                      String email) {
    public static UserDto convertirUsuarioEnDto(Usuario usuario){
        return new UserDto(usuario.getNombre(), usuario.getApellido(), usuario.getLogin());
    }
}
