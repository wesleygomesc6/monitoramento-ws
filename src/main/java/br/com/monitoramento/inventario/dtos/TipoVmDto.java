package br.com.monitoramento.inventario.dtos;

import org.springframework.data.domain.Page;

import br.com.monitoramento.inventario.models.TipoVm;

public class TipoVmDto {

	private Long id;
	private String nome;

	public TipoVmDto(TipoVm tipoVm) {
		if (tipoVm != null) {
			this.id = tipoVm.getId();
			this.nome = tipoVm.getNome();
		}
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public static Page<TipoVmDto> converter(Page<TipoVm> tiposVm) {
		return tiposVm.map(TipoVmDto::new);
	}
}
