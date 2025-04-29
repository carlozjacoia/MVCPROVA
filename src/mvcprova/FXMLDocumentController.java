/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package mvcprova;

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
import mvc.UsuarioDAO;
import mvc.UsuarioDTO;

/**
 *
 * @author ra2357046
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private TableColumn<UsuarioDTO, Integer> colunaId;
    @FXML private TableColumn<UsuarioDTO, String> colunaNome;
    @FXML private TableColumn<UsuarioDTO, String> colunaEmail;
    @FXML private TableColumn<UsuarioDTO, String> colunaSenha;
    @FXML private TableColumn<UsuarioDTO, String> colunaLogin;

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
    private TableView tb;
    
    public UsuarioDAO U = new UsuarioDAO();
    public UsuarioDTO  selecionado;
    private boolean emailValido(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
    @FXML
    private void limpar(ActionEvent event){
        txtNome.setText("");
        txtSenha.setText("");
        txtEmail.setText("");
        txtLogin.setText("");
        selecionado = null;
        btAtualizar.setDisable(true);
        btExcluir.setDisable(true);
    }
    
    @FXML
    private void cadastrar(ActionEvent event) throws SQLException {
        if(!txtNome.getText().isEmpty() && !txtSenha.getText().isEmpty() && !txtEmail.getText().isEmpty() && !txtLogin.getText().isEmpty()) {
            if (!emailValido(txtEmail.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erro de E-mail");
                alert.setHeaderText(null);
                alert.setContentText("E-mail inválido. Use o formato exemplo@dominio.com");
                alert.showAndWait();
                return;
            }
            String nome = txtNome.getText();
            String senha = txtSenha.getText();
            String email = txtEmail.getText();
            String login = txtLogin.getText();
            UsuarioDTO u = new UsuarioDTO(nome, email, senha, login);
            U.cadastrarUsuario(u);
            txtNome.setText("");
            txtSenha.setText("");
            txtEmail.setText("");
            txtLogin.setText("");
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos antes de cadastrar!");
            alert.showAndWait();
        }
        att();
    }
    
    @FXML
    private void deletar(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText(null);
        alert.setContentText("Certeza que deseja deletar o usuário " + selecionado.getNome() + "?");

        ButtonType botaoSim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType botaoNao = new ButtonType("Não", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(botaoSim, botaoNao);

        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == botaoSim) {
            U.deletarUsuario(selecionado.getId());
            btExcluir.setDisable(true);
            btAtualizar.setDisable(true);
            txtNome.setText("");
            txtEmail.setText("");
            txtSenha.setText("");
            txtLogin.setText("");
        }
        att();
    }
    
    @FXML
    private void atualizar(ActionEvent event) throws SQLException {
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
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Erro de E-mail");
                    alert.setHeaderText(null);
                    alert.setContentText("E-mail inválido. Use o formato exemplo@dominio.com");
                    alert.showAndWait();
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
                U.atualizarUsuario(selecionado);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos antes de atualizar!");
            alert.showAndWait();
        }
        att();
    }
    
    public void att(){
        List<UsuarioDTO> listaUsuarios = U.listarUsuarios();
        Collections.sort(listaUsuarios, Comparator.comparingInt(UsuarioDTO::getId));
        ObservableList<UsuarioDTO> usuarios = FXCollections.observableArrayList(listaUsuarios);
        tb.setItems(usuarios);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtNome.setFocusTraversable(true);
        txtEmail.setFocusTraversable(true);
        txtSenha.setFocusTraversable(true);
        txtLogin.setFocusTraversable(true);
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
        colunaLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        btAtualizar.setDisable(true);
        btExcluir.setDisable(true);
        tb.setOnMouseClicked(event -> {
           selecionado =  (UsuarioDTO) tb.getSelectionModel().getSelectedItem();
           txtNome.setText(selecionado.getNome());
           txtEmail.setText(selecionado.getEmail());
           txtSenha.setText(selecionado.getSenha());
           txtLogin.setText(selecionado.getLogin());
           btAtualizar.setDisable(false);
           btExcluir.setDisable(false);
        });
        att();
    }       
}
