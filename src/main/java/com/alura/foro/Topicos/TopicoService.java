package com.alura.foro.Topicos;

import com.alura.foro.Categoria.CategoriaRepository;
import com.alura.foro.Categoria.Categoria;
import com.alura.foro.Errores.BadRequestException;
import com.alura.foro.Respuesta.Respuesta;
import com.alura.foro.Respuesta.RespuestaRepository;
import com.alura.foro.Usuario.UserRepository;
import com.alura.foro.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    private CategoriaRepository categoriaRepository;


    public TopicoDto crearTopico(NewTopicoDto nuevoDto) throws BadRequestException {
        Topico nuevoTopico = new Topico();
        nuevoTopico.setMensaje(nuevoDto.mensaje());
        nuevoTopico.setTitulo(nuevoDto.titulo());
        nuevoTopico.setStatus(TopicoStatus.SIN_RESPUESTA);
        Usuario author = (Usuario) userRepository.findByLogin(nuevoDto.author());
        nuevoTopico.setAuthor(author);
        Optional<Categoria> categoria = categoriaRepository.findById(nuevoDto.id_categoria());
        if (categoria.isEmpty())
            throw new BadRequestException("no se encontro una categoria por id " + nuevoDto.id_categoria());
        nuevoTopico.setCategoria(categoria.get());
        try {
            nuevoTopico = topicoRepository.save(nuevoTopico);
            TopicoDto nuevo = TopicoDto.convertirTopicoEnDto(nuevoTopico);
            return  nuevo;
        } catch (Exception ex) {
            throw new BadRequestException("ya existe un topico con ese nombre");
        }

    }

    public TopicoDto buscarPorId(Long id) {
        Optional<Topico> encontrada = topicoRepository.findById(id);
        if (encontrada.isPresent())
            return TopicoDto.convertirTopicoEnDto(encontrada.get());
        else return null;
    }

    public void eliminarPorId(Long id) {
        List<Respuesta> listadas = respuestaRepository.listarRespuestaPorTopicoId(id);
        respuestaRepository.deleteAll(listadas);
        topicoRepository.deleteById(id);
    }

    public Page<TopicoFullDto> listarTodos(Pageable pagina) {
        return topicoRepository.listarTopicosComoDto(pagina);
    }
    public TopicoDto actualizarTopico(Long id,ActualizarTopicoDto datosActualizados) throws BadRequestException {
        Optional<Topico> original = topicoRepository.findById(id);
        if (original.isPresent()){
            original.get().setMensaje(datosActualizados.mensaje());
            original.get().setTitulo(datosActualizados.titulo());
            try {
                return TopicoDto.convertirTopicoEnDto(topicoRepository.save(original.get()));
            } catch (Exception e) {
                throw new BadRequestException("ya existe un topico con ese nombre");
            }
        }else{
            throw new BadRequestException("no existe un topico con el id " + id);
        }
    }
    public List<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }


}
