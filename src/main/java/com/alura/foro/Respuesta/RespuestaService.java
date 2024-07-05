package com.alura.foro.Respuesta;

import com.alura.foro.Topicos.Topico;
import com.alura.foro.Topicos.TopicoService;
import com.alura.foro.Topicos.TopicoStatus;
import com.alura.foro.Usuario.UserRepository;
import com.alura.foro.Usuario.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RespuestaService {
    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private TopicoService topicoService;//TODO reemplazar por Repository para evitar referencia ciclica
    @Autowired
    private UserRepository userRepository;

    public Respuesta nuevaRespuesta(RespuestaDto respuestaDto) throws EntityNotFoundException {

        Respuesta nueva = new Respuesta();
        nueva.setMensaje(respuestaDto.mensaje());
        Topico buscado = topicoService.buscarPorId(respuestaDto.id_topico());
        Usuario author = (Usuario) userRepository.findByLogin(respuestaDto.author());
        nueva.setAuthor(author);
        if (buscado == null) throw new EntityNotFoundException("no se encontro el topico con el id " + respuestaDto.id_topico());
        if (buscado.getStatus()==TopicoStatus.SIN_RESPUESTA)
            buscado.setStatus(TopicoStatus.ABIERTO);
        nueva.setTopico(buscado);
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
            Topico solucionado = respuesta.getTopico();
            solucionado.setStatus(TopicoStatus.RESUELTO);
            return respuestaRepository.save(respuesta);
        }
        return null;
    }

    public List<Respuesta> listarRespuestaPorTopicoId(Long id) {
        List<Respuesta> encontradas = respuestaRepository.listarRespuestaPorTopicoId(id);
        return encontradas;
    }


}
