package br.com.monitoramento.inventario.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.monitoramento.inventario.models.TipoVm;

public interface TipoVmRepository extends JpaRepository<TipoVm, Long> {

	Page<TipoVm> findByNome(String nome, Pageable paginacao);
}
