package proyecto.modelos;

/**
 * Representa una celda en un tablero, la cual puede ser transitable o no,
 * contener un personaje, y tener un tipo específico.
 */

public class Celda {
    private boolean transitable;
    private TipoCelda tipoCelda;
    private Personaje personaje;

    /**
     * Crea una nueva celda con la transitabilidad y tipo especificado.
     * @param transitable Indica si la celda puede ser transitada.
     * @param tipoCelda El tipo de celda.
     */

    public Celda(boolean transitable, TipoCelda tipoCelda){
        this.transitable=transitable;
        this.tipoCelda=tipoCelda;
    }
     
    //Getters and setters

    /**
     * Verifica si la celda es transitable.
     * @return true si es transitable, false en caso contrario.
     */
    public boolean isTransitable(){
        return this.transitable;
    }

    /**
     * Método redundante para obtener si es transitable.
     * @return true si es transitable, false en caso contrario.
     */
    public boolean getTransitable(){
        return this.transitable;
    }

    /**
     * Establece si la celda es transitable.
     * @param transitable Valor booleano de transitabilidad.
     */
    public void setTransitable(boolean transitable){
        this.transitable = transitable;
    }
    
     /**
     * Obtiene el tipo de celda.
     * @return El tipo de la celda.
     */
    public TipoCelda getTipoCelda(){
        return tipoCelda;
    }

    /**
     * Establece el tipo de celda.
     * @param tipoCelda El nuevo tipo de celda.
     */
    public void setTipoCelda(TipoCelda tipoCelda){
        this.tipoCelda = tipoCelda;
    }

    
    /**
     * Obtiene el personaje ubicado en esta celda.
     * @return El personaje, o null si no hay ninguno.
     */
    public Personaje getPersonaje(){
        return personaje;
    }

     /**
     * Asigna un personaje a esta celda.
     * @param personaje El personaje a ubicar en la celda.
     */
    public void setPersonaje(Personaje personaje){
        this.personaje = personaje;
    }

    /**
     * Verifica si la celda está ocupada por un personaje.
     * @return true si hay un personaje, false si está vacía.
     */
    public boolean estaOcupada(){
        return personaje != null;
    }

    //Mostrar
    /**
     * Devuelve una representación en texto de la celda.
     * @return String representando el estado de la celda.
     */
    @Override
    public String toString() {
        return "{" +
            " transitable='" + isTransitable() + "'" +
            "}";
    }

   

}
