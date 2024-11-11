package br.com.monitoramento.inventario.dtos;

import org.springframework.data.domain.Page;

import br.com.monitoramento.inventario.models.Sistema;

public class SistemaDto {
	
	private Long id;
	private String nome;

	public SistemaDto(Sistema sistema) {
		this.id = sistema.getId();
		this.nome = sistema.getNome();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public static Page<SistemaDto> converter(Page<Sistema> sistemas) {
		// TODO Auto-generated method stub
		return sistemas.map(SistemaDto::new);
	}

}
