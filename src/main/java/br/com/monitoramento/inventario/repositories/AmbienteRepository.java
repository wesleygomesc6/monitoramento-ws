package br.com.monitoramento.inventario.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.monitoramento.inventario.models.Ambiente;

public interface AmbienteRepository extends JpaRepository<Ambiente, Long> {

	Page<Ambiente> findByNome(String nome, Pageable paginacao);
}