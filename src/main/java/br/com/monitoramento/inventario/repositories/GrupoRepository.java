package br.com.monitoramento.inventario.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.monitoramento.inventario.models.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

	Page<Grupo> findByNome(String nome, Pageable paginacao);
}
