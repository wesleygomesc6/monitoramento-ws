package br.com.monitoramento.inventario.dtos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import br.com.monitoramento.inventario.models.Ambiente;
import br.com.monitoramento.inventario.models.Grupo;
import br.com.monitoramento.inventario.models.Responsavel;
import br.com.monitoramento.inventario.models.Servico;
import br.com.monitoramento.inventario.models.Sistema;
import br.com.monitoramento.inventario.models.StatusVm;
import br.com.monitoramento.inventario.models.Vm;

public class VmDto {

	private Long id;
	private String nome;
	private String hostname;
	private String ip_address;
	private TipoVmDto tipoVm;
	private ClusterDto cluster;
	private StatusVm status;
	private List<Grupo> grupos;
	private List<Ambiente> ambientes;
	private List<Responsavel> responsaveis;
	private List<Servico> servicos;
	private List<Sistema> sistemas;
	
	public VmDto(Vm vm) {
		this.id = vm.getId();
		this.nome = vm.getNome();
		this.hostname = vm.getHostname();
		this.ip_address = vm.getIp_address();
		this.tipoVm = new TipoVmDto(vm.getTipoVm());
		this.cluster = new ClusterDto(vm.getCluster());
		this.status = vm.getStatusVm();
		this.grupos = new ArrayList<>();
		this.grupos.addAll(vm.getGrupos());
		this.ambientes = new ArrayList<>();
		this.ambientes.addAll(vm.getAmbientes());
		this.responsaveis = new ArrayList<>();
		this.responsaveis.addAll(vm.getResponsaveis());
		this.servicos = new ArrayList<>();
		this.servicos.addAll(vm.getServicos());
		this.sistemas = new ArrayList<>();
		this.sistemas.addAll(vm.getSistemas());
	}
	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public String getHostname() {
		return hostname;
	}
	public String getIp_address() {
		return ip_address;
	}

	public TipoVmDto getTipoVm() {
		return tipoVm;
	}

	public ClusterDto getCluster() {
		return cluster;
	}
	
	public StatusVm getStatus() {
		return status;
	}
	
	public List<Grupo> getGrupos() {
		return grupos;
	}
	
	public List<Ambiente> getAmbientes() {
		return ambientes;
	}
	
	public List<Responsavel> getResponsaveis() {
		return responsaveis;
	}
	
	public List<Servico> getServicos() {
		return servicos;
	}
	
	public List<Sistema> getSistemas() {
		return sistemas;
	}
	
	public static Page<VmDto> converter(Page<Vm> vms) {
		return vms.map(VmDto::new);
	}
	
}
