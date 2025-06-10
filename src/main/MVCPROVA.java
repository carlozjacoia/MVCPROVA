/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package main;

import controller.FXMLDocumentController;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import validator.IUsuarioValidator;
import validator.UsuarioValidator;

/**
 *
 * @author ra2357046
 */
public class MVCPROVA extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        IUsuarioValidator usuarioValidator = new UsuarioValidator();
        FXMLLoader loader = new FXMLLoader();
        URL fxmlLocation = getClass().getResource("/view/FXMLDocument.fxml");
        if (fxmlLocation == null) {
            System.err.println("Erro: FXMLDocument.fxml não encontrado. Verifique o caminho.");
            return;
        }
        loader.setLocation(fxmlLocation);
        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == FXMLDocumentController.class) {
                return new FXMLDocumentController(usuarioValidator);
            }
            try {
                return controllerClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
