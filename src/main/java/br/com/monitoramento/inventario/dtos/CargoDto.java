package br.com.monitoramento.inventario.dtos;

import org.springframework.data.domain.Page;

import br.com.monitoramento.inventario.models.Cargo;

public class CargoDto {

	private Long id;
	private String nome;

	public CargoDto(Cargo cargo) {
		if (cargo != null) {
			this.id = cargo.getId();
			this.nome = cargo.getNome();
		}
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public static Page<CargoDto> converter(Page<Cargo> cargos) {
		return cargos.map(CargoDto::new);
	}
}
