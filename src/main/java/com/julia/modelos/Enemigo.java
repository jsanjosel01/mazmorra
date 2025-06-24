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
 * Crea una instancia de Enemigo con parámetros específicos.
 * @param tipo Tipo/clasificación del enemigo
 * @param x Posición inicial en el eje X (columna)
 * @param y Posición inicial en el eje Y (fila)
 * @param vida Puntos de vida iniciales
 * @param fuerza Capacidad de daño en ataques
 * @param defensa Reducción de daño recibido
 * @param velocidad Casillas que puede moverse por turno
 * @param percepcion Rango de detección del jugador (en casillas)
 */
    public Enemigo(String tipo, int x, int y, int vida, int fuerza, int defensa, int velocidad, int percepcion) {
        super(tipo, defensa, fuerza, 0, vida);
        this.velocidad = velocidad;
        this.percepcion = percepcion;
        this.fila = y;
        this.columna = x;
        this.direccion = Direccion.ABAJO; 

    }

    /**
     * 
     * @return Velocidad de movimiento en casillas por turno
     */
        public int getVelocidad() {
            return this.velocidad;
        }

    /**
     * 
     * @param velocidad Nueva velocidad de movimiento (debe ser ≥0)
     */
        public void setVelocidad(int velocidad) {
            this.velocidad = velocidad;
        }

    /**
     * 
     * @return Rango de percepción para detectar objetivos (en casillas)
     */
        public int getPercepcion() {
            return percepcion;
        }

    /**
     * 
     * @param percepcion Nuevo rango de percepción (debe ser ≥0)
     */
        public void setPercepcion(int percepcion) {
            this.percepcion = percepcion;
        }

    /**
     * 
     * @param direccion Nueva dirección a la que mira el enemigo
     */
        public void setDireccion(Direccion direccion) {
            this.direccion = direccion;
        }

    /**
     * 
     * @return Dirección actual a la que mira el enemigo
     */
        public Direccion getDireccion() {
            return direccion;
        }

    /**
     * @return Representación en String del enemigo con sus atributos clave
     * @implSpec Formato: 
     * "Enemigo [nombre=[tipo], vida=[puntosVida], pos=([fila],[columna]), fuerza=[fuerza], 
     * defensa=[defensa], velocidad=[velocidad], percepcion=[percepcion]]"
     */
        @Override
        public String toString() {
            return "Enemigo [nombre=" + nombre + ", vida=" + puntosVida + ", pos=(" + fila + "," + columna + "), fuerza=" + fuerza +
                    ", defensa=" + defensa + ", velocidad=" + velocidad + ", percepcion=" + percepcion + "]";
        }

        public int getVida() {
            return this.puntosVida;
        }
        
}