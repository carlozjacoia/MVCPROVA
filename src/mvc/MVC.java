package mvc;

import java.sql.SQLException;
import mvc.Conexao;
import mvc.UsuarioDAO;

public class MVC {

    public static void main(String[] args) throws SQLException {
        Conexao con = new Conexao();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UsuarioDTO usuario = new UsuarioDTO();
        con.getConnection();
        usuario.setNome("nome");
        usuario.setEmail("email");
        usuario.setSenha("senha");
        usuario.setLogin("login");
        usuarioDAO.cadastrarUsuario(usuario);
        usuarioDAO.selecionarUsuarios();
        usuario.setNome("nome atualizado");
        usuario.setEmail("email atualizado");
        usuario.setSenha("senha atualizado");
        usuario.setLogin("login atualizado");
        usuarioDAO.atualizarUsuario(usuario);
        usuarioDAO.selecionarUsuarios();
        usuarioDAO.deletarUsuario(usuario.getId());
        usuarioDAO.selecionarUsuarios();
    }
    
}
