
package validator;


public interface Validador<T> {
    boolean validar(T valor);
    String getMensagemErro();
    T getValor();
}
