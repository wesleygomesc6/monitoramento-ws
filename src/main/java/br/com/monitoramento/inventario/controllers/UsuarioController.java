package br.com.monitoramento.inventario.controllers;

import br.com.monitoramento.inventario.dtos.UsuarioDto;
import br.com.monitoramento.inventario.forms.UsuarioForm;
import br.com.monitoramento.inventario.models.Usuario;
import br.com.monitoramento.inventario.repositories.UsuarioRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@Api(tags = "Inventário / Usuarios")
@RestController
@RequestMapping("/inventario/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    @Cacheable(value = "listaUsuarios")
    public Page<UsuarioDto> lista(@RequestParam(required = false) String login,
                                  @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable paginacao) {

        if (login == null) {
            Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
            return UsuarioDto.converter(usuarios);
        } else {
            Page<Usuario> usuairos = usuarioRepository.findByLogin(login, paginacao);
            return UsuarioDto.converter(usuairos);
        }
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "listarUsuarios", allEntries = true)
    public ResponseEntity<?> cadastrar(@RequestBody @Valid UsuarioForm form, UriComponentsBuilder uriBuilder) {
        Usuario usuario = form.converter();
        usuarioRepository.save(usuario);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
    }
}
