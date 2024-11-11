package br.com.monitoramento.inventario.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.monitoramento.inventario.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	Page<Pessoa> findByNome(String nome, Pageable paginacao);
}
