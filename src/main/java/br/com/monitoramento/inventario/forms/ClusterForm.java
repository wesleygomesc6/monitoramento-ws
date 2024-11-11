package br.com.monitoramento.inventario.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.monitoramento.inventario.models.Cluster;
import br.com.monitoramento.inventario.repositories.ClusterRepository;

public class ClusterForm {

	@NotNull @NotEmpty
	private String nome;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cluster converter() {
		// TODO Auto-generated method stub
		return new Cluster(nome);
	}

	public Cluster update(Long id, ClusterRepository clusterRepository) {
		Cluster cluster = clusterRepository.getOne(id);
		cluster.setNome(this.nome);
		return cluster;
	}
}
