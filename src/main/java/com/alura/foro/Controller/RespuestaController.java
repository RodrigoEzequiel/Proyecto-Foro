package com.alura.foro.Controller;

import com.alura.foro.Errores.BadRequestException;
import com.alura.foro.Respuesta.Respuesta;
import com.alura.foro.Respuesta.NewRespuestaDto;
import com.alura.foro.Respuesta.RespuestaDto;
import com.alura.foro.Respuesta.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {
    @Autowired
    private RespuestaService respuestaService;

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
    @GetMapping
    public ResponseEntity listarTodas(){
        List<Respuesta> todasLasRespuestas = respuestaService.listarTodas();
        return ResponseEntity.ok(todasLasRespuestas);
    }
    @PatchMapping("/{id}")
    public ResponseEntity marcarSolucion(@PathVariable Long id) throws BadRequestException {
        RespuestaDto solucion = respuestaService.marcarSolucion(id);
        return ResponseEntity.ok(solucion);
    }
}
