package br.com.monitoramento.inventario.dtos;

import br.com.monitoramento.inventario.models.Pessoa;
import br.com.monitoramento.inventario.models.Sistema;
import br.com.monitoramento.inventario.models.Usuario;
import org.springframework.data.domain.Page;

public class UsuarioDto {
    private Long id;
    private String login;
    private int status;
    private Pessoa pessoa;

    public static Page<UsuarioDto> converter(Page<Usuario> usuarios) {
        // TODO Auto-generated method stub
        return usuarios.map(UsuarioDto::new);
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public int getStatus() {
        return status;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public UsuarioDto(Usuario usuario) {
        if(usuario != null) {
            this.id = usuario.getId();
            this.login = usuario.getLogin();
            this.status = usuario.getStatus();
            this.pessoa = usuario.getPessoa();
        }
    }
}
