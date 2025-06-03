package com.julia.modelos;

/**
 * Representa una posición bidimensional en un tablero o mapa.
 */
public class Posicion {
    private int x;
    private int y;

    /**
     * Crea una nueva posición con coordenadas específicas.
     * 
     * @param x Coordenada horizontal.
     * @param y Coordenada vertical.
     */
    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Obtiene la coordenada X de la posición.
     * 
     * @return El valor de X.
     */
    public int getX() {
        return this.x;
    }

     /**
     * Establece la coordenada X de la posición.
     * 
     * @param x El nuevo valor de X.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Obtiene la coordenada Y de la posición.
     * 
     * @return El valor de Y.
     */
    public int getY() {
        return this.y;
    }

     /**
     * Establece la coordenada Y de la posición.
     * 
     * @param y El nuevo valor de Y.
     */
    public void setY(int y) {
        this.y = y;
    }

    //Mostrar datos
    
    /**
     * Devuelve una representación en texto de la posición.
     * 
     * @return Una cadena con los valores de X e Y.
     */
    @Override
    public String toString() {
        return "{" +
            " x='" + getX() + "'" +
            ", y='" + getY() + "'" +
            "}";
    }

}
