package br.com.monitoramento.inventario.dtos;

import org.springframework.data.domain.Page;

import br.com.monitoramento.inventario.models.Ambiente;

public class AmbienteDto {

	private Long id;
	private String nome;

	public AmbienteDto(Ambiente ambiente) {
		if (ambiente != null) {
			this.id = ambiente.getId();
			this.nome = ambiente.getNome();
		}
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public static Page<AmbienteDto> converter(Page<Ambiente> ambientes) {
		return ambientes.map(AmbienteDto::new);
	}
}
