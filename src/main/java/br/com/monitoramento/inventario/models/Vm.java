package br.com.monitoramento.inventario.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.lang.Nullable;

@Entity(name = "vms")
public class Vm {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	private String nome;
	private String hostname;
	@Column(name = "ip_address")
	private String ipAddress;
	@Column(name = "nome_correto")
	private String nomeCorreto;
	private String finalidade;
	private String url;
	private String guest_os;
	private String state;
	private String complete_ip_address;
	private String observacoes;
	@ManyToOne @Nullable
	@JoinColumn(name = "tipo_vm_id")
	private TipoVm tipoVm;
	@ManyToOne @Nullable
	private Cluster cluster;
	@ManyToOne @Nullable
	@JoinColumn(name = "status_vm_id")
	private StatusVm statusVm;
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
		name = "grupos_vm",
		joinColumns = @JoinColumn(name = "vm_id"),
		inverseJoinColumns = @JoinColumn(name = "grupo_id")
	)
	private List<Grupo> grupos =  new ArrayList<>();
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
			name = "ambientes_vm",
			joinColumns = @JoinColumn(name = "vm_id"),
			inverseJoinColumns = @JoinColumn(name = "ambiente_id")
			)
	private List<Ambiente> ambientes =  new ArrayList<>();
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
		name = "responsaveis_vm",
		joinColumns = @JoinColumn(name = "vm_id"),
		inverseJoinColumns = @JoinColumn(name = "responsavel_id")
	)
	private List<Responsavel> responsaveis =  new ArrayList<>();
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
		name = "servicos_vm",
		joinColumns = @JoinColumn(name = "vm_id"),
		inverseJoinColumns = @JoinColumn(name = "servico_id")
	)
	private List<Servico> servicos =  new ArrayList<>();
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
		name = "sistemas_vm",
		joinColumns = @JoinColumn(name = "vm_id"),
		inverseJoinColumns = @JoinColumn(name = "sistema_id")
	)
	private List<Sistema> sistemas =  new ArrayList<>();
	
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
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getIp_address() {
		return ipAddress;
	}
	public void setIp_address(String ip_address) {
		this.ipAddress = ip_address;
	}
	public String getNome_correto() {
		return nomeCorreto;
	}
	public void setNome_correto(String nome_correto) {
		this.nomeCorreto = nome_correto;
	}
	public String getFinalidade() {
		return finalidade;
	}
	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getGuest_os() {
		return guest_os;
	}
	public void setGuest_os(String guest_os) {
		this.guest_os = guest_os;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getComplete_ip_address() {
		return complete_ip_address;
	}
	public void setComplete_ip_address(String complete_ip_address) {
		this.complete_ip_address = complete_ip_address;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public TipoVm getTipoVm() {
		return tipoVm;
	}
	public void setTipoVm(TipoVm tipoVm) {
		this.tipoVm = tipoVm;
	}
	public Cluster getCluster() {
		return cluster;
	}
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	public StatusVm getStatusVm() {
		return statusVm;
	}
	public void setStatusVm(StatusVm statusVm) {
		this.statusVm = statusVm;
	}
	public List<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
	public List<Ambiente> getAmbientes() {
		return ambientes;
	}
	public void setAmbientes(List<Ambiente> ambientes) {
		this.ambientes = ambientes;
	}
	public List<Responsavel> getResponsaveis() {
		return responsaveis;
	}
	public void setResponsaveis(List<Responsavel> responsaveis) {
		this.responsaveis = responsaveis;
	}
	public List<Servico> getServicos() {
		return servicos;
	}
	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}
	public List<Sistema> getSistemas() {
		return sistemas;
	}
	public void setSistemas(List<Sistema> sistemas) {
		this.sistemas = sistemas;
	}
	
}
