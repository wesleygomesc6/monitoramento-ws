package br.com.monitoramento.inventario.dtos;

import org.springframework.data.domain.Page;

import br.com.monitoramento.inventario.models.Servico;

public class ServicoDto {

	private Long id;
	private String nome;
	private String descricao;
	private String processo;
	private String periodicidade;
	private String log_execucao;
	private int status;
	
	public ServicoDto(Servico servico) {
		this.id = servico.getId();
		this.nome = servico.getNome();
		this.descricao = servico.getDescricao();
		this.processo = servico.getProcesso();
		this.periodicidade = servico.getPeriodicidade();
		this.log_execucao = servico.getLog_execucao();
		this.status = servico.getStatus();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getProcesso() {
		return processo;
	}

	public String getPeriodicidade() {
		return periodicidade;
	}

	public String getLog_execucao() {
		return log_execucao;
	}

	public int getStatus() {
		return status;
	}

	public static Page<ServicoDto> converter(Page<Servico> servicos) {
		return servicos.map(ServicoDto::new);
	}
	
}
