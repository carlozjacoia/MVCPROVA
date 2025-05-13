
package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.UsuarioDAO;
import model.UsuarioDTO;
import static util.DialogUtil.showConfirmation;
import static util.DialogUtil.showError;


public class FXMLDocumentController implements Initializable {
    
    @FXML 
    private TableColumn<UsuarioDTO, Integer> colId;
    
    @FXML 
    private TableColumn<UsuarioDTO, String> colNome;
    
    @FXML 
    private TableColumn<UsuarioDTO, String> colEmail;
    
    @FXML 
    private TableColumn<UsuarioDTO, String> colSenha;
    
    @FXML 
    private TableColumn<UsuarioDTO, String> colLogin;

    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtSenha;
    
    @FXML
    private TextField txtLogin;
    
    @FXML
    private Button btnLimpar;
    
    @FXML
    private Button btnCadastrar;
    
    @FXML
    private Button btnExcluir;
    
    @FXML
    private Button btnAtualizar;
    
    @FXML
    private TableView tblUsuario;
    
    //valida o email
    private boolean emailValido(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
    
    //limpa os campos
    @FXML
    private void limparCampos(ActionEvent event){
        limparTexto();
    }
    
    public void limparTexto(){
        txtNome.setText("");
        txtSenha.setText("");
        txtEmail.setText("");
        txtLogin.setText("");
        btnAtualizar.setDisable(true);
        btnExcluir.setDisable(true);
    }
    
    @FXML
    private void cadastrar(ActionEvent event) throws SQLException {
        if(!txtNome.getText().isEmpty() && !txtSenha.getText().isEmpty() && !txtEmail.getText().isEmpty() && !txtLogin.getText().isEmpty()) {
            if (!emailValido(txtEmail.getText())) {
                //se o email nao for valido, emite um alerta na tela
                showError("E-mail inválido. Use o formato exemplo@dominio.com");
                return;
            }
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            String nome = txtNome.getText();
            String senha = txtSenha.getText();
            String email = txtEmail.getText();
            String login = txtLogin.getText();
            UsuarioDTO novoUsuario = new UsuarioDTO(nome, email, senha, login);
            usuarioDAO.cadastrarUsuario(novoUsuario);
            limparTexto();
        }else{
            showError("Preencha todos os campos antes de cadastrar.");
        }
       AtualizarTela();
    }
    
    @FXML
    private void deletar(ActionEvent event) throws SQLException {
        UsuarioDTO selecionado = selecionarUsuario();
        if (selecionado == null) {
            showError("Nenhum usuário selecionado.");
            return;
        }
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (showConfirmation(selecionado.getNome(), "excluir")) {
            usuarioDAO.deletarUsuario(selecionado.getId());
            limparTexto();
        }
       AtualizarTela();
    }
    
    @FXML
    private void selecionarUsuario(MouseEvent event){
        UsuarioDTO selecionado = selecionarUsuario();
        txtNome.setText(selecionado.getNome());
        txtEmail.setText(selecionado.getEmail());
        txtSenha.setText(selecionado.getSenha());
        txtLogin.setText(selecionado.getLogin());
        btnAtualizar.setDisable(false);
        btnExcluir.setDisable(false);
    }
    
    @FXML
    private void atualizar(ActionEvent event) throws SQLException {
        UsuarioDTO selecionado = selecionarUsuario();
        if(!txtNome.getText().isEmpty() && !txtSenha.getText().isEmpty() && !txtEmail.getText().isEmpty() && !txtLogin.getText().isEmpty()) {
            //confirmação
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            if (showConfirmation(selecionado.getNome(), "atualizar")) {
                if (!emailValido(txtEmail.getText())) {
                    showError("E-mail inválido. Use o formato exemplo@dominio.com");
                    return;
                }
                String nome = txtNome.getText();
                String senha = txtSenha.getText();
                String email = txtEmail.getText();
                String login = txtLogin.getText();
                selecionado.setNome(nome);
                selecionado.setSenha(senha);
                selecionado.setEmail(email);
                selecionado.setLogin(login);
                usuarioDAO.atualizarUsuario(selecionado);
            }
        }else{
            showError("Preencha todos os campos antes de cadastrar.");
        }
       AtualizarTela();
    }
    
    public UsuarioDTO selecionarUsuario(){
        UsuarioDTO selecionado = (UsuarioDTO) tblUsuario.getSelectionModel().getSelectedItem();
        if(selecionado != null){
            return selecionado;
        }else{
            showError("Nenhum usuário selecionado.");
            return null;
        }
    }
    
    public void AtualizarTela(){
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<UsuarioDTO> listaUsuarios = usuarioDAO.listarUsuarios();
        Collections.sort(listaUsuarios, Comparator.comparingInt(UsuarioDTO::getId));
        ObservableList<UsuarioDTO> usuarios = FXCollections.observableArrayList(listaUsuarios);
        tblUsuario.setItems(usuarios);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //fazer o tab correr corretamente na ordem certa
        txtNome.setFocusTraversable(true);
        txtEmail.setFocusTraversable(true);
        txtSenha.setFocusTraversable(true);
        txtLogin.setFocusTraversable(true);
        //atribuindo o valor de cada coluna a um atributo específico
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        limparTexto();
        AtualizarTela();
    }       
}
