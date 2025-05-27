
package validator;

import java.util.regex.Pattern;


public class EmailValidador implements Validador<String> {
     private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.\\w+$";
    private final Pattern pattern = Pattern.compile(EMAIL_REGEX);
    private final String email; // Armazena o e-mail a ser validado

    public EmailValidador(String email) {
        this.email = email;
    }

    @Override
    public boolean validar(String valorAtual) { // O valor do parâmetro é o que será validado neste ciclo
        return this.email != null && pattern.matcher(this.email).matches();
    }

    @Override
    public String getMensagemErro() {
        return "Digite um e-mail válido (exemplo@dominio.com)!";
    }

    @Override
    public String getValor() {
        return email;
    }
}
