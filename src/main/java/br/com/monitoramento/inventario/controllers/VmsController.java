package br.com.monitoramento.inventario.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.monitoramento.inventario.dtos.VmDto;
import br.com.monitoramento.inventario.models.Vm;
import br.com.monitoramento.inventario.repositories.VmRepository;
import io.swagger.annotations.Api;

@Api(tags = "Inventário / VMs")
@RestController
@RequestMapping("/inventario/vms")
public class VmsController {

	@Autowired
	private VmRepository vmRepository;

	@GetMapping
	@Cacheable(value = "listaVms")
	public Page<VmDto> lista(@RequestParam(required = false) String filtro, 
			@PageableDefault(sort = "nome", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		
		if (filtro == null) {
			Page<Vm> vms = vmRepository.findAll(paginacao);
			return VmDto.converter(vms);
		} else {
			Page<Vm> vms = vmRepository.findByNomeContainsOrHostnameContainsOrIpAddressContainsOrTipoVmNomeContainsOrClusterNomeContainsAllIgnoreCase( filtro, filtro, filtro, filtro, filtro, paginacao);
			return VmDto.converter(vms);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<VmDto> detalhar(@PathVariable Long id) {
		Optional<Vm> vm = vmRepository.findById(id);
		if (vm.isPresent()) {
			return ResponseEntity.ok(new VmDto(vm.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
