package br.com.monitoramento.inventario.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.monitoramento.inventario.models.Gerencia;
import br.com.monitoramento.inventario.repositories.GerenciaRepository;

public class GerenciaForm {

	@NotNull @NotEmpty
	private String nome;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Gerencia converter() {
		// TODO Auto-generated method stub
		return new Gerencia(nome);
	}

	public Gerencia update(Long id, GerenciaRepository gerenciaRepository) {
		Gerencia gerencia = gerenciaRepository.getOne(id);
		gerencia.setNome(this.nome);
		return gerencia;
	}
}
