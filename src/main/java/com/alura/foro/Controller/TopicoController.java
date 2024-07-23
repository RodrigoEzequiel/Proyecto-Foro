package com.alura.foro.Controller;

import com.alura.foro.Categoria.Categoria;
import com.alura.foro.Errores.BadRequestException;
import com.alura.foro.Respuesta.Respuesta;
import com.alura.foro.Respuesta.RespuestaDto;
import com.alura.foro.Respuesta.RespuestaService;
import com.alura.foro.Topicos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Topico Controller", description = "Todos los metodos para trabajar con Topicos")
@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoService topicoService;

    @Autowired
    private RespuestaService respuestaService;

    @Operation(summary = "Endpoint para crear un Topico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El topico se creo exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TopicoDto.class)) }),
            @ApiResponse(responseCode = "400", description = "No se pudo crear el topico",
                    content = @Content), })

    @PostMapping
    public ResponseEntity crearTopico(@RequestBody @Valid NewTopicoDto topicoDto) throws BadRequestException {
        TopicoDto nuevoTopico = topicoService.crearTopico(topicoDto);
        return ResponseEntity.ok(nuevoTopico);
    }

    @Operation(summary = "Endpoint para buscar un topico por id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "devuelve el topico exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TopicoDto.class)) }),
            @ApiResponse(responseCode = "400", description = "no se pudo encontrar el topico",
                    content = @Content), })
    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable Long id) throws BadRequestException {
        TopicoDto encontrada = topicoService.buscarPorId(id);
        return ResponseEntity.ok(encontrada);
    }

    @Operation(summary = "Endpoint para eliminar un topico por id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El topico se elimino exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPorId(@PathVariable Long id){
        topicoService.eliminarPorId(id);
        return ResponseEntity.ok("se elimino con exito");
    }

    @Operation(summary = "Endpoint para listar los topicos paginados ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listan los topicos  exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HashMap.class)) })
    })
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

    @Operation(summary = "Endpoint para listar las respuestas paginadas de un topico ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listan las respuestas exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HashMap.class)) })
    })
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

    @Operation(summary = "Endpoint para actualizar un topico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El topico se actualizo exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TopicoDto.class)) }),
            @ApiResponse(responseCode = "400", description = "no se pudo encontrar el topico",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "ya existe un topico con el mismo titulo",
                    content = @Content),
    })
    @PatchMapping("/{id}")
    public ResponseEntity actualizarTopico(@PathVariable Long id,
                                           @RequestBody @Valid UpdateTopicoDto datosActualizados) throws BadRequestException {
        TopicoDto actualizado = topicoService.actualizarTopico(id,datosActualizados);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Endpoint para listar las categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listan las  categorias exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)) })
    })
    @GetMapping("/categorias")
    public ResponseEntity listarCategorias(){
        List<Categoria> encontradas = topicoService.listarCategorias();
        return ResponseEntity.ok(encontradas);
    }
}
