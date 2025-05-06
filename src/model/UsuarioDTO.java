package model;


public class UsuarioDTO {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String login;

    public UsuarioDTO(String nome, String email, String senha, String login) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.login = login;
    }

    public UsuarioDTO() {
    }
    
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
}
