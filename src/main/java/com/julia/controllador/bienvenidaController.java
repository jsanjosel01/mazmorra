package proyecto.controllador;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import proyecto.App;
import proyecto.modelos.SceneId;
import proyecto.modelos.SceneManager;

public class bienvenidaController {
     @FXML
    private AnchorPane ancho;

    @FXML
    private Button btnEmpezar;

    @FXML
    private void initialize() {
        // Ruta relativa a resources
        String rutaImg = "/proyecto/imagenes/bienvenido.jpg";

        // Establecer imagen de fondo usando CSS inline
        ancho.setStyle("-fx-background-image: url('" + getClass().getResource(rutaImg) + "'); " +
                       "-fx-background-size: cover; " +
                       "-fx-background-position: center center;");

        // Acción del botón
        btnEmpezar.setOnAction(event -> {
            // Cambia a la vista llamada VISTA1 usando el SceneManager
            SceneManager.getInstance().loadScene(SceneId.VISTA1);
        });
    }


    @FXML
    private void onBtnEmpezarClicked() {
        // Cambiar a la escena de vista1.fxml
        cambiarEscena("vista1.fxml");
    }

    private void cambiarEscena(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("views/" + rutaFXML));
            Parent root = loader.load();

            // Obtener la ventana actual
            Stage stage = (Stage) ancho.getScene().getWindow();
            Scene nuevaEscena = new Scene(root);

            stage.setScene(nuevaEscena);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista: " + rutaFXML);
        }
    }
}
