package proyecto.controllador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import proyecto.App;
import proyecto.modelos.Heroe;
import proyecto.modelos.Posicion;

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
        Heroe heroe = new Heroe(nombre, posicionInicial);
        heroe.setDefensa((int) defensa);
        heroe.setVelocidad((int) velocidad);
        heroe.setAtaque((int) ataque);
        heroe.setFuerza((int) fuerza);

        //Guardar el héroe en App
        App.setHeroe(heroe);

        //Cambiar a la segunda vista
        try {
            App.setRoot("vista2");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error al cargar la siguiente vista.");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error de validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
       
}
    

