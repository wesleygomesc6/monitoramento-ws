package br.com.monitoramento.inventario.dtos;

import org.springframework.data.domain.Page;

import br.com.monitoramento.inventario.models.Cluster;

public class ClusterDto {

	private Long id;
	private String nome;

	public ClusterDto(Cluster cluster) {
		if (cluster != null) {
			this.id = cluster.getId();
			this.nome = cluster.getNome();
		}
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public static Page<ClusterDto> converter(Page<Cluster> clusters) {
		return clusters.map(ClusterDto::new);
	}
}
