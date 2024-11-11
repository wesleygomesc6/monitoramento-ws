package br.com.monitoramento.inventario.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.monitoramento.inventario.models.TipoContratacao;
import br.com.monitoramento.inventario.repositories.TipoContratacaoRepository;

public class TipoContratacaoForm {

	@NotNull @NotEmpty
	private String nome;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoContratacao converter() {
		// TODO Auto-generated method stub
		return new TipoContratacao(nome);
	}

	public TipoContratacao update(Long id, TipoContratacaoRepository tipoContratacaoRepository) {
		TipoContratacao tipoContratacao = tipoContratacaoRepository.getOne(id);
		tipoContratacao.setNome(this.nome);
		return tipoContratacao;
	}
}
