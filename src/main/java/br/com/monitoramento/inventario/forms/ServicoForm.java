package br.com.monitoramento.inventario.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.monitoramento.inventario.models.Servico;
import br.com.monitoramento.inventario.repositories.ServicoRepository;

public class ServicoForm {

	@NotNull @NotEmpty
	private String nome;
	private String descricao;
	private String processo;
	private String periodicidade;
	private Integer tem_processo_eliminacao;
	private String log_execucao;
	private String log_sistema;
	private String log_ultima_execucao;
	private String comando_execucao;
	private String observacoes;
	private Integer status;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public void setPeriodicidade(String periodicidade) {
		this.periodicidade = periodicidade;
	}

	public void setTem_processo_eliminacao(Integer tem_processo_eliminacao) {
		this.tem_processo_eliminacao = tem_processo_eliminacao;
	}

	public void setLog_execucao(String log_execucao) {
		this.log_execucao = log_execucao;
	}

	public void setLog_sistema(String log_sistema) {
		this.log_sistema = log_sistema;
	}

	public void setLog_ultima_execucao(String log_ultima_execucao) {
		this.log_ultima_execucao = log_ultima_execucao;
	}

	public void setComando_execucao(String comando_execucao) {
		this.comando_execucao = comando_execucao;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Servico converter() {
		// TODO Auto-generated method stub
		return new Servico(nome, descricao, processo, periodicidade, status, log_execucao, log_sistema, log_ultima_execucao, comando_execucao, observacoes, status);
	}

	public Servico update(Long id, ServicoRepository servicoRepository) {
		Servico servico = servicoRepository.getOne(id);
		servico.setNome(this.nome);
		servico.setDescricao(this.descricao);
		servico.setProcesso(this.processo);
		servico.setPeriodicidade(this.periodicidade);
		if(this.tem_processo_eliminacao != null) {servico.setTem_processo_eliminacao(this.tem_processo_eliminacao);}
		servico.setLog_execucao(this.log_execucao);
		servico.setLog_sistema(this.log_sistema);
		servico.setLog_ultima_execucao(this.log_ultima_execucao);
		servico.setComando_execucao(this.comando_execucao);
		servico.setObservacoes(this.observacoes);
		if(this.status != null) {servico.setStatus(this.status);}
		return servico;
	}
}
