package br.com.monitoramento.inventario.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.monitoramento.inventario.models.Sistema;

public interface SistemaRepository extends JpaRepository<Sistema, Long> {
	
	Page<Sistema> findByNome(String nome, Pageable paginacao);

}
