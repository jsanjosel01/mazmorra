package com.julia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App principal
 * Julia San José León 
 */
public class App extends Application {

    // Instancia del SceneManager
    private static SceneManager sceneManager;

    @Override
    public void start(Stage stage) throws IOException {
        //Inicializa el SceneManager (singleton)
        sceneManager = SceneManager.getInstance();

        //Registra las escenas principales
        sceneManager.setScene(SceneId.BIENVENIDA, "bienvenida");
        sceneManager.setScene(SceneId.VISTA1, "vista1");
        sceneManager.setScene(SceneId.VISTA2, "vista2");
        sceneManager.init(stage);

        //Carga la escena de bienvenida al iniciar
        sceneManager.loadScene(SceneId.BIENVENIDA);
        stage.setTitle("Videojuego Mazmorras");
        stage.show();
    }

    /**
     * Método estático para cambiar la raíz de la escena principal
     */
    public static void setRoot(String fxml) throws IOException {
        sceneManager.loadScene(SceneId.valueOf(fxml.toUpperCase()));
    }

    public static void main(String[] args) {
        launch();
    }
}