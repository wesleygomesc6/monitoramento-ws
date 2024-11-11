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

import br.com.monitoramento.inventario.dtos.GerenciaDto;
import br.com.monitoramento.inventario.forms.GerenciaForm;
import br.com.monitoramento.inventario.models.Gerencia;
import br.com.monitoramento.inventario.repositories.GerenciaRepository;
import io.swagger.annotations.Api;

@Api(tags = "Inventário / Gerências")
@RestController
@RequestMapping("/inventario/gerencias")
public class GerenciasController {

	@Autowired
	private GerenciaRepository gerenciaRepository;

	@GetMapping
	@Cacheable(value = "listaGerencias")
	public Page<GerenciaDto> lista(@RequestParam(required = false) String nome, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		
		if (nome == null) {
			System.out.println(paginacao);
			Page<Gerencia> gerencias = gerenciaRepository.findAll(paginacao);
			return GerenciaDto.converter(gerencias);
		} else {
			Page<Gerencia> gerencias = gerenciaRepository.findByNome(nome, paginacao);
			return GerenciaDto.converter(gerencias);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaGerencias", allEntries = true)
	public ResponseEntity<GerenciaDto> cadastrar(@RequestBody @Valid GerenciaForm form, UriComponentsBuilder uriBuilder) {
		Gerencia gerencia = form.converter();
		gerenciaRepository.save(gerencia);
		
		URI uri = uriBuilder.path("/tipos-vm/{id}").buildAndExpand(gerencia.getId()).toUri();
		return ResponseEntity.created(uri).body(new GerenciaDto(gerencia));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GerenciaDto> detalhar(@PathVariable Long id) {
		Optional<Gerencia> gerencia = gerenciaRepository.findById(id);
		if (gerencia.isPresent()) {
			return ResponseEntity.ok(new GerenciaDto(gerencia.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaGerencias", allEntries = true)
	public ResponseEntity<GerenciaDto> atualizar(@PathVariable Long id, @RequestBody @Valid GerenciaForm form) {
		Optional<Gerencia> optional = gerenciaRepository.findById(id);
		if (optional.isPresent()) {
			Gerencia gerencia = form.update(id, gerenciaRepository);
			return ResponseEntity.ok(new GerenciaDto(gerencia));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaGerencias", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Gerencia> optional = gerenciaRepository.findById(id);
		if (optional.isPresent()) {
			gerenciaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
