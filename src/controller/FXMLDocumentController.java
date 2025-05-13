/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.UsuarioDAO;
import model.UsuarioDTO;
import static util.DialogUtil.showError;
import static util.DialogUtil.showInfo;


/**
 *
 * @author ra2357046
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML 
    private TableColumn<UsuarioDTO, Integer> colunaId;
    
    @FXML 
    private TableColumn<UsuarioDTO, String> colunaNome;
    
    @FXML 
    private TableColumn<UsuarioDTO, String> colunaEmail;
    
    @FXML 
    private TableColumn<UsuarioDTO, String> colunaSenha;
    
    @FXML 
    private TableColumn<UsuarioDTO, String> colunaLogin;

    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtSenha;
    
    @FXML
    private TextField txtLogin;
    
    @FXML
    private Button btLimpar;
    
    @FXML
    private Button btCadastrar;
    
    @FXML
    private Button btExcluir;
    
    @FXML
    private Button btAtualizar;
    
    @FXML
    private TableView table;
    
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
        btAtualizar.setDisable(true);
        btExcluir.setDisable(true);
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
            UsuarioDTO u = new UsuarioDTO(nome, email, senha, login);
            usuarioDAO.cadastrarUsuario(u);
            limparTexto();
        }else{
            showError("Preencha todos os campos antes de cadastrar.");
        }
       AtualizarTela();
    }
    
    @FXML
    private void deletar(ActionEvent event) throws SQLException {
        UsuarioDTO selecionado = (UsuarioDTO) table.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            showError("Nenhum usuário selecionado.");
            return;
        }
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText(null);
        alert.setContentText("Certeza que deseja deletar o usuário " + selecionado.getNome() + "?");
        ButtonType botaoSim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType botaoNao = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(botaoSim, botaoNao);
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == botaoSim) {
            usuarioDAO.deletarUsuario(selecionado.getId());
            limparTexto();
        }
       AtualizarTela();
    }
    
    @FXML
    private void selecionarUsuario(MouseEvent event){
        UsuarioDTO selecionado =  (UsuarioDTO) table.getSelectionModel().getSelectedItem();
        txtNome.setText(selecionado.getNome());
        txtEmail.setText(selecionado.getEmail());
        txtSenha.setText(selecionado.getSenha());
        txtLogin.setText(selecionado.getLogin());
        btAtualizar.setDisable(false);
        btExcluir.setDisable(false);
    }
    
    @FXML
    private void atualizar(ActionEvent event) throws SQLException {
        UsuarioDTO selecionado = (UsuarioDTO) table.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            showError("Nenhum usuário selecionado.");
            return;
        }
        if(!txtNome.getText().isEmpty() && !txtSenha.getText().isEmpty() && !txtEmail.getText().isEmpty() && !txtLogin.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Atualização");
            alert.setHeaderText(null);
            alert.setContentText("Certeza que deseja atualizar o usuário " + selecionado.getNome() + "?");
            ButtonType botaoSim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
            ButtonType botaoNao = new ButtonType("Não", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(botaoSim, botaoNao);
            Optional<ButtonType> resultado = alert.showAndWait();
            if (resultado.isPresent() && resultado.get() == botaoSim) {
                if (!emailValido(txtEmail.getText())) {
                    showError("E-mail inválido. Use o formato exemplo@dominio.com");
                    return;
                }
                UsuarioDAO usuarioDAO = new UsuarioDAO();
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
    
    public void AtualizarTela(){
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<UsuarioDTO> listaUsuarios = usuarioDAO.listarUsuarios();
        Collections.sort(listaUsuarios, Comparator.comparingInt(UsuarioDTO::getId));
        ObservableList<UsuarioDTO> usuarios = FXCollections.observableArrayList(listaUsuarios);
        table.setItems(usuarios);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //fazer o tab correr corretamente na ordem certa
        txtNome.setFocusTraversable(true);
        txtEmail.setFocusTraversable(true);
        txtSenha.setFocusTraversable(true);
        txtLogin.setFocusTraversable(true);
        //atribuindo o valor de cada coluna a um atributo específico
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
        colunaLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        limparTexto();
        AtualizarTela();
    }       
}
