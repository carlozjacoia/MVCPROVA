/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package mvcprova;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mvc.UsuarioDAO;
import mvc.UsuarioDTO;

/**
 *
 * @author ra2357046
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtSenha;
    
    @FXML
    private TextField txtLogin;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {
        UsuarioDAO U = new UsuarioDAO();
        String nome = txtNome.getText();
        String senha = txtSenha.getText();
        String email = txtEmail.getText();
        String login = txtLogin.getText();
        UsuarioDTO u = new UsuarioDTO(nome, email, senha, login);
        U.cadastrarUsuario(u);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
