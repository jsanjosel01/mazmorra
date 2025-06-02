package proyecto.modelos;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

     private static SceneManager instance;
    private Stage stage;
    private HashMap<SceneId, Scene> scenes;

    private SceneManager() {
        scenes = new HashMap<>();
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void init(Stage primaryStage) {
        this.stage = primaryStage;
    }

    /**
     * Carga un archivo FXML, crea una escena y la guarda en el mapa con su SceneId.
     * @param sceneId Identificador de la escena
     * @param fxmlFile Nombre del archivo FXML (sin extensión)
     * @param j 
     * @param i 
     */
    public void setScene(SceneId sceneId, String fxmlFile, int i, int j) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/proyecto/vista/" + fxmlFile + ".fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setUserData(loader); // guardar el loader para acceder al controlador luego
            scenes.put(sceneId, scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cambia la escena activa sin pasar datos.
     * @param sceneId Escena a mostrar
     */
    public void loadScene(SceneId sceneId) {
        Scene scene = scenes.get(sceneId);
        if (scene == null) {
            throw new IllegalStateException("La escena " + sceneId + " no está configurada.");
        }
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cambia la escena activa y pasa un objeto al controlador,
     * si el controlador tiene el método correspondiente.
     * @param sceneId Escena a mostrar
     * @param data Objeto a pasar al controlador
     */
    public void loadSceneWithData(SceneId sceneId, Object data) {
        Scene scene = scenes.get(sceneId);
        if (scene == null) {
            throw new IllegalStateException("La escena " + sceneId + " no está configurada.");
        }
        FXMLLoader loader = (FXMLLoader) scene.getUserData();
        if (loader != null) {
            Object controller = loader.getController();
            try {
                // Asume que el controlador tiene un método setData(Object)
                controller.getClass().getMethod("setData", Object.class).invoke(controller, data);
            } catch (Exception e) {
                System.err.println("El controlador no tiene método setData(Object) o falló la invocación.");
                e.printStackTrace();
            }
        }
        stage.setScene(scene);
        stage.show();
    }

    public Scene getScene(SceneId sceneId) {
        return scenes.get(sceneId);
    }
}
