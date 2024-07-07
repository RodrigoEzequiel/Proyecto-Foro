package com.alura.foro.Controller;

import com.alura.foro.Categoria.Categoria;
import com.alura.foro.Errores.BadRequestException;
import com.alura.foro.Respuesta.Respuesta;
import com.alura.foro.Respuesta.RespuestaDto;
import com.alura.foro.Respuesta.RespuestaService;
import com.alura.foro.Topicos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoService topicoService;

    @Autowired
    private RespuestaService respuestaService;

    @PostMapping
    public ResponseEntity crearTopico(@RequestBody NewTopicoDto topicoDto) throws BadRequestException {
        TopicoDto nuevoTopico = topicoService.crearTopico(topicoDto);
        return ResponseEntity.ok(nuevoTopico);
    }
    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable Long id){
        TopicoDto encontrada = topicoService.buscarPorId(id);
        return ResponseEntity.ok(encontrada);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPorId(@PathVariable Long id){
        topicoService.eliminarPorId(id);
        return ResponseEntity.ok("se elimino con exito");
    }
    @GetMapping
    public ResponseEntity listarTodas(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "2") int size){
        Pageable pagina = PageRequest.of(page,size);
        Page<TopicoFullDto> todosLosTopicos = topicoService.listarTodos(pagina);
        Map<String,Object> response = new HashMap<>();
        response.put("Topicos totales",todosLosTopicos.getTotalElements());
        response.put("Pagina actual", todosLosTopicos.getNumber());
        response.put("Total de paginas", todosLosTopicos.getTotalPages());
        response.put("Resultados por pagina", todosLosTopicos.getSize());
        response.put("Topicos",todosLosTopicos.get());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}/respuestas")
    public ResponseEntity listarRespuestasPorTopicoId(@PathVariable Long id,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "2") int size){
        Pageable pagina = PageRequest.of(page,size);
        Page<Respuesta> encontradas = respuestaService.listarRespuestaPorTopicoId(id,pagina);
        Map<String,Object> response = new HashMap<>();
        response.put("Respuestas totales",encontradas.getTotalElements());
        response.put("Pagina actual", encontradas.getNumber());
        response.put("Total de paginas", encontradas.getTotalPages());
        response.put("Resultados por pagina", encontradas.getSize());
        List<RespuestaDto> convertidas = encontradas.get().toList().stream().map(RespuestaDto::convertirRespuestaEnDto).toList();
        response.put("Respuestas",convertidas);
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/{id}")
    public ResponseEntity actualizarTopico(@PathVariable Long id,@RequestBody ActualizarTopicoDto datosActualizados) throws BadRequestException {
        TopicoDto actualizado = topicoService.actualizarTopico(id,datosActualizados);
        return ResponseEntity.ok(actualizado);
    }
    @GetMapping("/categorias")
    public ResponseEntity listarCategorias(){
        List<Categoria> encontradas = topicoService.listarCategorias();
        return ResponseEntity.ok(encontradas);
    }
}
