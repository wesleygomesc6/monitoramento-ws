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

import br.com.monitoramento.inventario.dtos.ResponsavelDto;
import br.com.monitoramento.inventario.forms.ResponsavelForm;
import br.com.monitoramento.inventario.models.Responsavel;
import br.com.monitoramento.inventario.repositories.ResponsavelRepository;
import io.swagger.annotations.Api;

@Api(tags = "Inventário / Responsáveis")
@RestController
@RequestMapping("/inventario/responsaveis")
public class ResponsaveisController {

	@Autowired
	private ResponsavelRepository responsavelRepository;

	@GetMapping
	@Cacheable(value = "listaResponsaveis")
	public Page<ResponsavelDto> lista(@RequestParam(required = false) String nome, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		
		if (nome == null) {
			System.out.println(paginacao);
			Page<Responsavel> responsaveis = responsavelRepository.findAll(paginacao);
			return ResponsavelDto.converter(responsaveis);
		} else {
			Page<Responsavel> responsaveis = responsavelRepository.findByNome(nome, paginacao);
			return ResponsavelDto.converter(responsaveis);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaResponsaveis", allEntries = true)
	public ResponseEntity<ResponsavelDto> cadastrar(@RequestBody @Valid ResponsavelForm form, UriComponentsBuilder uriBuilder) {
		Responsavel responsavel = form.converter();
		responsavelRepository.save(responsavel);
		
		URI uri = uriBuilder.path("/tipos-vm/{id}").buildAndExpand(responsavel.getId()).toUri();
		return ResponseEntity.created(uri).body(new ResponsavelDto(responsavel));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponsavelDto> detalhar(@PathVariable Long id) {
		Optional<Responsavel> responsavel = responsavelRepository.findById(id);
		if (responsavel.isPresent()) {
			return ResponseEntity.ok(new ResponsavelDto(responsavel.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaResponsaveis", allEntries = true)
	public ResponseEntity<ResponsavelDto> atualizar(@PathVariable Long id, @RequestBody @Valid ResponsavelForm form) {
		Optional<Responsavel> optional = responsavelRepository.findById(id);
		if (optional.isPresent()) {
			Responsavel responsavel = form.update(id, responsavelRepository);
			return ResponseEntity.ok(new ResponsavelDto(responsavel));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaResponsaveis", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Responsavel> optional = responsavelRepository.findById(id);
		if (optional.isPresent()) {
			responsavelRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
