package proyecto.modelos;

import java.util.ArrayList;
import java.util.List;

import interfaces.Observador;


public class GestorPersonajes{
    private List<Personaje> personajes;
    private List<Observador> observers;

    public GestorPersonajes(){
        personajes = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public void subscribe(Observador observer){
        if (!observers.contains(observer)){
            observers.add(observer);
        }
    }
   
    public void unsubscribe(Observador observer){
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observador observer : observers){
            observer.actualizar();
        }
    }

    //Añadir personaje
    public void insertarPersonaje(Personaje p){
        personajes.add(p);
        notifyObservers();
    }

    //Eliminar personaje
    public void eliminarPersonaje(Personaje p){
        personajes.remove(p);
        notifyObservers();
    }

    //Obtener lista de personajes
    public List<Personaje> getPersonajes(){
        return personajes;
    }

    //Buscar personaje por nombre
    public Personaje buscarPorNombre(String nombre){
        for (Personaje p : personajes) {
            if (p.getNombre() != null && p.getNombre().equalsIgnoreCase(nombre)){
                return p;
            }
        }
        return null;
    }

   //Realizar los turnos, se ordenara el personaje por velocidad descendente
    public void realizarTurnos(Mapa mapa){
        personajes.sort((p1, p2) -> Integer.compare(p2.getVelocidad(), p1.getVelocidad()));

        for (Personaje p : personajes){
            p.realizarTurno(mapa);
            notifyObservers();
        }
    }

    //Mostrar
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Personaje p : personajes) {
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }

}
