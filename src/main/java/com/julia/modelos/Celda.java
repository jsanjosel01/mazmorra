package com.julia.modelos;

/**
 * Representa una celda en un tablero, la cual puede ser transitable o no,
 * contener un personaje, y tener un tipo específico.
 */

public class Celda {
     private TipoCelda tipo;
    private boolean ocupado;

/**
 * Crea una nueva celda con el tipo especificado y la marca como no ocupada.
 * @param tipo El tipo de la celda.
 */
    public Celda(TipoCelda tipo){
        this.tipo = tipo;
        this.ocupado = false;
    }

/**
 * Obtiene el tipo de la celda.
 * @return El tipo de la celda.
 */
    public TipoCelda getTipo() {
        return this.tipo;
    }


/**
 * Establece el tipo de la celda.
 * @param tipo El nuevo tipo de la celda.
 */
    public void setTipo(TipoCelda tipo) {
        this.tipo = tipo;
    }

/**
 *  Indica si la celda está ocupada.
 * @return true si la celda está ocupada, false en caso contrario.
 */
    public boolean isOcupado() {
        return this.ocupado;
    }


    public boolean getOcupado() {
        return this.ocupado;
    }

/**
 * Establece el estado de ocupación de la celda.
 * @param ocupado true para marcar la celda como ocupada, false para marcarla como libre.
 */
    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

/**
 * Indica si la celda es transitable, es decir, si es de tipo SUELO y no está ocupada.
 * @return true si la celda es transitable, false en caso contrario.
 */
    public boolean esTransitable(){
        return tipo == TipoCelda.SUELO && !ocupado;
    }
    
}