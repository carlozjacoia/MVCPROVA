
package util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class DialogUtil {
    public static void showError(String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void showInfo(String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    
    public static boolean showConfirmation(String nome, String operacao){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmação de "+operacao);
        alert.setHeaderText(null);
        alert.setContentText("Você tem certeza que deseja "+operacao+" o usuário "+nome+"?");
        
        ButtonType sim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType nao = new ButtonType("Não", ButtonBar.ButtonData.NO);
        
        alert.getButtonTypes().setAll(sim, nao);
        Optional<ButtonType> resultado = alert.showAndWait();
        
        return resultado.get() == sim;
    }
}

