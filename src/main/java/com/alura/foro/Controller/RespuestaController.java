package com.alura.foro.Controller;

import com.alura.foro.Respuesta.Respuesta;
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
    public ResponseEntity guardarRespuesta(@RequestBody RespuestaDto respuestaDto){
        Respuesta nueva = respuestaService.nuevaRespuesta(respuestaDto);
        return ResponseEntity.ok(nueva);

    }
    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable Long id){
        Respuesta encontrada = respuestaService.buscarPorId(id);
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
    public ResponseEntity marcarSolucion(@PathVariable Long id){
        Respuesta solucion = respuestaService.marcarSolucion(id);
        return ResponseEntity.ok(solucion);
    }
}
