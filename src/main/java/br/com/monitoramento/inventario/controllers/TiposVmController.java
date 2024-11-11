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

//import br.com.monitoramento.inventario.dtos.TipoVmDetailDto;
import br.com.monitoramento.inventario.dtos.TipoVmDto;
import br.com.monitoramento.inventario.forms.TipoVmForm;
import br.com.monitoramento.inventario.forms.TipoVmUpdateForm;
import br.com.monitoramento.inventario.models.TipoVm;
import br.com.monitoramento.inventario.repositories.TipoVmRepository;
import io.swagger.annotations.Api;

@Api(tags = "Inventário / Tipos de VM")
@RestController
@RequestMapping("/inventario/tiposvm")
public class TiposVmController {

	@Autowired
	private TipoVmRepository tipoVmRepository;

	@GetMapping
	@Cacheable(value = "listaTiposVm")
	public Page<TipoVmDto> lista(@RequestParam(required = false) String nome, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		
		if (nome == null) {
			System.out.println(paginacao);
			Page<TipoVm> tipos_vm = tipoVmRepository.findAll(paginacao);
			return TipoVmDto.converter(tipos_vm);
		} else {
			Page<TipoVm> tipos_vm = tipoVmRepository.findByNome(nome, paginacao);
			return TipoVmDto.converter(tipos_vm);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaTiposVm", allEntries = true)
	public ResponseEntity<?> cadastrar(@RequestBody @Valid TipoVmForm form, UriComponentsBuilder uriBuilder) {
		TipoVm tipoVm = form.converter();
		tipoVmRepository.save(tipoVm);
		URI uri = uriBuilder.path("/tipos-vm/{id}").buildAndExpand(tipoVm.getId()).toUri();
		return ResponseEntity.created(uri).body(new TipoVmDto(tipoVm));	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TipoVmDto> detalhar(@PathVariable Long id) {
		Optional<TipoVm> tipoVm = tipoVmRepository.findById(id);
		if (tipoVm.isPresent()) {
			return ResponseEntity.ok(new TipoVmDto(tipoVm.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaTiposVm", allEntries = true)
	public ResponseEntity<TipoVmDto> atualizar(@PathVariable Long id, @RequestBody @Valid TipoVmUpdateForm form) {
		Optional<TipoVm> optional = tipoVmRepository.findById(id);
		if (optional.isPresent()) {
			TipoVm tipoVm = form.update(id, tipoVmRepository);
			return ResponseEntity.ok(new TipoVmDto(tipoVm));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaTiposVm", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<TipoVm> optional = tipoVmRepository.findById(id);
		if (optional.isPresent()) {
			tipoVmRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
