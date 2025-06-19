package com.julia.controllador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import com.julia.App;
import com.julia.SceneId;
import com.julia.SceneManager;
import com.julia.modelos.Heroe;
import com.julia.modelos.Posicion;
import com.julia.modelos.Proveedor;

public class vista1Controller {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField defensa;

    @FXML
    private TextField velocidad;

    @FXML
    private TextField ataque;

    @FXML
    private TextField fuerza;

    @FXML
    private Button startButton;

    @FXML
    private void initialize() {
        startButton.setOnAction(event -> onStartClicked());
    }

     private void onStartClicked() {
        String nombre = nombreField.getText().trim();
        if (nombre.isEmpty()) {
            mostrarError("Debes darle un nombre al personaje.");
            return;
        }

        int defensaVal, velocidadVal, ataqueVal, fuerzaVal;

        try {
            defensaVal = Integer.parseInt(defensa.getText().trim());
            velocidadVal = Integer.parseInt(velocidad.getText().trim());
            ataqueVal = Integer.parseInt(ataque.getText().trim());
            fuerzaVal = Integer.parseInt(fuerza.getText().trim());
        } catch (NumberFormatException e) {
            mostrarError("Todos los atributos deben ser números enteros válidos.");
            return;
        }

        if (defensaVal < 0 || velocidadVal < 0 || ataqueVal < 0 || fuerzaVal < 0) {
            mostrarError("Los atributos no pueden ser negativos.");
            return;
        }

        int suma = defensaVal + velocidadVal + ataqueVal + fuerzaVal;
        if (suma != 40) {
            mostrarError("La suma debe ser 40. Actualmente es: " + suma);
            return;
        }

        Posicion posicionInicial = new Posicion(0, 0);
        int vidaMaxima = 100;

        Heroe heroe = new Heroe(nombre, posicionInicial, vidaMaxima, fuerzaVal, defensaVal, velocidadVal);
        heroe.setAtaque(ataqueVal);

        Proveedor.getInstance().setHeroe(heroe);
        SceneManager.getInstance().loadScene(SceneId.VISTA2);
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}