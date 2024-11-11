package br.com.monitoramento.inventario.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.monitoramento.inventario.models.Sistema;
import br.com.monitoramento.inventario.repositories.SistemaRepository;

public class SistemaForm {
	
	@NotNull @NotEmpty
	private String nome;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Sistema converter() {
		// TODO Auto-generated method stub
		return new Sistema(nome);
	}

	public Sistema update(Long id, SistemaRepository sistemaRepository) {
		Sistema sistema = sistemaRepository.getOne(id);
		sistema.setNome(this.nome);
		return sistema;
	}

}
