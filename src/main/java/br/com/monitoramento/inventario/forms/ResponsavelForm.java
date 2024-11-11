package br.com.monitoramento.inventario.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.monitoramento.inventario.models.Cargo;
import br.com.monitoramento.inventario.models.Gerencia;
import br.com.monitoramento.inventario.models.Pessoa;
import br.com.monitoramento.inventario.models.Responsavel;
import br.com.monitoramento.inventario.models.TipoContratacao;
import br.com.monitoramento.inventario.repositories.ResponsavelRepository;

public class ResponsavelForm {

	@NotNull
	@NotEmpty
	private String nome;
	private String atividades;
	private Boolean gerente;
	private Boolean status;
	private Gerencia gerencia;
	private Cargo cargo;
	private TipoContratacao tipoContratacao;
	@NotNull
//	@NotEmpty
	private Pessoa pessoa;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setAtividades(String atividades) {
		this.atividades = atividades;
	}

	public void setGerente(Boolean gerente) {
		this.gerente = (gerente != null) ? gerente : false;
	}

	public void setStatus(Boolean status) {
		this.status = (status != null) ? status : true;
	}

	public void setGerencia(Gerencia gerencia) {
		this.gerencia = gerencia;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public void setTipoContratacao(TipoContratacao tipoContratacao) {
		this.tipoContratacao = tipoContratacao;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Responsavel converter() {
		// TODO Auto-generated method stub
		return new Responsavel(nome, atividades, gerente, status, gerencia, cargo, tipoContratacao, pessoa);
	}

	public Responsavel update(Long id, ResponsavelRepository responsavelRepository) {
		Responsavel responsavel = responsavelRepository.getOne(id);
		responsavel.setNome(this.nome);
		responsavel.setAtividades(this.atividades);
		if (this.gerente != null) {
			responsavel.setStatus(this.gerente);
		}
		if (this.status != null) {
			responsavel.setStatus(this.status);
		}
		responsavel.setGerencia(this.gerencia);
		responsavel.setCargo(this.cargo);
		responsavel.setTipoContratacao(this.tipoContratacao);
		responsavel.setPessoa(this.pessoa);
		return responsavel;
	}
}
