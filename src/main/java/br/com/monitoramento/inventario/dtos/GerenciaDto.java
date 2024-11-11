package br.com.monitoramento.inventario.dtos;

import org.springframework.data.domain.Page;

import br.com.monitoramento.inventario.models.Gerencia;

public class GerenciaDto {

	private Long id;
	private String nome;

	public GerenciaDto(Gerencia gerencia) {
		if (gerencia != null) {
			this.id = gerencia.getId();
			this.nome = gerencia.getNome();
		}
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public static Page<GerenciaDto> converter(Page<Gerencia> gerencias) {
		return gerencias.map(GerenciaDto::new);
	}
}
