package com.julia.controllador;

import com.julia.SceneId;
import com.julia.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * Controlador de la vista de bienvenida.
 * Muestra la pantalla inicial y gestiona el cambio a la siguiente vista cuando el usuario pulsa "Empezar".
 */
public class bienvenidaController {

    /** Botón para comenzar el juego y pasar a la siguiente vista. */
    @FXML
    private Button btnEmpezar;

    /** Panel raíz de la vista de bienvenida, utilizado para establecer el fondo. */
    @FXML
    private AnchorPane ancho;

    /**
     * Inicializa la vista de bienvenida.
     * Establece la imagen de fondo y configura el evento del botón para cambiar de escena.
     */
    @FXML
    public void initialize() {
        String rutaImg = "/proyecto/imagenes/bienvenido.jpg";
        ancho.setStyle("-fx-background-image: url('" + getClass().getResource(rutaImg) + "'); " +
                       "-fx-background-size: cover; -fx-background-position: center center;");

        btnEmpezar.setOnAction(event -> {
            // Cambia a la siguiente vista usando el SceneManager
            SceneManager.getInstance().loadScene(SceneId.VISTA1);
        });
    }
}
