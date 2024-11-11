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

import br.com.monitoramento.inventario.dtos.ServicoDto;
import br.com.monitoramento.inventario.forms.ServicoForm;
import br.com.monitoramento.inventario.models.Servico;
import br.com.monitoramento.inventario.repositories.ServicoRepository;
import io.swagger.annotations.Api;

@Api(tags = "Inventário / Serviços")
@RestController
@RequestMapping("/inventario/servicos")
public class ServicosController {

	@Autowired
	private ServicoRepository servicoRepository;

	@GetMapping
	@Cacheable(value = "listaServicos")
	public Page<ServicoDto> lista(@RequestParam(required = false) String nome, 
			@PageableDefault(sort = "nome", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		
		if (nome == null) {
			Page<Servico> servicos = servicoRepository.findAll(paginacao);
			return ServicoDto.converter(servicos);
		} else {
			Page<Servico> servicos = servicoRepository.findByNome(nome, paginacao);
			return ServicoDto.converter(servicos);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaServicos", allEntries = true)
	public ResponseEntity<ServicoDto> cadastrar(@RequestBody @Valid ServicoForm form, UriComponentsBuilder uriBuilder) {
		Servico servico = form.converter();
		servicoRepository.save(servico);
		
		URI uri = uriBuilder.path("/tipos-vm/{id}").buildAndExpand(servico.getId()).toUri();
		return ResponseEntity.created(uri).body(new ServicoDto(servico));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ServicoDto> detalhar(@PathVariable Long id) {
		Optional<Servico> servico = servicoRepository.findById(id);
		if (servico.isPresent()) {
			return ResponseEntity.ok(new ServicoDto(servico.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaServicos", allEntries = true)
	public ResponseEntity<ServicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid ServicoForm form) {
		Optional<Servico> optional = servicoRepository.findById(id);
		if (optional.isPresent()) {
			Servico servico = form.update(id, servicoRepository);
			return ResponseEntity.ok(new ServicoDto(servico));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaServicos", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Servico> optional = servicoRepository.findById(id);
		if (optional.isPresent()) {
			servicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
