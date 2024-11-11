package br.com.monitoramento.inventario.dtos;

import org.springframework.data.domain.Page;

import br.com.monitoramento.inventario.models.Grupo;

public class GrupoDto {

	private Long id;
	private String nome;

	public GrupoDto(Grupo grupo) {
		if (grupo != null) {
			this.id = grupo.getId();
			this.nome = grupo.getNome();
		}
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public static Page<GrupoDto> converter(Page<Grupo> grupos) {
		return grupos.map(GrupoDto::new);
	}
}
