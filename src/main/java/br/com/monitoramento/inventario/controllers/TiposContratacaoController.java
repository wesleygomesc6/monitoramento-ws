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

import br.com.monitoramento.inventario.dtos.TipoContratacaoDto;
import br.com.monitoramento.inventario.forms.TipoContratacaoForm;
import br.com.monitoramento.inventario.models.TipoContratacao;
import br.com.monitoramento.inventario.repositories.TipoContratacaoRepository;
import io.swagger.annotations.Api;

@Api(tags = "Inventário / Tipos de Contratação")
@RestController
@RequestMapping("/inventario/tipos-contratacao")
public class TiposContratacaoController {

	@Autowired
	private TipoContratacaoRepository tipoContratacaoRepository;

	@GetMapping
	@Cacheable(value = "listaTiposContratacao")
	public Page<TipoContratacaoDto> lista(@RequestParam(required = false) String nome, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		
		if (nome == null) {
			System.out.println(paginacao);
			Page<TipoContratacao> tiposContratacao = tipoContratacaoRepository.findAll(paginacao);
			return TipoContratacaoDto.converter(tiposContratacao);
		} else {
			Page<TipoContratacao> tiposContratacao = tipoContratacaoRepository.findByNome(nome, paginacao);
			return TipoContratacaoDto.converter(tiposContratacao);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaTiposContratacao", allEntries = true)
	public ResponseEntity<TipoContratacaoDto> cadastrar(@RequestBody @Valid TipoContratacaoForm form, UriComponentsBuilder uriBuilder) {
		TipoContratacao tipoContratacao = form.converter();
		tipoContratacaoRepository.save(tipoContratacao);
		
		URI uri = uriBuilder.path("/tipos-vm/{id}").buildAndExpand(tipoContratacao.getId()).toUri();
		return ResponseEntity.created(uri).body(new TipoContratacaoDto(tipoContratacao));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TipoContratacaoDto> detalhar(@PathVariable Long id) {
		Optional<TipoContratacao> tipoContratacao = tipoContratacaoRepository.findById(id);
		if (tipoContratacao.isPresent()) {
			return ResponseEntity.ok(new TipoContratacaoDto(tipoContratacao.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaTiposContratacao", allEntries = true)
	public ResponseEntity<TipoContratacaoDto> atualizar(@PathVariable Long id, @RequestBody @Valid TipoContratacaoForm form) {
		Optional<TipoContratacao> optional = tipoContratacaoRepository.findById(id);
		if (optional.isPresent()) {
			TipoContratacao tipoContratacao = form.update(id, tipoContratacaoRepository);
			return ResponseEntity.ok(new TipoContratacaoDto(tipoContratacao));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaTiposContratacao", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<TipoContratacao> optional = tipoContratacaoRepository.findById(id);
		if (optional.isPresent()) {
			tipoContratacaoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
