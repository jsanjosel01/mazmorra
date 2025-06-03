package com.julia.modelos;

/**
 * Representa un enemigo en el juego, que hereda de la clase {@link Personaje}.
 * Cada enemigo tiene un tipo y un nivel de percepción que determina su capacidad
 * para detectar y moverse hacia el héroe.
 */
public class Enemigo extends Personaje {
    private int velocidad;
    private int percepcion;
    private Direccion direccion;

    /**
     * Constructor de Enemigo.
     * 
     * @param nombre Nombre del enemigo.
     * @param posicion Posición inicial en el mapa.
     * @param vidaMaxima Vida máxima del enemigo.
     * @param fuerza Fuerza de ataque.
     * @param defensa Defensa.
     * @param velocidad Velocidad de movimiento.
     * @param percepcion Rango de percepción para detectar héroes u otros personajes.
     */
    public Enemigo(String nombre, Posicion posicion, int vidaMaxima, int fuerza, int defensa, int velocidad, int percepcion) {
        super(nombre, posicion);
        this.setVidaMaxima(vidaMaxima);
        this.setVidaActual(vidaMaxima);
        this.setFuerza(fuerza);
        this.setDefensa(defensa);
        this.velocidad = velocidad;
        this.percepcion = percepcion;
        this.direccion = Direccion.ABAJO; 
    }

    //Getters y setters
    public int getVelocidad(){
        return velocidad;
    }

    public void setVelocidad(int velocidad){
        this.velocidad = velocidad;
    }

    public int getPercepcion(){
        return percepcion;
    }

    public void setPercepcion(int percepcion){
        this.percepcion = percepcion;
    }

    public Direccion getDireccion(){
        return direccion;
    }

    public void setDireccion(Direccion direccion){
        this.direccion = direccion;
    }

    //Mostrar
    @Override
    public String toString(){
        return "Enemigo{" +
               "nombre='" + getNombre() + '\'' +
               ", posicion=" + getPosicion() +
               ", vidaActual=" + getVidaActual() +
               ", vidaMaxima=" + getVidaMaxima() +
               ", fuerza=" + getFuerza() +
               ", defensa=" + getDefensa() +
               ", velocidad=" + velocidad +
               ", percepcion=" + percepcion +
               ", direccion=" + direccion +
               '}';
    }

    //Hacer el turno del enemigo
    @Override
    public void realizarTurno(Mapa mapa){

    }
}



