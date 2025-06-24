package com.julia.modelos;

public class Proveedor {
   /** Instancia única de la clase Proveedor (patrón Singleton). */
    private static Proveedor instance;
    /** Instancia compartida del heroe. */
    private Heroe heroe;
    /** Instancia compartida del gestor de enemigos. */
    private GestorEnemigos gestorEnemigos;
    /** Instancia compartida del motor del juego.  */
    private MotorJuego motorJuego;
 
    /**
     * Constructor privado para evitar la creación de múltiples instancias.
     * Inicializa el gestor de enemigos.
     */
    private Proveedor(){
        gestorEnemigos = new GestorEnemigos();
    }
    
    /**
     * Obtiene la instancia única de Proveedor.
     * Si no existe, la crea.
     * @return Instancia única de Proveedor.
     */
    public static Proveedor getInstance(){
        if (instance == null) {
            instance = new Proveedor();
        }
        return instance;
    }

    /**
     * Inicializa el heroe y el motor del juego en el proveedor.
     * @param heroe Instancia del heroe.
     * @param motorJuego Instancia del motor del juego.
     */
    public void inicializar(Heroe heroe, MotorJuego motorJuego) {
        this.heroe = heroe;
        this.motorJuego = motorJuego;
    }

    /**
    * Establece el heroe compartido en la aplicación.
    * Este método debe ser llamado antes de usar getHeroe().

    * @param heroe Instancia del heroe.
    */
    public void setHeroe(Heroe heroe){
        this.heroe = heroe;
    }

    /**
     * Obtiene el heroe compartido.
     * @return Instancia del heroe.
     * @throws IllegalStateException Si el heroe no ha sido inicializado.
     */
    public Heroe getHeroe() {
        if (heroe == null) {
            throw new IllegalStateException("El Heroe no ha sido inicializado.");
        }
        return heroe;
    }

    /**
     * Obtiene el motor del juego compartido.
     * @return Instancia del motor del juego.
     * @throws IllegalStateException Si el motor del juego no ha sido inicializado.
     */
    public MotorJuego getMotorJuego() {
        if (motorJuego == null) {
            throw new IllegalStateException("El MotorJuego no ha sido inicializado.");
        }
        return motorJuego;
    }

    /**
     * Obtiene el gestor de enemigos compartido.
     * @return Instancia del gestor de enemigos.
     */
    public GestorEnemigos getGestorEnemigos(){
        return gestorEnemigos;
    }

    /**
     *  Establece el motor del juego compartido en la aplicación.
     * @param motorJuego Instancia del motor del juego.
     */
    public void setMotorJuego(MotorJuego motorJuego) {
        this.motorJuego = motorJuego;
    }
}