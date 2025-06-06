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
    private Slider defensaSlider;

    @FXML
    private Slider velocidadSlider;

    @FXML
    private Slider ataqueSlider;

    @FXML
    private Slider fuerzaSlider;

    @FXML
    private Button startButton;

    @FXML
    private void initialize() {
        defensaSlider.setValue(5);
        velocidadSlider.setValue(5);
        ataqueSlider.setValue(5);
        fuerzaSlider.setValue(5);

        startButton.setOnAction(event -> onStartClicked());
    }

    @FXML
    private void onStartClicked() {
        String nombre = nombreField.getText();
        double defensa = defensaSlider.getValue();
        double velocidad = velocidadSlider.getValue();
        double ataque = ataqueSlider.getValue();
        double fuerza = fuerzaSlider.getValue();

        if (nombre.isEmpty()) {
            mostrarError("Debes darle un nombre al personaje");
            return;
        }

        if (defensa <= 0 || velocidad <= 0 || ataque <= 0 || fuerza <= 0) {
            mostrarError("Todos los atributos deben ser mayores a cero.");
            return;
        }

        Posicion posicionInicial = new Posicion(0, 0);
        int vidaMaxima = 100; // o el valor que quieras asignar

        Heroe heroe = new Heroe(nombre,posicionInicial,vidaMaxima,(int) fuerza,(int) defensa,(int) velocidad);

    heroe.setAtaque((int) ataque);
        //Guardar el héroe en el proveedor
        Proveedor.getInstance().setHeroe(heroe);

        SceneManager.getInstance().loadScene(SceneId.VISTA2);
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error de validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
       
}
    

