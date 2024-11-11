package br.com.monitoramento.inventario.dtos;

import br.com.monitoramento.inventario.models.TipoVm;

public class TipoVmDetailDto {

	private Long id;
	private String nome;
	
	public TipoVmDetailDto(TipoVm tipoVm) {
		this.id = tipoVm.getId();
		this.nome = tipoVm.getNome();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
}
