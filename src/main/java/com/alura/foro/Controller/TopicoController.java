package com.alura.foro.Controller;

import com.alura.foro.Respuesta.Respuesta;
import com.alura.foro.Respuesta.RespuestaService;
import com.alura.foro.Topicos.Topico;
import com.alura.foro.Topicos.TopicoDto;
import com.alura.foro.Topicos.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoService topicoService;

    @Autowired
    private RespuestaService respuestaService;

    @PostMapping
    public ResponseEntity crearTopico(@RequestBody TopicoDto topicoDto){
        Topico nuevoTopico = topicoService.crearTopico(topicoDto);
        return ResponseEntity.ok(nuevoTopico);
    }
    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable Long id){
        Topico encontrada = topicoService.buscarPorId(id);
        return ResponseEntity.ok(encontrada);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPorId(@PathVariable Long id){
        topicoService.eliminarPorId(id);
        return ResponseEntity.ok("se elimino con exito");
    }
    @GetMapping
    public ResponseEntity listarTodas(){
        List<Topico> todosLosTopicos = topicoService.listarTodas();
        return ResponseEntity.ok(todosLosTopicos);
    }
    @GetMapping("/{id}/respuestas")
    public ResponseEntity listarRespuestasPorTopicoId(@PathVariable Long id){
        List<Respuesta> encontradas = respuestaService.listarRespuestaPorTopicoId(id);
        return ResponseEntity.ok(encontradas);
    }

}
