package br.com.monitoramento.inventario.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.monitoramento.inventario.models.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {

	Page<Cargo> findByNome(String nome, Pageable paginacao);
}
