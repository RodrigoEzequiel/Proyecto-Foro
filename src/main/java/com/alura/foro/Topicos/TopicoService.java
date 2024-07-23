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
        Usuario author = (Usuario) userRepository.findByLogin(nuevoDto.author());
        if (author == null) throw new BadRequestException("No existe un usuario con ese email");
        Optional<Categoria> categoria = categoriaRepository.findById(nuevoDto.id_categoria());
        if (categoria.isEmpty())
            throw new BadRequestException("no se encontro una categoria por id " + nuevoDto.id_categoria());

        Topico nuevoTopico = new Topico();
        nuevoTopico.setCategoria(categoria.get());
        nuevoTopico.setMensaje(nuevoDto.mensaje());
        nuevoTopico.setTitulo(nuevoDto.titulo());
        nuevoTopico.setStatus(TopicoStatus.SIN_RESPUESTA);
        nuevoTopico.setAuthor(author);

        try {
            nuevoTopico = topicoRepository.save(nuevoTopico);
            TopicoDto nuevo = TopicoDto.convertirTopicoEnDto(nuevoTopico);
            return  nuevo;
        } catch (Exception ex) {
            throw new BadRequestException("ya existe un topico con ese nombre");
        }

    }

    public TopicoDto buscarPorId(Long id) throws BadRequestException {
        Topico encontrada = topicoRepository.findById(id).orElse(null);
        if (encontrada == null) throw new BadRequestException("No existe un topico con el id " + id);
        return TopicoDto.convertirTopicoEnDto(encontrada);
    }

    public void eliminarPorId(Long id) {
        List<Respuesta> listadas = respuestaRepository.listarRespuestaPorTopicoId(id);
        respuestaRepository.deleteAll(listadas);
        topicoRepository.deleteById(id);
    }

    public Page<TopicoFullDto> listarTodos(Pageable pagina) {
        return topicoRepository.listarTopicosComoDto(pagina);
    }

    public TopicoDto actualizarTopico(Long id, UpdateTopicoDto datosActualizados) throws BadRequestException {
        Topico original = topicoRepository.findById(id).orElse(null);
        if (original == null) throw new BadRequestException("no existe un topico con el id " + id);

        original.setMensaje(datosActualizados.mensaje());
        original.setTitulo(datosActualizados.titulo());
        try {
            return TopicoDto.convertirTopicoEnDto(topicoRepository.save(original));
        } catch (Exception e) {
             throw new BadRequestException("ocurrio un problema guardando el topico en la base de datos");
        }
    }

    public List<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }

}
