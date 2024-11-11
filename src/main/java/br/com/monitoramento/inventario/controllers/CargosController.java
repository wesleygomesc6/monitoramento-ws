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

import br.com.monitoramento.inventario.dtos.CargoDto;
import br.com.monitoramento.inventario.forms.CargoForm;
import br.com.monitoramento.inventario.models.Cargo;
import br.com.monitoramento.inventario.repositories.CargoRepository;
import io.swagger.annotations.Api;

@Api(tags = "Inventário / Cargos")
@RestController
@RequestMapping("/inventario/cargos")
public class CargosController {

	@Autowired
	private CargoRepository cargoRepository;

	@GetMapping
	@Cacheable(value = "listaCargos")
	public Page<CargoDto> lista(@RequestParam(required = false) String nome, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		
		if (nome == null) {
			System.out.println(paginacao);
			Page<Cargo> cargos = cargoRepository.findAll(paginacao);
			return CargoDto.converter(cargos);
		} else {
			Page<Cargo> cargos = cargoRepository.findByNome(nome, paginacao);
			return CargoDto.converter(cargos);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaCargos", allEntries = true)
	public ResponseEntity<CargoDto> cadastrar(@RequestBody @Valid CargoForm form, UriComponentsBuilder uriBuilder) {
		Cargo cargo = form.converter();
		cargoRepository.save(cargo);
		
		URI uri = uriBuilder.path("/tipos-vm/{id}").buildAndExpand(cargo.getId()).toUri();
		return ResponseEntity.created(uri).body(new CargoDto(cargo));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CargoDto> detalhar(@PathVariable Long id) {
		Optional<Cargo> cargo = cargoRepository.findById(id);
		if (cargo.isPresent()) {
			return ResponseEntity.ok(new CargoDto(cargo.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaCargos", allEntries = true)
	public ResponseEntity<CargoDto> atualizar(@PathVariable Long id, @RequestBody @Valid CargoForm form) {
		Optional<Cargo> optional = cargoRepository.findById(id);
		if (optional.isPresent()) {
			Cargo cargo = form.update(id, cargoRepository);
			return ResponseEntity.ok(new CargoDto(cargo));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaCargos", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Cargo> optional = cargoRepository.findById(id);
		if (optional.isPresent()) {
			cargoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
