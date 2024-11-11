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

//import br.com.monitoramento.inventario.dtos.SistemaDetailDto;
import br.com.monitoramento.inventario.dtos.SistemaDto;
import br.com.monitoramento.inventario.forms.SistemaForm;
//import br.com.monitoramento.inventario.forms.SistemaUpdateForm;
import br.com.monitoramento.inventario.models.Sistema;
import br.com.monitoramento.inventario.repositories.SistemaRepository;
import io.swagger.annotations.Api;

@Api(tags = "Inventário / Sistemas")
@RestController
@RequestMapping("/inventario/sistemas")
public class SistemasController {

	@Autowired
	private SistemaRepository sistemaRepository;

	@GetMapping
	@Cacheable(value = "listaSistemas")
	public Page<SistemaDto> lista(@RequestParam(required = false) String nome, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		
		if (nome == null) {
			System.out.println(paginacao);
			Page<Sistema> sistemas = sistemaRepository.findAll(paginacao);
			return SistemaDto.converter(sistemas);
		} else {
			Page<Sistema> sistemas = sistemaRepository.findByNome(nome, paginacao);
			return SistemaDto.converter(sistemas);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaSistemas", allEntries = true)
	public ResponseEntity<SistemaDto> cadastrar(@RequestBody @Valid SistemaForm form, UriComponentsBuilder uriBuilder) {
		Sistema sistema = form.converter();
		sistemaRepository.save(sistema);
		
		URI uri = uriBuilder.path("/tipos-vm/{id}").buildAndExpand(sistema.getId()).toUri();
		return ResponseEntity.created(uri).body(new SistemaDto(sistema));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SistemaDto> detalhar(@PathVariable Long id) {
		Optional<Sistema> sistema = sistemaRepository.findById(id);
		if (sistema.isPresent()) {
			return ResponseEntity.ok(new SistemaDto(sistema.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaSistemas", allEntries = true)
	public ResponseEntity<SistemaDto> atualizar(@PathVariable Long id, @RequestBody @Valid SistemaForm form) {
		Optional<Sistema> optional = sistemaRepository.findById(id);
		if (optional.isPresent()) {
			Sistema sistema = form.update(id, sistemaRepository);
			return ResponseEntity.ok(new SistemaDto(sistema));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaSistemas", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Sistema> optional = sistemaRepository.findById(id);
		if (optional.isPresent()) {
			sistemaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
