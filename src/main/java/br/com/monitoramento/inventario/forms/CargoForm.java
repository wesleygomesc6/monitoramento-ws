package br.com.monitoramento.inventario.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.monitoramento.inventario.models.Cargo;
import br.com.monitoramento.inventario.repositories.CargoRepository;

public class CargoForm {

	@NotNull @NotEmpty
	private String nome;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cargo converter() {
		// TODO Auto-generated method stub
		return new Cargo(nome);
	}

	public Cargo update(Long id, CargoRepository cargoRepository) {
		Cargo cargo = cargoRepository.getOne(id);
		cargo.setNome(this.nome);
		return cargo;
	}
}
