package com.julia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author Julia San José León 
 * Clase principal de la aplicación JavaFX.
 * Controla el flujo inicial del programa y la gestión de escenas mediante SceneManager.
 * 
 */
public class App extends Application {
    /**
     * Instancia singleton del SceneManager que gestiona las diferentes escenas de la aplicación.
     */
    private static SceneManager sceneManager;

    /**
     * Método llamado al iniciar la aplicación.
     * Inicializa el SceneManager, registra las escenas disponibles y carga la escena inicial.
     * @param stage Ventana principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar las escenas.
     */
    @Override
    public void start(Stage stage) throws IOException {
        //Registra las escenas principales
        sceneManager = SceneManager.getInstance();
        sceneManager.setScene(SceneId.BIENVENIDA, "bienvenida");
        sceneManager.setScene(SceneId.VISTA1, "vista1");
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

    /**
     * Método principal que lanza la aplicación JavaFX.
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        launch();
    }
}