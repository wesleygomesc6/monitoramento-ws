package br.com.monitoramento.inventario.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.monitoramento.inventario.models.Responsavel;

public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {

	Page<Responsavel> findByNome(String nome, Pageable paginacao);
}
