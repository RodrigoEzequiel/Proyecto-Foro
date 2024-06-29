package com.alura.foro.Respuesta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RespuestaService {
    @Autowired
    private RespuestaRepository respuestaRepository;

    public Respuesta nuevaRespuesta(RespuestaDto respuestaDto){
        Respuesta nueva = new Respuesta();
        nueva.setMensaje(respuestaDto.mensaje());
        //nueva.setAuthor(respuestaDto.author());
        nueva.setSolucion(respuestaDto.solucion());
        nueva=respuestaRepository.save(nueva);
        return nueva;
    }

    public Respuesta buscarPorId(Long id) {
        Optional<Respuesta> encontrada = respuestaRepository.findById(id);
        if (encontrada.isPresent())
            return encontrada.get();
        else return null;
    }

    public void eliminarPorId(Long id) {
        respuestaRepository.deleteById(id);
    }

    public List<Respuesta> listarTodas() {
        return respuestaRepository.findAll();
    }

    public Respuesta marcarSolucion(Long id) {
        Respuesta respuesta = this.buscarPorId(id);
        if (respuesta != null){
            respuesta.setSolucion(true);
            return respuestaRepository.save(respuesta);
        }
        return null;
    }
}
