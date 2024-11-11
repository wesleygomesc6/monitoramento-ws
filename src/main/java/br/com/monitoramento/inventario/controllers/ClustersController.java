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

import br.com.monitoramento.inventario.dtos.ClusterDto;
import br.com.monitoramento.inventario.forms.ClusterForm;
import br.com.monitoramento.inventario.models.Cluster;
import br.com.monitoramento.inventario.repositories.ClusterRepository;
import io.swagger.annotations.Api;

@Api(tags = "Inventário / Clusters")
@RestController
@RequestMapping("/inventario/clusters")
public class ClustersController {

	@Autowired
	private ClusterRepository clusterRepository;

	@GetMapping
	@Cacheable(value = "listaClusters")
	public Page<ClusterDto> lista(@RequestParam(required = false) String nome, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		
		if (nome == null) {
			System.out.println(paginacao);
			Page<Cluster> clusters = clusterRepository.findAll(paginacao);
			return ClusterDto.converter(clusters);
		} else {
			Page<Cluster> clusters = clusterRepository.findByNome(nome, paginacao);
			return ClusterDto.converter(clusters);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaClusters", allEntries = true)
	public ResponseEntity<ClusterDto> cadastrar(@RequestBody @Valid ClusterForm form, UriComponentsBuilder uriBuilder) {
		Cluster cluster = form.converter();
		clusterRepository.save(cluster);
		
		URI uri = uriBuilder.path("/tipos-vm/{id}").buildAndExpand(cluster.getId()).toUri();
		return ResponseEntity.created(uri).body(new ClusterDto(cluster));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClusterDto> detalhar(@PathVariable Long id) {
		Optional<Cluster> cluster = clusterRepository.findById(id);
		if (cluster.isPresent()) {
			return ResponseEntity.ok(new ClusterDto(cluster.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaClusters", allEntries = true)
	public ResponseEntity<ClusterDto> atualizar(@PathVariable Long id, @RequestBody @Valid ClusterForm form) {
		Optional<Cluster> optional = clusterRepository.findById(id);
		if (optional.isPresent()) {
			Cluster cluster = form.update(id, clusterRepository);
			return ResponseEntity.ok(new ClusterDto(cluster));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaClusters", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Cluster> optional = clusterRepository.findById(id);
		if (optional.isPresent()) {
			clusterRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
