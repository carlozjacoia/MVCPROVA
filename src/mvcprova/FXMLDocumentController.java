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
    private TextField txtNome1;
    
    @FXML
    private TextField txtEmail1;
    
    @FXML
    private TextField txtSenha1;
    
    @FXML
    private TextField txtLogin1;
    
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
    
    @FXML
    private void cadastrar(ActionEvent event) throws SQLException {
        if(txtNome.getText() != "" && txtSenha.getText() != "" && txtEmail.getText() != "" && txtLogin.getText() != ""){
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
            txtNome1.setText("");
           txtEmail1.setText("");
           txtSenha1.setText("");
           txtLogin1.setText("");
        }
        att();
    }
    
    @FXML
    private void atualizar(ActionEvent event) throws SQLException {
        if(txtNome1.getText() != "" && txtSenha1.getText() != "" && txtEmail1.getText() != "" && txtLogin1.getText() != ""){
            String nome = txtNome1.getText();
            String senha = txtSenha1.getText();
            String email = txtEmail1.getText();
            String login = txtLogin1.getText();
            selecionado.setNome(nome);
            selecionado.setSenha(senha);
            selecionado.setEmail(email);
            selecionado.setLogin(login);
            U.atualizarUsuario(selecionado);
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
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
        colunaLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        btAtualizar.setDisable(true);
        btExcluir.setDisable(true);
        tb.setOnMouseClicked(event -> {
           selecionado =  (UsuarioDTO) tb.getSelectionModel().getSelectedItem();
           txtNome1.setText(selecionado.getNome());
           txtEmail1.setText(selecionado.getEmail());
           txtSenha1.setText(selecionado.getSenha());
           txtLogin1.setText(selecionado.getLogin());
           btAtualizar.setDisable(false);
           btExcluir.setDisable(false);
        });
        att();
    }       
}
