package com.julia.modelos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.julia.interfaces.Observer;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class MotorJuego { //CAMBIOS EN EL JUEGO
    /**
     * Instancia del héroe principal del juego.
     */
    private Heroe heroe;
    /**
     * Gestor que administra la lista y comportamiento de los enemigos en el juego.
     */
    private GestorEnemigos gestorEnemigos;
    /**
     * Objeto encargado de la lectura y carga del mapa del juego.
     */
    private LectoraMapa mapa;
    /**
     * Lista de observadores que serán notificados ante cambios en el modelo (patrón
     * Observer).
     */
    private List<Observer> observadores = new ArrayList<>();

    /**
     * Crea un nuevo motor de juego, carga el mapa y posiciona al heroe.
     * 
     * @param rutaMapa Ruta al archivo del mapa.
     * @param heroe    Instancia del heroe.
     * @throws RuntimeException Si ocurre un error al cargar el mapa.
     */
    public MotorJuego(String rutaMapa, Heroe heroe) {
        this.heroe = heroe;
        try {
            this.mapa = new LectoraMapa(rutaMapa);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar el mapa: " + e.getMessage());
        }

        int[] posInicial = encontrarPosicionInicial();
        this.heroe.setPosicion(posInicial[0], posInicial[1]);

        this.gestorEnemigos = new GestorEnemigos();
        notifyObservers();
    }

    /**
     * Busca la primera celda de tipo SUELO en el mapa para ubicar al heroe.
     * 
     * @return Un array con la fila y columna de la posición inicial.
     * @throws IllegalStateException Si no se encuentra una celda SUELO.
     */
    public int[] encontrarPosicionInicial() {
        for (int fila = 0; fila < mapa.getAlto(); fila++) {
            for (int columna = 0; columna < mapa.getAncho(); columna++) {
                if (mapa.getCelda(fila, columna).getTipo() == TipoCelda.SUELO) {
                    return new int[] { fila, columna };
                }
            }
        }
        throw new IllegalStateException("No se encontró una celda para ubicar al heroe.");
    }

    /**
     * Intenta mover al heroe a la posición indicada.
     * Si hay un enemigo en la nueva posición, inicia un combate.
     * Si la posición es válida y libre, mueve al heroe y a los enemigos.
     * 
     * @param nuevaFila    nuevaFila Nueva fila de destino.
     * @param nuevaColumna nuevaColumna Nueva columna de destino.
     */
    public void moverHeroe(int nuevaFila, int nuevaColumna) {
        if (esPosicionValida(nuevaFila, nuevaColumna)) {
            // System.out.println("Nueva posición del héroe: (" + nuevaFila + ", " +
            // nuevaColumna + ")");
            int filaActual = heroe.getFila();
            int columnaActual = heroe.getColumna();

            if (nuevaFila < filaActual) {
                heroe.setDireccion(Direccion.ARRIBA);
            } else if (nuevaFila > filaActual) {
                heroe.setDireccion(Direccion.ABAJO);
            } else if (nuevaColumna < columnaActual) {
                heroe.setDireccion(Direccion.IZQUIERDA);
            } else if (nuevaColumna > columnaActual) {
                heroe.setDireccion(Direccion.DERECHA);
            }

            if (hayEnemigoEnPosicion(nuevaFila, nuevaColumna)) {
                iniciarCombate(nuevaFila, nuevaColumna);

            } else {
                heroe.setPosicion(nuevaFila, nuevaColumna);
                Celda celdaTrampa = mapa.getCelda(nuevaFila, nuevaColumna);
                //if (celdaTrampa.getTipo() == TipoCelda.MALDICION) {
                    //heroe.disminuirMaldicion();
                    // celdaTrampa.setTipo(TipoCelda.TRAMPA);
                    // System.out.println("Recibir danio");
                    if (heroe.getPuntosVida() <= 0) { //CAMBIAR EL 0 POR UNA CANTIDAD SÍ RECIBE DAÑO
                        finalizarJuego();
                    }
                }
                gestorEnemigos.moverEnemigos(heroe, mapa);
                notifyObservers();
            }
        }
//    }

    /**
     * Verifica si hay un enemigo en la posición indicada.
     * 
     * @param fila    Fila a comprobar.
     * @param columna Columna a comprobar.
     * @return true si hay un enemigo en esa posición, false en caso contrario.
     */
    public boolean hayEnemigoEnPosicion(int fila, int columna) {

        for (Enemigo enemigo : gestorEnemigos.getEnemigos()) {
            if (enemigo.getFila() == fila && enemigo.getColumna() == columna) {
                return true;
            }
        }
        return false;
    }

    /**
     * Inicia un combate entre el heroe y el enemigo en la posición dada.
     * Calcula el daño, actualiza los puntos de vida y elimina al enemigo si es
     * derrotado.
     * Finaliza el juego si el heroe muere.
     * 
     * @param fila    Fila donde ocurre el combate.
     * @param columna Columna donde ocurre el combate.
     */

    public void iniciarCombate(int fila, int columna) {

        for (Enemigo enemigo : new ArrayList<>(gestorEnemigos.getEnemigos())) {
            if (enemigo.getFila() == fila && enemigo.getColumna() == columna) {
                System.out.println("¡Combate entre " + heroe.getNombre() + " y " + enemigo.getNombre());

                // Calcular el danio
                heroe.CalcularPuntosVida(enemigo.calcularDanio());
                enemigo.CalcularPuntosVida(heroe.calcularDanio());

                // Mostrar resultados
                System.out.println(heroe.getNombre() + " tiene " + heroe.getPuntosVida() + "de vida");
                System.out.println(enemigo.getNombre() + "tiene" + enemigo.getPuntosVida() + "de vida ");

                // se verificara sí el enemigo ha perdido
                if (!enemigo.estaVivo()) {
                    System.out.println(enemigo.getNombre() + " ha sido derrotado ");
                    gestorEnemigos.eliminarEnemigo(enemigo);

                }
                // se verificara sí el heroe ha perdido
                if (!heroe.estaVivo()) {
                    System.out.println("El héroe ha muerto. Fin del juego");
                    finalizarJuego();
                }
                notifyObservers();
            }
        }
    }

    /**
     * Finaliza el juego mostrando una alerta y cerrando la aplicación.
     */
    public void finalizarJuego() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Fin de la partida ¡Perdiste!");
        alert.showAndWait();
        Platform.exit();
    }

    /**
     * Verifica si la posición indicada es válida para el heroe (dentro de los
     * límites y no es muro).
     * 
     * @param fila    Fila a comprobar.
     * @param columna Columna a comprobar.
     * @return true si la posición es válida, false en caso contrario.
     */

    private boolean esPosicionValida(int fila, int columna) {

        if (fila < 0 || fila >= mapa.getAlto() || columna < 0 || columna >= mapa.getAncho()) {
            System.out.println("Posición fuera de los límites: (" + fila + ", " + columna + ")");
            return false; // Fuera de los límites
        }
        Celda celda = mapa.getCelda(fila, columna);
        if (celda.getTipo() == TipoCelda.MURO) {
            System.out.println("Celda actual: " + celda.getTipo());
            return false; // Si la celda es una muro
        }
        return true;

    }

    /**
     * Devuelve el lector de escenario (mapa) actual.
     * 
     * @return LectorEscenario utilizado en el juego.
     */
    public LectoraMapa getMapa() {
        return mapa;
    }

    /**
     * Devuelve el gestor de enemigos actual.
     * 
     * @return GestorEnemigo utilizado en el juego.
     */
    public GestorEnemigos getGestorEnemigo() {
        return gestorEnemigos;
    }

    /**
     * Añade un observador para recibir notificaciones de cambios en el juego.
     * 
     * @param o Observador a añadir.
     */
    public void addObserver(Observer o) {
        observadores.add(o);
    }

    /**
     * Notifica a todos los observadores registrados sobre un cambio en el estado
     * del juego.
     */
    private void notifyObservers() {
        for (Observer o : observadores) {
            o.onChange();
        }
    }
}
