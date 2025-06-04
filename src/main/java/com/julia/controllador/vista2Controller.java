package com.julia.controllador;

import com.julia.interfaces.Observador;
import com.julia.modelos.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

public class vista2Controller implements Observador {

    @FXML
    private AnchorPane tablero;

    @FXML
    private AnchorPane estadisticas;

    private GridPane grid;
    private VBox datosHeroe;

    private Mapa mapa;
    private Heroe heroe;

    private Image imgSuelo;
    private Image imgMuro;
    private Image imgHeroeUp;
    private Image imgHeroeDown;
    private Image imgHeroeLeft;
    private Image imgHeroeRight;

    @FXML
    public void initialize() {
        mapa = Proveedor.getInstance().getMapa();
        heroe = Proveedor.getInstance().getHeroe();
        

        if (mapa == null || heroe == null || grid == null) {
            System.err.println("No están disponibles...");
            return;
        }

        cargarImagenes();

        mapa.agregarObservador(this);
        heroe.agregarObservador(this);

        configurarGrid();
        configurarPanelEstadisticas();

        // Controlar el teclado
        tablero.setOnKeyPressed(this::manejarMovimiento);
        tablero.setFocusTraversable(true);
        tablero.requestFocus();

        actualizarVista();
    }

    //ruta de las imagenes
    private void cargarImagenes() {
        imgSuelo = new Image(getClass().getResourceAsStream("/imagenes/Escenario.png"));
        imgMuro = new Image(getClass().getResourceAsStream("/imagenes/muro.jpg"));
        imgHeroeUp = new Image(getClass().getResourceAsStream("/imagenes/h1Espalda.png"));
        imgHeroeDown = new Image(getClass().getResourceAsStream("/imagenes/h1Delante.png"));
        imgHeroeLeft = new Image(getClass().getResourceAsStream("/imagenes/h1izq.png"));
        imgHeroeRight = new Image(getClass().getResourceAsStream("/imagenes/h1derech.png"));
    }

    //Configurar el grid
    private void configurarGrid() {
        grid = new GridPane();
        for (int x = 0; x < mapa.getAncho(); x++) {
            grid.getColumnConstraints().add(new ColumnConstraints(40));
        }
        for (int y = 0; y < mapa.getAlto(); y++) {
            grid.getRowConstraints().add(new RowConstraints(40));
        }
        tablero.getChildren().add(grid);
    }

    //Estadisticas
    private void configurarPanelEstadisticas() {
        datosHeroe = new VBox(10);
        datosHeroe.setLayoutX(10);
        datosHeroe.setLayoutY(10);
        estadisticas.getChildren().add(datosHeroe);
    }

    //Movimientos
    private void manejarMovimiento(KeyEvent event) {
    Direccion dir = null;
    switch (event.getCode()) {
        case UP:
            dir = Direccion.ARRIBA;
            break;
        case DOWN:
            dir = Direccion.ABAJO;
            break;
        case LEFT:
            dir = Direccion.IZQUIERDA;
            break;
        case RIGHT:
            dir = Direccion.DERECHA;
            break;
        default:
            break;
        }

            if (dir != null) {
              heroe.setDireccion(dir);
             heroe.realizarTurno(mapa);
        }
    }

    private void actualizarVista() {
        grid.getChildren().clear();

        for (int y = 0; y < mapa.getAlto(); y++) {
            for (int x = 0; x < mapa.getAncho(); x++) {
                Celda celda = mapa.getCelda(x, y);
                ImageView celdaView = new ImageView(
                        celda.getTipoCelda() == TipoCelda.MURO ? imgMuro : imgSuelo);
                celdaView.setFitWidth(40);
                celdaView.setFitHeight(40);
                grid.add(celdaView, x, y);
            }
        }

        Posicion pos = heroe.getPosicion();
        Image imgHeroe;
            switch (heroe.getDireccion()) {
                case ARRIBA:
                    imgHeroe = imgHeroeUp;
                    break;
                case ABAJO:
                    imgHeroe = imgHeroeDown;
                    break;
                case IZQUIERDA:
                    imgHeroe = imgHeroeLeft;
                    break;
                case DERECHA:
                    imgHeroe = imgHeroeRight;
                    break;
                default:
                    imgHeroe = imgHeroeDown;
                    break;
            }
        ImageView ivHeroe = new ImageView(imgHeroe);
        ivHeroe.setFitWidth(40);
        ivHeroe.setFitHeight(40);
        grid.add(ivHeroe, pos.getX(), pos.getY());

        actualizarDatosHeroe();
    }


    //Actualizar los datos del heroe
    private void actualizarDatosHeroe() {
        datosHeroe.getChildren().clear();
        datosHeroe.getChildren().addAll(
                new Label("Nombre: " + heroe.getNombre()),
                new Label("Vida: " + heroe.getVidaActual() + "/" + heroe.getVidaMaxima()),
                new Label("Ataque: " + heroe.getAtaque()),
                new Label("Defensa: " + heroe.getDefensa()),
                new Label("Fuerza: " + heroe.getFuerza()),
                new Label("Posición: (" + heroe.getPosicion().getX() + ", " + heroe.getPosicion().getY() + ")")
        );
    }


    @Override
    public void actualizar() {
        actualizarVista();
    }
}