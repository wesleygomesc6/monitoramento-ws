package br.com.monitoramento.inventario.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.lang.Nullable;

@Entity(name = "responsaveis")
public class Responsavel {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String atividades;
	private Boolean gerente;
	private Boolean status;
	@ManyToOne @Nullable
	@JoinColumn(name = "gerencia_id")
	private Gerencia gerencia;
	@ManyToOne @Nullable
	@JoinColumn(name = "cargo_id")
	private Cargo cargo;
	@ManyToOne @Nullable
	@JoinColumn(name = "tipo_contratacao_id")
	private TipoContratacao tipoContratacao;
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoa pessoa;
	
	public Responsavel() {}
	
	public Responsavel(String nome, String atividades, Boolean gerente, Boolean status, Gerencia gerencia, Cargo cargo,
			TipoContratacao tipoContratacao, Pessoa pessoa) {
		this.nome = nome;
		this.atividades = atividades;
		this.gerente = (gerente != null) ? gerente : false;
		this.status = (status != null) ? status : true;
		this.gerencia = gerencia;
		this.cargo = cargo;
		this.tipoContratacao = tipoContratacao;
		this.pessoa = pessoa;
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

	public String getAtividades() {
		return atividades;
	}

	public void setAtividades(String atividades) {
		this.atividades = atividades;
	}

	public Boolean getGerente() {
		return gerente;
	}

	public void setGerente(Boolean gerente) {
		this.gerente = gerente;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Gerencia getGerencia() {
		return gerencia;
	}

	public void setGerencia(Gerencia gerencia) {
		this.gerencia = gerencia;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public TipoContratacao getTipoContratacao() {
		return tipoContratacao;
	}

	public void setTipoContratacao(TipoContratacao tipoContratacao) {
		this.tipoContratacao = tipoContratacao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
