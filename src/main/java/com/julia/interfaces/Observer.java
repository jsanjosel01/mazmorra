package com.julia.interfaces;
/**
 * Interfaz para implementar el patrón Observer.
 * Permite que las clases que la implementan sean notificadas cuando ocurre un cambio en el modelo observado.
 */
public interface Observer {
    /**
     * Método llamado cuando el objeto observado cambia de estado.
     * Debe ser implementado por las clases observadoras para actualizar su estado o vista.
     */
    public void onChange();
}
