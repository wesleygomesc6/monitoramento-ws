package br.com.monitoramento.inventario.controllers;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.monitoramento.inventario.dtos.GrupoDto;
import br.com.monitoramento.inventario.forms.GrupoForm;
import br.com.monitoramento.inventario.models.Grupo;
import br.com.monitoramento.inventario.repositories.GrupoRepository;
import io.swagger.annotations.Api;

@Api(tags = "Inventário / Grupos")
@RestController
@RequestMapping("/inventario/grupos")
public class GruposController {

	@Autowired
	private GrupoRepository grupoRepository;

	@GetMapping
	@Cacheable(value = "listaGrupos")
	public Page<GrupoDto> lista(@RequestParam(required = false) String nome, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		
		if (nome == null) {
			System.out.println(paginacao);
			Page<Grupo> grupos = grupoRepository.findAll(paginacao);
			return GrupoDto.converter(grupos);
		} else {
			Page<Grupo> grupos = grupoRepository.findByNome(nome, paginacao);
			return GrupoDto.converter(grupos);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaGrupos", allEntries = true)
	public ResponseEntity<GrupoDto> cadastrar(@RequestBody @Valid GrupoForm form, UriComponentsBuilder uriBuilder) {
		Grupo grupo = form.converter();
		grupoRepository.save(grupo);
		
		URI uri = uriBuilder.path("/tipos-vm/{id}").buildAndExpand(grupo.getId()).toUri();
		return ResponseEntity.created(uri).body(new GrupoDto(grupo));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GrupoDto> detalhar(@PathVariable Long id) {
		Optional<Grupo> grupo = grupoRepository.findById(id);
		if (grupo.isPresent()) {
			return ResponseEntity.ok(new GrupoDto(grupo.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaGrupos", allEntries = true)
	public ResponseEntity<GrupoDto> atualizar(@PathVariable Long id, @RequestBody @Valid GrupoForm form) {
		Optional<Grupo> optional = grupoRepository.findById(id);
		if (optional.isPresent()) {
			Grupo grupo = form.update(id, grupoRepository);
			return ResponseEntity.ok(new GrupoDto(grupo));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaGrupos", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Grupo> optional = grupoRepository.findById(id);
		if (optional.isPresent()) {
			grupoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
