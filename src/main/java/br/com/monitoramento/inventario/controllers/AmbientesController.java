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

import br.com.monitoramento.inventario.dtos.AmbienteDto;
import br.com.monitoramento.inventario.forms.AmbienteForm;
import br.com.monitoramento.inventario.models.Ambiente;
import br.com.monitoramento.inventario.repositories.AmbienteRepository;
import io.swagger.annotations.Api;

@Api(tags = "Inventário / Ambientes")
@RestController
@RequestMapping("/inventario/ambientes")
public class AmbientesController {

	@Autowired
	private AmbienteRepository ambienteRepository;

	@GetMapping
	@Cacheable(value = "listaAmbientes")
	public Page<AmbienteDto> lista(@RequestParam(required = false) String nome, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		
		if (nome == null) {
			System.out.println(paginacao);
			Page<Ambiente> ambientes = ambienteRepository.findAll(paginacao);
			return AmbienteDto.converter(ambientes);
		} else {
			Page<Ambiente> ambientes = ambienteRepository.findByNome(nome, paginacao);
			return AmbienteDto.converter(ambientes);
		}
	}
	
	/*@PostMapping
	@Transactional
	@CacheEvict(value = "listaAmbientes", allEntries = true)
	public ResponseEntity<AmbienteDto> cadastrar(@RequestBody @Valid AmbienteForm form, UriComponentsBuilder uriBuilder) {
		Ambiente ambiente = form.converter();
		ambienteRepository.save(ambiente);
		
		URI uri = uriBuilder.path("/tipos-vm/{id}").buildAndExpand(ambiente.getId()).toUri();
		return ResponseEntity.created(uri).body(new AmbienteDto(ambiente));
	}*/
	
	@GetMapping("/{id}")
	public ResponseEntity<AmbienteDto> detalhar(@PathVariable Long id) {
		Optional<Ambiente> ambiente = ambienteRepository.findById(id);
		if (ambiente.isPresent()) {
			return ResponseEntity.ok(new AmbienteDto(ambiente.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	/*@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaAmbientes", allEntries = true)
	public ResponseEntity<AmbienteDto> atualizar(@PathVariable Long id, @RequestBody @Valid AmbienteForm form) {
		Optional<Ambiente> optional = ambienteRepository.findById(id);
		if (optional.isPresent()) {
			Ambiente ambiente = form.update(id, ambienteRepository);
			return ResponseEntity.ok(new AmbienteDto(ambiente));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaAmbientes", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Ambiente> optional = ambienteRepository.findById(id);
		if (optional.isPresent()) {
			ambienteRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}*/
}
