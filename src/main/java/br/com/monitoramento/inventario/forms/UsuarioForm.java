package br.com.monitoramento.inventario.forms;

import br.com.monitoramento.inventario.models.Pessoa;
import br.com.monitoramento.inventario.models.Usuario;
import br.com.monitoramento.inventario.repositories.UsuarioRepository;

public class UsuarioForm {
    private String login;
    private String senha;
    private int status;
    private Pessoa pessoa;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Usuario update(Long id, UsuarioRepository usuarioRepository) {
        Usuario usuario = usuarioRepository.getOne(id);
        usuario.setLogin(this.login);
        usuario.setSenha(this.senha);
        usuario.setStatus(this.status);
        usuario.setPessoa(this.pessoa);
        return usuario;
    }

    public Usuario converter() {
        return new Usuario(login, senha, status, pessoa);
    }
}
