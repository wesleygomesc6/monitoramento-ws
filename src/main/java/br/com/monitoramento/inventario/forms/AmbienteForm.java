package br.com.monitoramento.inventario.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.monitoramento.inventario.models.Ambiente;
import br.com.monitoramento.inventario.repositories.AmbienteRepository;

public class AmbienteForm {

	@NotNull @NotEmpty
	private String nome;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Ambiente converter() {
		// TODO Auto-generated method stub
		return new Ambiente(nome);
	}

	public Ambiente update(Long id, AmbienteRepository ambienteRepository) {
		Ambiente ambiente = ambienteRepository.getOne(id);
		ambiente.setNome(this.nome);
		return ambiente;
	}
}
