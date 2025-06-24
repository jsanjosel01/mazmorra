package com.julia.controllador;

import com.julia.modelos.Celda;
import com.julia.modelos.Direccion;
import com.julia.modelos.Enemigo;
import com.julia.modelos.GestorPersonajes;
import com.julia.modelos.MotorJuego;
import com.julia.modelos.Heroe;
import com.julia.modelos.Proveedor;

import java.util.List;

import com.julia.interfaces.Observer;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
/**
 * Controlador de la vista2 que implementa la interfaz Observer para actualizar la UI según el estado del juego.
 */
public class vista2Controller implements Observer {

    // Imágenes del suelo y el muro (pared)
    private Image imgSuelo;
    private Image imgMuro;

    // Imágenes del héroe
    private Image imgHeroeUp;
    private Image imgHeroeDown;
    private Image imgHeroeLeft;
    private Image imgHeroeRight;

    // Imágenes de los enemigos
    private Image imgEnemigoDown;
    //private Image imgEnemigoup;
    //private Image imgEnemigoLeft;
    //private Image imgEnemigoRight;

    //IMAGEN DE LA TRAMPA Y MALDICION
    //private Image imgTrampa;
    private Image imgTele;
    

     /**
     * Contenedor principal horizontal para la vista2, donde se colocan la cuadrícula y paneles de información.
     */
    @FXML
    private HBox vista2;
    
    /**
     * Cuadrícula principal donde se dibuja el tablero del juego.
     */
    private GridPane mainGridPane;
    /**
     * Motor del juego que controla la lógica y el estado.
     */
    private MotorJuego motorJuego;
    /**
     * Referencia al héroe del juego.
     */
    private Heroe heroe;
    /**
     * Contenedor vertical para mostrar los datos del jugador.
     */
    private VBox datosJugador;
    /**
     * Contenedor vertical para mostrar las estadísticas o información de los enemigos.
     */
    private VBox datosEnemigos;

   /**
     * Permite establecer al héroe desde fuera del controlador.
     * Actualiza la vista si el GridPane ya está inicializado.
     * @param heroe Héroe a mostrar.
     */
    public void setHeroe(Heroe heroe) {
        this.heroe = heroe;
        Proveedor.getInstance().setHeroe(heroe);
        if (mainGridPane != null) {
            actualizarVista();
        }
    }

    /**
     * Inicializa la vista: carga imágenes, configura el mapa, añade observador,
     * crea el panel de estadísticas y configura los eventos de teclado.
     */
    @FXML
    public void initialize() {
        try {
            motorJuego = Proveedor.getInstance().getMotorJuego();
            heroe = Proveedor.getInstance().getHeroe();

            if (motorJuego == null || heroe == null) {
                System.err.println("No están inicializados aún.");
                return;
            }

            // Carga de las imágenes
            imgHeroeUp = GestorPersonajes.getImagen(GestorPersonajes.PROTA_ARRIBA);
            imgHeroeDown = GestorPersonajes.getImagen(GestorPersonajes.PROTA_ABAJO);
            imgHeroeLeft = GestorPersonajes.getImagen(GestorPersonajes.PROTA_IZQUIERDA);
            imgHeroeRight = GestorPersonajes.getImagen(GestorPersonajes.PROTA_DERECHA);
            
            imgEnemigoDown = GestorPersonajes.getImagen(GestorPersonajes.ENE_ABAJO);

            imgSuelo = GestorPersonajes.getImagen(GestorPersonajes.SUELO);
            imgMuro = GestorPersonajes.getImagen(GestorPersonajes.MURO);

            //imgTrampa = GestorPersonajes.getImagen(GestorPersonajes.TRAMPA);
            //imgMaldicion =GestorPersonajes.getImagen(GestorPersonajes.MALDICION);
            imgTele =GestorPersonajes.getImagen(GestorPersonajes.TELE);

            motorJuego.addObserver(this);
            mainGridPane = new GridPane();

            // Establecer el tamaño de las celdas
            for (int i = 0; i < motorJuego.getMapa().getAncho(); i++) {
                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.setMinWidth(50); // el ancho mínimo de cada columna
                columnConstraints.setPrefWidth(50); // el ancho preferido
                columnConstraints.setMaxWidth(50); // el ancho máximo
                mainGridPane.getColumnConstraints().add(columnConstraints);
            }

            for (int i = 0; i < motorJuego.getMapa().getAlto(); i++) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setMinHeight(50); // La altura mínima de cada fila
                rowConstraints.setPrefHeight(50); // La altura preferida
                rowConstraints.setMaxHeight(50); // La altura máxima
                mainGridPane.getRowConstraints().add(rowConstraints);
            }

            // VBox con estadísticas del heroe y de los enemigos
            datosJugador = new VBox(10); // Espacio vertical entre los label
            datosJugador.setPadding(new Insets(20));
            datosEnemigos = new VBox(20);
            datosEnemigos.setPadding(new Insets(20));
            actualizarDatosJugador();

            vista2.setSpacing(20); // Espacio entre mainGridPane y datosJugador/datosEnemigos
            vista2.getChildren().addAll(mainGridPane, datosJugador, datosEnemigos);

            actualizarVista();
            mainGridPane.setOnMouseClicked(event -> {
                mainGridPane.requestFocus();
            });
            mainGridPane.setOnKeyPressed(event -> manejarMovimiento(event));
            mainGridPane.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Actualiza los datos y las estadísticas del heroe(del jugador)
     */
    private void actualizarDatosJugador() {
        datosJugador.getChildren().clear();

        if (heroe != null) {
            Label lblEstadisticas = new Label(" Estadísticas del héroe: ");
            Label lblNombre = new Label(" Nombre: " + heroe.getNombre());
            Label lblDefensa = new Label(" Defensa: " + heroe.getDefensa());
            Label lblFuerza = new Label(" Fuerza: " + heroe.getFuerza());
            Label lblVida = new Label(" Vida: " + heroe.getPuntosVida());
            Label lblPosicion = new Label(
                    " Posición: fila " + heroe.getFila() + ", columna " + heroe.getColumna());

            String estiloLabel = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill:rgb(94, 11, 114);";
            lblEstadisticas.setStyle(estiloLabel);
            lblNombre.setStyle(estiloLabel);
            lblDefensa.setStyle(estiloLabel);
            lblFuerza.setStyle(estiloLabel);
            lblVida.setStyle(estiloLabel);
            lblPosicion.setStyle(estiloLabel);

             // Estilo para el VBox 
            datosJugador.setStyle(
            "-fx-background-color: #ffffff;" +  // Fondo blanco
            "-fx-border-color: #cccccc;" +      // Borde gris claro
            "-fx-border-width: 2px;" +          // Grosor del borde
            "-fx-border-radius: 10px;" +        // Bordes redondeados
            "-fx-background-radius: 10px;" +    // Redondeo también para el fondo
            "-fx-padding: 20px;" +              // Padding interno
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 4);" // Sombra suave
        );

        // Agregar todos los labels al VBox
        datosJugador.getChildren().addAll(lblEstadisticas, lblNombre, lblVida, lblDefensa, lblFuerza, lblPosicion);
        }
  
    }

    // Estadisticas de los Enemigos
    private void actualizarDatosEnemigos(List<Enemigo> enemigos) {
        datosEnemigos.getChildren().clear();

        if (enemigos.isEmpty()) {
            Label lblNoEnemigos = new Label("No hay enemigos");
            lblNoEnemigos.setStyle("-fx-font-size: 16px; -fx-text-fill: #cc3300;");
            datosEnemigos.getChildren().add(lblNoEnemigos);
            return;
        }

        Label lblTitulo = new Label(" Estadísticas de los enemigos: ");
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #cc3300;");
        datosEnemigos.getChildren().add(lblTitulo);

        for (Enemigo enemigo : enemigos) {
            Label lblNombre = new Label(" Enemigo: " + enemigo.getNombre());
            Label lblVida = new Label(" Vida: " + enemigo.getVida());
            Label lblPosicion = new Label(" Posición: fila " + enemigo.getFila() + ", columna " + enemigo.getColumna());

            String estiloLabel = "-fx-font-size: 16px; -fx-text-fill: #cc3300;";
            lblNombre.setStyle(estiloLabel);
            lblVida.setStyle(estiloLabel);
            lblPosicion.setStyle(estiloLabel);

             datosEnemigos.setStyle(
            "-fx-background-color: #ffffff;" +  // Fondo blanco
            "-fx-border-color: #cccccc;" +      // Borde gris claro
            "-fx-border-width: 2px;" +          // Grosor del borde
            "-fx-border-radius: 10px;" +        // Bordes redondeados
            "-fx-background-radius: 10px;" +    // Redondeo también para el fondo
            "-fx-padding: 20px;" +              // Padding interno
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 4);" // Sombra suave
             );
            VBox enemigoBox = new VBox(2);
            enemigoBox.getChildren().addAll(lblNombre, lblVida, lblPosicion);
            enemigoBox.setStyle("-fx-border-color: #cc3300; -fx-border-width: 1px; -fx-padding: 5px; -fx-border-radius: 5px;");

            datosEnemigos.getChildren().add(enemigoBox);
        }
    }

    /**
     * Gestiona el evento de teclado para mover al heroe según la tecla pulsada.
     * @param event Evento de teclado.
     */
    private void manejarMovimiento(KeyEvent event) {
        System.out.println("Tecla presionada: " + event.getCode());
        int nuevaFila = heroe.getFila();
        int nuevaColumna = heroe.getColumna();

        switch (event.getCode()) {
            case UP:
                nuevaFila--;
                break;
            case DOWN:
                nuevaFila++;
                break;
            case LEFT:
                nuevaColumna--;
                break;
            case RIGHT:
                nuevaColumna++;
                break;
            default:
                return;
        }
        motorJuego.moverHeroe(nuevaFila, nuevaColumna);
    }

    /**
     *  Actualiza las vistas
     */
    private void actualizarVista() {
        if (motorJuego == null || heroe == null || mainGridPane == null) {
            System.err.println("Vista no puede actualizarse");
            return;
        }
        // Verificar si el héroe ha muerto
        if (heroe.getPuntosVida() <= 0) {
            mostrarMensajeDerrota();
            return;
        }
        // Verificar si ya no quedan enemigos
        if (motorJuego.getGestorEnemigo().getEnemigos().isEmpty()) {
            mostrarMensajeVictoria();
        }

        mainGridPane.getChildren().clear();

        actualizarDatosJugador();
        actualizarDatosEnemigos(motorJuego.getGestorEnemigo().getEnemigos());

        // Dibujar el mapa
        for (int fila = 0; fila < motorJuego.getMapa().getAlto(); fila++) {
            for (int columna = 0; columna < motorJuego.getMapa().getAncho(); columna++) {
                Celda celda = motorJuego.getMapa().getCelda(fila, columna);
                ImageView celdaImageView = null;

                switch (celda.getTipo()) {
                    case SUELO:
                        celdaImageView = new ImageView(imgSuelo);
                        break;
                    case MURO:
                        celdaImageView = new ImageView(imgMuro);
                        break;
                    case TELE: //TRAMPA MALDICION
                        celdaImageView = new ImageView(imgTele);
                        break;
                    default:
                        continue;
                }

                celdaImageView.setFitWidth(60);
                celdaImageView.setFitHeight(60);
                mainGridPane.add(celdaImageView, columna, fila);
            }
        }
        
        // Dibujar al heroe
        Image imgProta;
        switch (heroe.getDireccion()) {
            case ARRIBA:
                imgProta = imgHeroeUp;
                break;
            case ABAJO:
                imgProta = imgHeroeDown;
                break;
            case IZQUIERDA:
                imgProta = imgHeroeLeft;
                break;
            case DERECHA:
                imgProta = imgHeroeRight;
                break;
            default:
                imgProta = imgHeroeDown;
                break;
        }

        ImageView heroeView = new ImageView(imgProta);
        heroeView.setFitWidth(60);
        heroeView.setFitHeight(60);
        mainGridPane.add(heroeView, heroe.getColumna(), heroe.getFila());

        // Dibujar a los enemigos
        motorJuego.getGestorEnemigo().getEnemigos().forEach(enemigo -> {
            if (enemigo.getDireccion() == null) {
                System.err.println("Enemigo sin dirección asignada en la posición: (" + enemigo.getFila() + ", "
                        + enemigo.getColumna() + ")");
                // Asigna una dirección por defecto si es necesario
                enemigo.setDireccion(Direccion.ABAJO);
            }
            ImageView enemigoView = new ImageView(imgEnemigoDown);
            enemigoView.setFitWidth(60);
            enemigoView.setFitHeight(60);
            mainGridPane.add(enemigoView, enemigo.getColumna(), enemigo.getFila());

        });
        // Actualiza las estadísticas del protagonista
        actualizarDatosJugador();
    }
    // Mensaje de perder la partida
    private void mostrarMensajeDerrota() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Game over");
        alert.setHeaderText(null);
        alert.setContentText("Has perdido la partida! Fin del juego.");
        alert.showAndWait();
    }

    // Mensaje de enhorabuena, ganaste la partida
    private void mostrarMensajeVictoria() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Enhorabuena ganaste la partida ");
        alert.showAndWait();
    }

    /**
     * Método del patrón Observer. Se llama automáticamente cuando el modelo cambia.
     * Actualiza la vista para reflejar los cambios.
     */
    @Override
    public void onChange() {
        actualizarVista();
    }
}