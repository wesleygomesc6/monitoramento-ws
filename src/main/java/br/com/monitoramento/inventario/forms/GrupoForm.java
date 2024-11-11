package br.com.monitoramento.inventario.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.monitoramento.inventario.models.Grupo;
import br.com.monitoramento.inventario.repositories.GrupoRepository;

public class GrupoForm {

	@NotNull @NotEmpty
	private String nome;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Grupo converter() {
		// TODO Auto-generated method stub
		return new Grupo(nome);
	}

	public Grupo update(Long id, GrupoRepository grupoRepository) {
		Grupo grupo = grupoRepository.getOne(id);
		grupo.setNome(this.nome);
		return grupo;
	}
}
