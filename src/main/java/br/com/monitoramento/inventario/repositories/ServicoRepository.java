package br.com.monitoramento.inventario.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.monitoramento.inventario.models.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

	Page<Servico> findByNome(String nome, Pageable paginacao);
}
