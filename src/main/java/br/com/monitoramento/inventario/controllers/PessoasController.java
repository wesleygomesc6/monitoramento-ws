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

import br.com.monitoramento.inventario.forms.PessoaForm;
import br.com.monitoramento.inventario.models.Pessoa;
import br.com.monitoramento.inventario.repositories.PessoaRepository;
import io.swagger.annotations.Api;

@Api(tags = "Inventário / Pessoas")
@RestController
@RequestMapping("/inventario/pessoas")
public class PessoasController {

	@Autowired
	private PessoaRepository pessoaRepository;

	@GetMapping
	@Cacheable(value = "listaPessoas")
	public Page<Pessoa> lista(@RequestParam(required = false) String nome, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		
		if (nome == null) {
			System.out.println(paginacao);
			Page<Pessoa> pessoas = pessoaRepository.findAll(paginacao);
			return pessoas;
		} else {
			Page<Pessoa> pessoas = pessoaRepository.findByNome(nome, paginacao);
			return pessoas;
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaPessoas", allEntries = true)
	public ResponseEntity<Pessoa> cadastrar(@RequestBody @Valid Pessoa pessoa, UriComponentsBuilder uriBuilder) {
//		Pessoa pessoa = form.converter();
		pessoaRepository.save(pessoa);
		
		URI uri = uriBuilder.path("/tipos-vm/{id}").buildAndExpand(pessoa.getId()).toUri();
		return ResponseEntity.created(uri).body(pessoa);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> detalhar(@PathVariable Long id) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(id);
		if (pessoa.isPresent()) {
			return ResponseEntity.ok(pessoa.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaPessoas", allEntries = true)
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @RequestBody @Valid PessoaForm form) {
		Optional<Pessoa> optional = pessoaRepository.findById(id);
		if (optional.isPresent()) {
			Pessoa pessoa = form.update(id, pessoaRepository);
			return ResponseEntity.ok(pessoa);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaPessoas", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Pessoa> optional = pessoaRepository.findById(id);
		if (optional.isPresent()) {
			pessoaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
