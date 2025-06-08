package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.UsuarioDAO;
import model.UsuarioDTO;
import static util.DialogUtil.showConfirmation;
import static util.DialogUtil.showError;
import validator.UsuarioValidator;

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

    private final UsuarioValidator usuarioValidador = new UsuarioValidator();

    //limpa os campos
    @FXML
    private void limparCampos(ActionEvent event) {
        limparTexto();
    }

    public void limparTexto() {
        txtNome.setText("");
        txtSenha.setText("");
        txtEmail.setText("");
        txtLogin.setText("");
        btnAtualizar.setDisable(true);
        btnExcluir.setDisable(true);
    }

    @FXML
    private void cadastrar(ActionEvent event) throws SQLException {
        if (usuarioValidador.validarUsuario(txtNome.getText(), txtEmail.getText(), txtSenha.getText(), txtLogin.getText())) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            String nome = txtNome.getText();
            String senha = txtSenha.getText();
            String email = txtEmail.getText();
            String login = txtLogin.getText();
            UsuarioDTO novoUsuario = new UsuarioDTO(nome, email, senha, login);
            usuarioDAO.cadastrarUsuario(novoUsuario);
            limparTexto();
        }
        AtualizarTela();
    }

    @FXML
    private void deletar(ActionEvent event) throws SQLException {
        UsuarioDTO usuarioSelecionado = selecionarUsuario();
        if (usuarioSelecionado == null) {
            showError("Nenhum usuário usuarioSelecionado.");
            return;
        }
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (showConfirmation(usuarioSelecionado.getNome(), "excluir")) {
            usuarioDAO.deletarUsuario(usuarioSelecionado.getId());
            limparTexto();
        }
        AtualizarTela();
    }

    @FXML
    private void selecionaUsuario(MouseEvent event) {
        UsuarioDTO usuarioSelecionado = selecionarUsuario();
        txtNome.setText(usuarioSelecionado.getNome());
        txtEmail.setText(usuarioSelecionado.getEmail());
        txtSenha.setText(usuarioSelecionado.getSenha());
        txtLogin.setText(usuarioSelecionado.getLogin());
        btnAtualizar.setDisable(false);
        btnExcluir.setDisable(false);
    }

    @FXML
    private void atualizar(ActionEvent event) throws SQLException {
        UsuarioDTO usuarioSelecionado = selecionarUsuario();
        if (usuarioValidador.validarUsuario(txtNome.getText(), txtEmail.getText(), txtSenha.getText(), txtLogin.getText())) {
            //confirmação
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            if (showConfirmation(usuarioSelecionado.getNome(), "atualizar")) {
                String nome = txtNome.getText();
                String senha = txtSenha.getText();
                String email = txtEmail.getText();
                String login = txtLogin.getText();
                usuarioSelecionado.setNome(nome);
                usuarioSelecionado.setSenha(senha);
                usuarioSelecionado.setEmail(email);
                usuarioSelecionado.setLogin(login);
                usuarioDAO.atualizarUsuario(usuarioSelecionado);
            }
        }
        AtualizarTela();
    }

    public UsuarioDTO selecionarUsuario() {
        UsuarioDTO usuarioSelecionado = (UsuarioDTO) tblUsuario.getSelectionModel().getSelectedItem();
        return usuarioSelecionado;
    }

    public void AtualizarTela() {
        UsuarioDTO usuarioSelecionado = (UsuarioDTO) tblUsuario.getSelectionModel().getSelectedItem();
        Integer idSelecionado = (usuarioSelecionado != null) ? usuarioSelecionado.getId() : null;

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<UsuarioDTO> listaUsuarios = usuarioDAO.listarUsuarios();
        Collections.sort(listaUsuarios, Comparator.comparingInt(UsuarioDTO::getId));
        ObservableList<UsuarioDTO> usuarios = FXCollections.observableArrayList(listaUsuarios);
        tblUsuario.setItems(usuarios);

        // Reselecionar o item se possível
        if (idSelecionado != null) {
            for (UsuarioDTO u : usuarios) {
                if (u.getId() == idSelecionado) {
                    tblUsuario.getSelectionModel().select(u);
                    break;
                }
            }
        }
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
