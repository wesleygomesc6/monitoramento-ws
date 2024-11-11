package br.com.monitoramento.inventario.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.monitoramento.inventario.models.TipoVm;
import br.com.monitoramento.inventario.repositories.TipoVmRepository;

public class TipoVmUpdateForm {

	@NotNull @NotEmpty
	private String nome;

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public TipoVm update(Long id, TipoVmRepository rep) {
		TipoVm tipoVm = rep.getOne(id);
		tipoVm.setNome(this.nome);
		return tipoVm;
	}
}
