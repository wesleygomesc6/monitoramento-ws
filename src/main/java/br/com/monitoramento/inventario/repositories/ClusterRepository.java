package br.com.monitoramento.inventario.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.monitoramento.inventario.models.Cluster;

public interface ClusterRepository extends JpaRepository<Cluster, Long> {

	Page<Cluster> findByNome(String nome, Pageable paginacao);
}
