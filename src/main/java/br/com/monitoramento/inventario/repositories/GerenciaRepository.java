package br.com.monitoramento.inventario.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.monitoramento.inventario.models.Gerencia;

public interface GerenciaRepository extends JpaRepository<Gerencia, Long> {

	Page<Gerencia> findByNome(String nome, Pageable paginacao);
}
