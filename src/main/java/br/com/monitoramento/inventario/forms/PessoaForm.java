package br.com.monitoramento.inventario.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.monitoramento.inventario.models.Pessoa;
import br.com.monitoramento.inventario.repositories.PessoaRepository;

public class PessoaForm {

	@NotNull @NotEmpty
	private String nome;
	private String email;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Pessoa update(Long id, PessoaRepository pessoaRepository) {
		Pessoa pessoa = pessoaRepository.getOne(id);
		pessoa.setNome(this.nome);
		pessoa.setEmail(this.email);
		return pessoa;
	}
}
