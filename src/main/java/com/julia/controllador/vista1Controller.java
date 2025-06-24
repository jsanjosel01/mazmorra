package com.julia.controllador;

import com.julia.SceneId;
import com.julia.SceneManager;
import com.julia.modelos.MotorJuego;
import com.julia.modelos.Heroe;
import com.julia.modelos.Proveedor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
/**
 * Controlador para la vista1 donde se configura el héroe y se inicia el juego.
 */
public class vista1Controller {
    /**
     * Campo de texto para ingresar el nombre del héroe.
     */
    @FXML
    private TextField nombre;
     /**
     * Campo de texto para ingresar la defensa del héroe.
     */
    @FXML
    private TextField defensa;
     /**
     * Campo de texto para ingresar la fuerza del héroe.
     */
    @FXML
    private TextField fuerza;
    /**
     * Campo de texto para ingresar la vida del héroe.
     */
    @FXML
    private TextField vida;
    /**
     * Botón para iniciar el juego después de configurar el héroe.
     */
    @FXML
    private Button startButton;
    /**
     * Panel principal de la vista1.
     */
    @FXML
    private AnchorPane Vista1;
    
    /**
     * Método que se ejecuta automáticamente al cargar la vista,
     * configura el evento del botón para crear el héroe y empezar el juego.
     */
    @FXML
    private void initialize() {
        startButton.setOnAction(event -> {
         try {
                String nombreField = nombre.getText();
                int vidafield = Integer.parseInt(vida.getText());
                int defensafield = Integer.parseInt(defensa.getText());
                int fuerzafield = Integer.parseInt(fuerza.getText());

                // Valida que los puntos sumen exactamente 100 y que no sean negativos
                if (vidafield < 0 || defensafield < 0 || fuerzafield < 0 || (vidafield + defensafield + fuerzafield) != 100) {
                    mostrarError("Error", "Los puntos deben sumar 100.");
                    return;
                }
                // Crea un nuevo héroe
                Heroe heroe = new Heroe(nombreField, defensafield, fuerzafield, fuerzafield, vidafield);
                // Inicializa el motor de juego con el mapa y el héroe
                MotorJuego motorJuego = new MotorJuego("/dataUrl/escenario.txt", heroe);
                Proveedor.getInstance().inicializar(heroe, motorJuego);
                
                // Cambia la escena a la vista2 donde se desarrolla el juego
                SceneManager.getInstance().setScene(SceneId.VISTA2, "Vista2");
                SceneManager.getInstance().loadScene(SceneId.VISTA2);

            } catch (NumberFormatException e) {
                mostrarError("Error de formato", "Ingrese valores numéricos válidos.");
            } catch (Exception e) {
                e.printStackTrace();
                mostrarError("Error", "Ocurrió un error..");
            }
        });
    }

    /**
     * Muestra un cuadro de diálogo de error con el título y mensaje especificados.
     * @param titulo Título de la ventana de error.
     * @param mensaje Mensaje a mostrar en el cuadro de diálogo.
     */
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
