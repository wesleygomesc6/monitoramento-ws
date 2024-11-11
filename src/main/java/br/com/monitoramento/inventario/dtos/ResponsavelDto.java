package br.com.monitoramento.inventario.dtos;

import org.springframework.data.domain.Page;

import br.com.monitoramento.inventario.models.Cargo;
import br.com.monitoramento.inventario.models.Gerencia;
import br.com.monitoramento.inventario.models.Pessoa;
import br.com.monitoramento.inventario.models.Responsavel;
import br.com.monitoramento.inventario.models.TipoContratacao;

public class ResponsavelDto {

	private Long id;
	private String nome;
	private String atividades;
	private Boolean gerente;
	private Boolean status;
	private Gerencia gerencia;
	private Cargo cargo;
	private TipoContratacao tipoContratacao;
	private Pessoa pessoa;
	
	public ResponsavelDto(Responsavel responsavel) {
		if (responsavel != null) {
			this.id = responsavel.getId();
			this.nome = responsavel.getNome();
			this.atividades = responsavel.getAtividades();
			this.gerente = responsavel.getGerente();
			this.status = responsavel.getStatus();
			this.gerencia = responsavel.getGerencia();
			this.cargo = responsavel.getCargo();
			this.tipoContratacao = responsavel.getTipoContratacao();
			this.pessoa = responsavel.getPessoa();
		}
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getAtividades() {
		return atividades;
	}

	public Boolean getGerente() {
		return gerente;
	}

	public Boolean getStatus() {
		return status;
	}

	public Gerencia getGerencia() {
		return gerencia;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public TipoContratacao getTipoContratacao() {
		return tipoContratacao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public static Page<ResponsavelDto> converter(Page<Responsavel> responsaveis) {
		return responsaveis.map(ResponsavelDto::new);
	}
}
