package com.alura.foro.Respuesta;

import com.alura.foro.Errores.BadRequestException;
import com.alura.foro.Topicos.Topico;
import com.alura.foro.Topicos.TopicoRepository;
import com.alura.foro.Topicos.TopicoStatus;
import com.alura.foro.Usuario.UserRepository;
import com.alura.foro.Usuario.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RespuestaService {
    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UserRepository userRepository;

    public RespuestaDto nuevaRespuesta(NewRespuestaDto newRespuestaDto) throws EntityNotFoundException {

        Respuesta nueva = new Respuesta();
        nueva.setMensaje(newRespuestaDto.mensaje());
        Topico buscado = topicoRepository.findById(newRespuestaDto.id_topico()).orElse(null);
        if (buscado == null) throw new EntityNotFoundException("no se encontro el topico con el id " + newRespuestaDto.id_topico());
        Usuario author = (Usuario) userRepository.findByLogin(newRespuestaDto.author());
        if (author!=null) {
            nueva.setAuthor(author);
        }else throw new EntityNotFoundException("no existe el autor");

        if (buscado.getStatus()==TopicoStatus.SIN_RESPUESTA)
            buscado.setStatus(TopicoStatus.ABIERTO);
        nueva.setTopico(buscado);
        nueva=respuestaRepository.save(nueva);
        return RespuestaDto.convertirRespuestaEnDto(nueva);
    }

    public RespuestaDto buscarPorId(Long id) throws BadRequestException {
        Respuesta encontrada = respuestaRepository.findById(id).orElse(null);
        if (encontrada==null)
            throw new BadRequestException("no se encontro una respuesta con el id " + id);
        return RespuestaDto.convertirRespuestaEnDto(encontrada);
    }

    public void eliminarPorId(Long id) {
        respuestaRepository.deleteById(id);
    }

    public RespuestaDto marcarSolucion(Long id) throws BadRequestException {
        Respuesta respuesta = respuestaRepository.findById(id).orElse(null);
        if (respuesta != null){
            respuesta.setSolucion(true);
            Topico solucionado = respuesta.getTopico();
            solucionado.setStatus(TopicoStatus.RESUELTO);
            return RespuestaDto.convertirRespuestaEnDto(respuestaRepository.save(respuesta));
        }
        throw new BadRequestException("no se encontro una respuesta con el id " + id);
    }

    public Page<Respuesta> listarRespuestaPorTopicoId(Long id, Pageable pagina) {
        Page<Respuesta> encontradas = respuestaRepository.listarRespuestaPorTopicoId(id,pagina);
        return encontradas;
    }


    public RespuestaDto actualizarRespuesta(Long id, UpdateRespuestaDto datosActualizados) throws BadRequestException {
        Respuesta actualizada = respuestaRepository.findById(id).orElse(null);
        if (actualizada==null) throw new BadRequestException("no se encontro una respuesta con el id " + id);
        actualizada.setMensaje(datosActualizados.mensaje());
        return RespuestaDto.convertirRespuestaEnDto(respuestaRepository.save(actualizada)) ;
    }
}
