package br.com.monitoramento.inventario.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.monitoramento.inventario.models.Vm;

public interface VmRepository extends JpaRepository<Vm, Long> {

	Page<Vm> findByNome(String nome, Pageable paginacao);
	Page<Vm> findByNomeContainsOrHostnameContainsOrIpAddressContainsOrTipoVmNomeContainsOrClusterNomeContainsAllIgnoreCase(String nome, String hostname, String ipAddress, String tipoVm, String cluster,Pageable paginacao);
	Page<Vm> findByHostname(String nome, Pageable paginacao);
}
