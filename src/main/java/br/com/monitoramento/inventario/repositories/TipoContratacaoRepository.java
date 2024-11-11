package br.com.monitoramento.inventario.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.monitoramento.inventario.models.TipoContratacao;

public interface TipoContratacaoRepository extends JpaRepository<TipoContratacao, Long> {

	Page<TipoContratacao> findByNome(String nome, Pageable paginacao);
}
