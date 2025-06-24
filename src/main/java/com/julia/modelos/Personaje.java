package com.julia.modelos;

/**
 * Clase abstracta que representa un personaje en el juego.
 * Define atributos y comportamientos comunes para héroes y enemigos.
 * Implementa el patrón de observador para notificar cambios en el estado del personaje.
 */
 abstract class Personaje {
    /**Nombre del personaje.  */
    protected String nombre;
    /**Puntos de vida actuales del personaje. */
    protected int puntosVida;
    /**Valor de defensa del personaje, reduce el daño recibido. */
    protected int defensa;
    /**Valor de fuerza, determina el daño que puede infligir. */
    protected int fuerza;
    /**Fila actual en el mapa. */
    protected int fila ;
    /** Columna actual en el mapa. */
    protected int columna;

    protected int vidaMax; //vida maxima

    /**
     * Constructor para inicializar los atributos principales del personaje.
     * @param nombre Nombre del personaje.
     * @param defensa Valor de defensa.
     * @param fuerza Valor de fuerza.
     * @param fila Fila inicial en el mapa.
     * @param vida Puntos de vida iniciales.
     */
    Personaje(String nombre, int defensa, int fuerza, int fila, int vida){
        this.nombre = nombre;
        this.defensa = defensa;
        this.fuerza = fuerza;
        this.fila = fila;
        this.puntosVida = vida;
        this.vidaMax=vida;
    }

    /**
     * 
     * @return El nombre del personaje.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @param nombre Nuevo nombre del personaje.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * 
     * @return La fila actual del personaje en el mapa.
     */
    public int getFila() {
        return this.fila;
    }

    /**
     * 
     * @param fila Nueva fila en el mapa.
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    /**
     * 
     * @return La columna actual del personaje en el mapa. 
     */
    public int getColumna() {
        return this.columna;
    }

    /**
     * 
     * @param columna Nueva columna en el mapa. 
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

    /**
     * 
     * @return Los puntos de vida actuales del personaje.
     */
    public int getPuntosVida() {
        return puntosVida;
    }

    /**
     * 
     * @param puntosVida Nuevos puntos de vida. 
     */
    public void setPuntosVida(int puntosVida) {
        this.puntosVida = puntosVida;
    }

    /**
     * 
     * @return Valor de defensa del personaje.
     */
    public int getDefensa() {
        return defensa;
    }

    /**
     * 
     * @param defensa Nuevo valor de defensa.
     */
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    /**
     * 
     * @return Valor de fuerza del personaje.
     */
    public int getFuerza() {
        return fuerza;
    }

    /**
     * 
     * @param fuerza Nuevo valor de fuerza. 
     */
    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getVidaMax(){
        return vidaMax;
    }

    /**
     * Calcula y actualiza los puntos de vida del personaje tras recibir un ataque.
     * 
     * @param danio es un parametro de entrada que se resta, de la defensa para obtener el daño real
     * es lo que une entre puntos de vida y ataque (fuerza):Con el ataque se incrementa el numero de daños(guardado en daño real) 
     * y asu vez se decrementa estos puntos a puntosVida
     * si tenemos 15 de puntosVida , y 5 de defensa , y me atacan con 10 de fuerza , yo(protagonista) defenderia 5, pero los otros
     * 5 se incrementan en puntosDanio q asu vez se restan de sus puntosVida, entonces nos quedaria 10 de puntosVida.
     */

    public void CalcularPuntosVida(int danio) {
        //int danioReal = Math.max(danio - defensa, 0); // asegura que el daño no sea negativo
        int danioReal = (danio - defensa);
        puntosVida -= danioReal; // Resta el daño real de los puntos de vida
        
        if (puntosVida < 0) {
            puntosVida = 0; // Asegura que los puntos de vida no sean negativos
        }
    }

    /**
     * Indica si el personaje sigue vivo (puntos de vida mayores a cero).
     * @return true si el personaje está vivo, false si ha sido derrotado.
     */
    public boolean estaVivo(){ 
         return puntosVida > 0;
    }

    /**
     * Calcula el daño base que puede infligir el personaje.
     * Por defecto, es igual al atributo fuerza.
     * 
     * @return retorna fuerza porq es la forma en la q se calcula el daño , el cual esta relacionado con los puntos de vida
     */
     public int calcularDanio(){
        return fuerza; 
    }

    /**
     * Establece la posición del personaje en el mapa.
     * @param fila Nueva fila.
     * @param columna Nueva columna.
     */
    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Devuelve una representación en texto del personaje y sus atributos principales.
     * @return String descriptivo del personaje.
     */
    @Override
    public String toString() {
        return "Personaje [nombre=" + nombre + ", puntosVida=" + puntosVida + ", defensa=" + defensa + ", fuerza="
                + fuerza + ", ]";
    }
 }



