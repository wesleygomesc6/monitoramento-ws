package br.com.monitoramento.inventario.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.lang.Nullable;

@Entity(name = "servicos")
public class Servico {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	private String processo;
	private String periodicidade;
	@Nullable
	private Integer tem_processo_eliminacao;
	private String log_execucao;
	private String log_sistema;
	private String log_ultima_execucao;
	private String comando_execucao;
	private String observacoes;
	private Integer status;
	
	public Servico() {}
	
	public Servico(String nome, String descricao, String processo, String periodicidade,
			Integer tem_processo_eliminacao, String log_execucao, String log_sistema, String log_ultima_execucao,
			String comando_execucao, String observacoes, Integer status) {
		this.nome = nome;
		this.descricao = descricao;
		this.processo = processo;
		this.periodicidade = periodicidade;
		this.tem_processo_eliminacao = tem_processo_eliminacao;
		this.log_execucao = log_execucao;
		this.log_sistema = log_sistema;
		this.log_ultima_execucao = log_ultima_execucao;
		this.comando_execucao = comando_execucao;
		this.observacoes = observacoes;
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getProcesso() {
		return processo;
	}
	public void setProcesso(String processo) {
		this.processo = processo;
	}
	public String getPeriodicidade() {
		return periodicidade;
	}
	public void setPeriodicidade(String periodicidade) {
		this.periodicidade = periodicidade;
	}
	public Integer getTem_processo_eliminacao() {
		return tem_processo_eliminacao;
	}
	public void setTem_processo_eliminacao(Integer tem_processo_eliminacao) {
		this.tem_processo_eliminacao = tem_processo_eliminacao;
	}
	public String getLog_execucao() {
		return log_execucao;
	}
	public void setLog_execucao(String log_execucao) {
		this.log_execucao = log_execucao;
	}
	public String getLog_sistema() {
		return log_sistema;
	}
	public void setLog_sistema(String log_sistema) {
		this.log_sistema = log_sistema;
	}
	public String getLog_ultima_execucao() {
		return log_ultima_execucao;
	}
	public void setLog_ultima_execucao(String log_ultima_execucao) {
		this.log_ultima_execucao = log_ultima_execucao;
	}
	public String getComando_execucao() {
		return comando_execucao;
	}
	public void setComando_execucao(String comando_execucao) {
		this.comando_execucao = comando_execucao;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
