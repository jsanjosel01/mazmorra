package com.julia.modelos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.julia.interfaces.Observador;
import com.julia.interfaces.Observable;

/**
 * Representa el mapa del juego, el cual contiene celdas, personajes y lógica de movimiento.
 * Implementa la interfaz {@link Observable} para notificar cambios a los observadores.
 */
public class Mapa implements Observable {
   private Celda[][] celdas;
    private int ancho;
    private int alto;
    private Map<Posicion, Personaje> posicionesPersonajes;
    private Heroe heroe;
    private List<Observador> observadores;

    public Mapa(int ancho, int alto){
        this.ancho = ancho;
        this.alto = alto;
        this.celdas = new Celda[alto][ancho];
        this.posicionesPersonajes = new HashMap<>();
        this.observadores = new ArrayList<>();

        for (int y = 0; y < alto; y++){
            for (int x = 0; x < ancho; x++){
                celdas[y][x] = new Celda(true, TipoCelda.SUELO);
            }
        }
    }

    // Agrega un personaje al mapa (heroe o enemigo)
    public void agregarPersonaje(Personaje personaje){
        if (personaje instanceof Heroe) {
            heroe = (Heroe) personaje;
        }
        if (esPosicionValida(personaje.getPosicion())){
            posicionesPersonajes.put(personaje.getPosicion(), personaje);
            Celda celda = getCelda(personaje.getPosicion().getX(), personaje.getPosicion().getY());
            if (celda != null) celda.setPersonaje(personaje);
            notificarObservadores();
        }
    }

    // Valida que la posición esté dentro del mapa
    public boolean esPosicionValida(Posicion posicion){
        int x = posicion.getX();
        int y = posicion.getY();
        return x >= 0 && x < ancho && y >= 0 && y < alto;
    }

    //Devuelve la celda
    public Celda getCelda(int x, int y){
        if (esPosicionValida(new Posicion(x, y))){
            return celdas[y][x];
        }
        return null;
    }

    //Mueve un personaje a una posición válida, si la celda es transitable y no está ocupada
    public boolean moverPersonaje(Personaje personaje, Posicion nuevaPosicion){
        if (!esPosicionValida(nuevaPosicion)) return false;

        Celda celdaDestino = getCelda(nuevaPosicion.getX(), nuevaPosicion.getY());
        if (celdaDestino == null || !celdaDestino.isTransitable() || celdaDestino.estaOcupada()){
            return false;
        }

        Posicion posicionActual = personaje.getPosicion();
        Celda celdaActual = getCelda(posicionActual.getX(), posicionActual.getY());

        //Actualiza celdas
        if (celdaActual != null) celdaActual.setPersonaje(null);
        celdaDestino.setPersonaje(personaje);

        //Actualiza mapa de posiciones
        posicionesPersonajes.remove(posicionActual);
        posicionesPersonajes.put(nuevaPosicion, personaje);

        //Actualiza posición personaje
        personaje.setPosicion(nuevaPosicion);

        notificarObservadores();
        return true;
    }

    public Heroe getHeroe(){
        return heroe;
    }

    public Map<Posicion, Personaje> getPosicionesPersonajes(){
        return posicionesPersonajes;
    }

    //Métodos observables
    @Override
    public void agregarObservador(Observador o){
        observadores.add(o);
    }

    @Override
    public void eliminarObservador(Observador o){
        observadores.remove(o);
    }

    @Override
    public void notificarObservadores(){
        for (Observador o : observadores){
            o.actualizar();
        }
    }

    //Getters 
    public int getAncho(){
        return ancho;
    }

    public int getAlto(){
        return alto;
    }
}

    

