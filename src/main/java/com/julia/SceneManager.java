package com.julia;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * {@code SceneManager} es un singleton encargado de gestionar el cambio y almacenamiento
 * de escenas en la aplicación JavaFX. Permite cargar FXML, asociar controladores
 * y cambiar de escena de forma centralizada.
 */
public class SceneManager {
    /** Instancia única de SceneManager (patrón Singleton). */
    private static SceneManager instance;
    /** Ventana principal de la aplicación JavaFX. */
    private Stage stage;
    /** Mapa que asocia identificadores de escena con objetos Scene. */
    private HashMap<SceneId, Scene> scenes;

    /**
     * Constructor privado para evitar la creación de múltiples instancias.
     * Inicializa el mapa de escenas.
     */
    private SceneManager() {
        scenes = new HashMap<>();
    }

    /**
     * Obtiene la instancia única de SceneManager.
     * Si no existe, la crea.
     * @return Instancia única de SceneManager.
     */
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    /**
     * Inicializa la referencia al {@link Stage} principal de la aplicación.
     * @param stage Ventana principal de JavaFX.
     */
    public void init(Stage stage) {
        this.stage = stage;
    }

    /**
     * Carga un archivo FXML, crea una nueva escena y la almacena en el mapa de escenas
     * bajo el identificador proporcionado.
     * @param sceneID Identificador único de la escena.
     * @param fxml  Nombre del archivo FXML (sin la extensión) que define la vista.
     */
    public void setScene(SceneId sceneID, String fxml) {
        try {
            URL url = App.class.getResource("views/" + fxml + ".fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 400);
            scene.setUserData(fxmlLoader); 
            scenes.put(sceneID, scene); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cambia la escena actual a la asociada al identificador proporcionado.
     * Si el controlador de la escena es una instancia de una clase concreta,
     * le pasa un objeto si es necesario antes de mostrar la escena.
     * 
     * @param sceneID Identificador de la escena a mostrar.
     * @param data Instancia de datos a pasar al controlador.
     * @throws IllegalStateException Si la escena no ha sido configurada previamente.
     */
    public void loadSceneWithData(SceneId sceneID, Object data) {
        if (scenes.containsKey(sceneID)) {
            Scene scene = scenes.get(sceneID);

            // Setear el controlador ANTES de mostrar la escena
            FXMLLoader loader = (FXMLLoader) scene.getUserData();
            if (loader != null) {
                Object controller = loader.getController();
                try {
                    controller.getClass().getMethod("setData", Object.class).invoke(controller, data);
                } catch (Exception e) {
                    System.err.println("El controlador no tiene método setData.");
                }
            }
            stage.setScene(scene); 
            stage.show();
        } else {
            throw new IllegalStateException("La escena " + sceneID + " no está configurada.");
        }
    }

    /**
     * Cambia la escena actual a la asociada al identificador proporcionado.
     * @param sceneID Identificador de la escena a mostrar.
     * @throws IllegalStateException Si la escena no ha sido configurada previamente.
     */
    public void loadScene(SceneId sceneID) {
        if (scenes.containsKey(sceneID)) {
            Scene scene = scenes.get(sceneID);
            stage.setScene(scene);
            stage.show();
        } else {
            throw new IllegalStateException("La escena " + sceneID + " no está configurada.");
        }
    }

    /**
     * Devuelve la escena asociada al identificador proporcionado.
     * @param id Identificador de la escena.
     * @return La escena correspondiente, o {@code null} si no existe.
     */
    public Scene getScene(SceneId id) {
        return scenes.get(id);
    }
}