package com.julia.controllador;

import com.julia.App;
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

    @FXML
    private VBox vboxEstadisticas;

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

    //private Image imgEnemigoup;
    private Image imgEnemigoDown;
    //private Image imgEnemigoLeft;
    //private Image imgEnemigoRight;

    @FXML
    public void initialize() {
        
        Proveedor p = Proveedor.getInstance();
        mapa = Proveedor.getInstance().getMapa();
        heroe = Proveedor.getInstance().getHeroe();

        if (mapa == null || heroe == null) {
            System.err.println("No están disponibles...");
        }

        cargarImagenes();

        mapa.agregarObservador(this);
        //heroe.agregarObservador(this);

        configurarGrid();
        configurarPanelEstadisticas();

        // Controlar el teclado
        tablero.setOnKeyPressed(this::manejarMovimiento);
        tablero.setFocusTraversable(true);
        tablero.requestFocus();

       
        pintarEscenario();
        pintarPersonaje(); 
       
        //actualizarVista();

    }

    public void pintarEscenario() {
        mapa = Proveedor.getInstance().getMapa();
        //Nos vamos a la clase proveedor,
        // acceder a la matriz, y recorrerla.

        for (int i = 0; i < mapa.getAlto(); i++) {
            for (int j = 0; j < mapa.getAncho(); j++) {
                if (mapa.getCelda(i, j).getTipoCelda() == TipoCelda.SUELO) {
                    ImageView imagenViewSuelo = new ImageView(imgSuelo);
                    grid.add(imagenViewSuelo, j, i);

                } else {
                    ImageView imageViewMuro = new ImageView(imgMuro);
                    grid.add(imageViewMuro, j, i);
                }
                //En vez de ser If/else. Poner en la linea 87 un else if
                //poner otro else if.
                //otro else if.
            }
        }
    }

    //metodo pintar los enemigos
    public void pintarPersonaje() {
    GestorPersonajes gestor = Proveedor.getInstance().getGestorPersonajes();

    for (Personaje p : gestor.getPersonajes()) {
        if (!(p instanceof Heroe)) {
            Posicion pos = p.getPosicion();
            ImageView imgEnemigoView = new ImageView(imgEnemigoDown); 
            imgEnemigoView.setFitWidth(40);
            imgEnemigoView.setFitHeight(40);
            grid.add(imgEnemigoView, pos.getX(), pos.getY());
        }
    }
}
    
    // ruta de las imagenes
    private void cargarImagenes() {
        try {
            imgSuelo = new Image(App.class.getResourceAsStream("imagenes/imagenes/Escenario.png"),40,40,false,false);
            imgMuro = new Image(App.class.getResourceAsStream("imagenes/imagenes/muro.jpg"),40,40,false,false);
            imgHeroeUp = new Image(App.class.getResourceAsStream("imagenes/imagenes/prota_espalda.png"));
            imgHeroeDown = new Image(App.class.getResourceAsStream("imagenes/imagenes/prota_delante.png"));
            imgHeroeLeft = new Image(App.class.getResourceAsStream("imagenes/imagenes/prota_izquierda.png"));
            imgHeroeRight = new Image(App.class.getResourceAsStream("imagenes/imagenes/prota_derecha.png"));

            
            //imgEnemigoup = new Image(App.class.getResourceAsStream("imagenes/imagenes/g1Espalda.png"));
            imgEnemigoDown = new Image(App.class.getResourceAsStream("imagenes/imagenes/goblin_delante.png"));
            //imgEnemigoLeft = new Image(App.class.getResourceAsStream("imagenes/imagenes/g2izq.png"));
            //imgEnemigoRight = new Image(App.class.getResourceAsStream("imagenes/imagenes/g4derech.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    // Configurar el grid
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

    // Estadisticas
    private void configurarPanelEstadisticas() {
        datosHeroe = new VBox(10);
        datosHeroe.setLayoutX(10);
        datosHeroe.setLayoutY(10);
        estadisticas.getChildren().add(datosHeroe);

    }

    // Movimientos
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

    // Actualizar la vista
    private void actualizarVista() {
       grid.getChildren().clear();

    // Pintar suelo y muros
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
    pintarHeroe();
    pintarPersonaje();
    actualizarDatosHeroe();
}

    private void pintarHeroe() {
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
    }

    // Actualizar los datos del heroe
    private void actualizarDatosHeroe() {
        datosHeroe.getChildren().clear();
        datosHeroe.getChildren().addAll(
                new Label("Nombre: " + heroe.getNombre()),
                new Label("Vida: " + heroe.getVidaActual() + "/" + heroe.getVidaMaxima()),
                new Label("Ataque: " + heroe.getAtaque()),
                new Label("Defensa: " + heroe.getDefensa()),
                new Label("Fuerza: " + heroe.getFuerza()),
                new Label("Posición: (" + heroe.getPosicion().getX() + ", " + heroe.getPosicion().getY() + ")"));
    }

    // Estadisticas
    private void agregarEstadistica(String nombre, String valor) {
        Label lbl = new Label(nombre + ": " + valor);
        vboxEstadisticas.getChildren().add(lbl);
    }

    @Override
    public void actualizar() {
        actualizarVista();
    }

    
}