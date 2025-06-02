package interfaces;

public interface Observable{
    void agregarObservador(Observador o);
    void eliminarObservador(Observador o);
    void notificarObservadores();
}