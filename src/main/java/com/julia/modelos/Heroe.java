package com.julia.modelos;
/**
 * Representa un heroe controlado por el jugador en el juego.
 * Hereda de {@link Personaje} y puede ganar experiencia y moverse según una dirección.
 */
public class Heroe extends Personaje {
    private Direccion direccion = Direccion.ABAJO;
    
    /**
     * Crea una nueva instancia de heroe con los atributos principales.
     * @param nombre  Nombre del heroe.
     * @param defensa Valor de defensa.
     * @param fuerza Valor de fuerza.
     * @param ataque (No se utiliza directamente, pero se pasa al constructor padre).
     * @param vida Puntos de vida iniciales.
     */
    public Heroe(String nombre, int defensa, int fuerza, int ataque, int puntosVida) {
        super(nombre, defensa, fuerza, ataque, puntosVida);
        this.nombre = nombre;
    }

    /**
     * Establece la posición del heroe en el mapa.
     * @param fila    Nueva fila.
     * @param columna Nueva columna.
     */
    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Establece la dirección en la que mira el heroe.
     * @param direccion Nueva dirección.
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la dirección actual en la que mira el heroe.
     * @return Dirección actual.
     */

    public Direccion getDireccion() {
        return direccion;
    }

   /**
     * Reduce los puntos de vida del heroe en la cantidad especificada.
     * Si los puntos de vida resultan negativos, se ajustan a cero.
     * @param cantidad Cantidad de puntos de vida a restar.
     */  
   public void disminuirPuntosVida(int cantidad){
        this.puntosVida -= cantidad;

        if(puntosVida < 0){
            puntosVida = 0;
        }
    }
    
    //Metodo para la maldicion y disminuya la vida al 25%
    /**public void disminuirMaldicion(){
        int vidaMaldicion = (super.getVidaMax()*25)/100;
        this.puntosVida-=vidaMaldicion;
         if(puntosVida < 0){
            puntosVida = 0;
        }
    }*/
 }