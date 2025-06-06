package com.julia.modelos;

/**
 * Enum que define los tipos posibles de celdas en el mapa del juego.
 * Las celdas son suelo y pared.
 */
public enum TipoCelda {
    SUELO(true),
    MURO(false);

    private final boolean transitable;

    TipoCelda(boolean transitable) {
        this.transitable = transitable;
    }

    public boolean isTransitable() {
        return transitable;
    }
}
