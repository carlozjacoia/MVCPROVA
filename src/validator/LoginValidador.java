package validator;

import model.UsuarioDAO;
import model.UsuarioDTO;

public class LoginValidador implements Validador<String> {
    private final String login;
    private final UsuarioDAO usuarioDAO;

    public LoginValidador(String login) {
        this.login = login;
        this.usuarioDAO = new UsuarioDAO(); // ou receba via construtor para facilitar testes
    }

    @Override
    public boolean validar(String valorAtual) {
        UsuarioDTO usuarioEncontrado = usuarioDAO.buscarPorLogin(this.login);
        return usuarioEncontrado == null; // true se não existe, ou seja, login está livre
    }

    @Override
    public String getMensagemErro() {
        return "Este login já está em uso. Escolha outro.";
    }

    @Override
    public String getValor() {
        return login;
    }
}
