package model;


import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import static util.DialogUtil.showError;

public class UsuarioDAO {
    public void cadastrarUsuario(UsuarioDTO usuario) throws SQLException{
        String sql = "INSERT INTO usuario(nome, email, senha, login) VALUES (?,?,?,?)";
        
        try(Connection con = new Conexao().getConnection(); PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getLogin());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                usuario.setId(rs.getInt(1)); 
            }
        }
        }catch(SQLException e){
            e.printStackTrace();
            showError("Erro ao cadastrar o usuário");
        }
    }
    
    public void selecionarUsuarios(){
        String sql = "SELECT * FROM usuario";
        try(Connection con = new Conexao().getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery();){
            while(rs.next()){
                //System.out.println("#"+ rs.getInt("id")+ " # "+rs.getString("nome")+ " # "+rs.getString("email")+ " # "+rs.getString("senha")+ " # "+rs.getString("login"));
            }
        }catch(SQLException ex){
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            showError("Erro ao selecionar os usuários");
        }
    }
    public List<UsuarioDTO> listarUsuarios() {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection con = new Conexao().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UsuarioDTO usuario = new UsuarioDTO();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setLogin(rs.getString("login"));
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            showError("Erro ao listar os usuários");
        }

        return usuarios;
    }
    
    public void deletarUsuario(int id){
        String sql = "DELETE FROM usuario WHERE id = ?";
        try(Connection con = new Conexao().getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, id); 
            ps.executeUpdate();
        }catch(SQLException ex){
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            showError("Erro ao deletar o usuário");
        }
    }
    
    public void atualizarUsuario(UsuarioDTO usuario) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, login = ? WHERE id = ?";
        try (Connection con = new Conexao().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getLogin());
            ps.setInt(5, usuario.getId()); 
            int linhas = ps.executeUpdate();
            if (linhas > 0) {
                //System.out.println("Usuário com ID " + usuario.getId() + " atualizado com sucesso.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            showError("Erro ao atualizar o usuário");
        }
    }
}
