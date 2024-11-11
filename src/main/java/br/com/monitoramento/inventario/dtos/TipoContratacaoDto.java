package br.com.monitoramento.inventario.dtos;

import org.springframework.data.domain.Page;

import br.com.monitoramento.inventario.models.TipoContratacao;

public class TipoContratacaoDto {

	private Long id;
	private String nome;

	public TipoContratacaoDto(TipoContratacao tipoContratacao) {
		if (tipoContratacao != null) {
			this.id = tipoContratacao.getId();
			this.nome = tipoContratacao.getNome();
		}
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public static Page<TipoContratacaoDto> converter(Page<TipoContratacao> tiposContratacao) {
		return tiposContratacao.map(TipoContratacaoDto::new);
	}
}
