package com.alura.foro.Controller;

import com.alura.foro.Errores.BadRequestException;
import com.alura.foro.Respuesta.*;
import com.alura.foro.Security.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Respuesta Controller", description = "Metodos para la Respuesta")
@RestController
@RequestMapping("/respuestas")
public class RespuestaController {
    @Autowired
    private RespuestaService respuestaService;

    @Operation(summary = "Endpoint para crear la Respuesta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La respuesta se creo exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "No pudo crearse la respuesta",
                    content = @Content), })

    @PostMapping
    public ResponseEntity guardarRespuesta(@RequestBody NewRespuestaDto newRespuestaDto){
        RespuestaDto nueva = respuestaService.nuevaRespuesta(newRespuestaDto);
        return ResponseEntity.ok(nueva);

    }
    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable Long id) throws BadRequestException {
        RespuestaDto encontrada = respuestaService.buscarPorId(id);
        return ResponseEntity.ok(encontrada);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPorId(@PathVariable Long id){
        respuestaService.eliminarPorId(id);
        return ResponseEntity.ok("se elimino con exito");
    }
    @PatchMapping("/marcar_solucion/{id}")
    public ResponseEntity marcarSolucion(@PathVariable Long id) throws BadRequestException {
        RespuestaDto solucion = respuestaService.marcarSolucion(id);
        return ResponseEntity.ok(solucion);
    }
    @PatchMapping("/{id}")
    public ResponseEntity actualizarRespuesta(@PathVariable Long id,@RequestBody UpdateRespuestaDto datosActualizados) throws BadRequestException {
        RespuestaDto actualizado = respuestaService.actualizarRespuesta(id,datosActualizados);
        return ResponseEntity.ok(actualizado);
    }
}
