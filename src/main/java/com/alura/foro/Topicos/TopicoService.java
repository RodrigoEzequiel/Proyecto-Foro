package com.alura.foro.Topicos;

import com.alura.foro.Respuesta.Respuesta;
import com.alura.foro.Respuesta.RespuestaRepository;
import com.alura.foro.Usuario.UserRepository;
import com.alura.foro.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RespuestaRepository respuestaRepository;

    public Topico crearTopico(TopicoDto nuevoDto){
        Topico nuevoTopico = new Topico();
        nuevoTopico.setMensaje(nuevoDto.mensaje());
        nuevoTopico.setTitulo(nuevoDto.titulo());
        Usuario author = (Usuario) userRepository.findByLogin(nuevoDto.author());
        nuevoTopico.setAuthor(author);
        nuevoTopico = topicoRepository.save(nuevoTopico);
        return nuevoTopico ;
    }

    public Topico buscarPorId(Long id) {
        Optional<Topico> encontrada = topicoRepository.findById(id);
        if (encontrada.isPresent())
            return encontrada.get();
        else return null;
    }

    public void eliminarPorId(Long id) {
        List<Respuesta> listadas = respuestaRepository.listarRespuestaPorTopicoId(id);
        respuestaRepository.deleteAll(listadas);
        topicoRepository.deleteById(id);
    }

    public List<Topico> listarTodas() {
        return topicoRepository.findAll();
    }

}
