package proyecto.modelos;

import java.util.List;
import java.util.Objects;

import interfaces.Observador;
import interfaces.Observable;

/**
 * Clase abstracta que representa un personaje en el juego.
 * Define atributos y comportamientos comunes para héroes y enemigos.
 * Implementa el patrón de observador para notificar cambios en el estado del personaje.
 */
public abstract class Personaje implements Observable {

    private String nombre;
    private int id;
    private static int identificador =0;
    private int vidaMaxima;
    private int vidaActual;
    private int velocidad;
    private int ataque;
    private int defensa;
    private int nivel;
    private Posicion posicion;
    private int fuerza; 
    private List<Observador> observadores;

    
     /**
     * Constructor que inicializa un personaje con nombre y posición.
     *
     * @param nombre Nombre del personaje.
     * @param posicion Posición inicial del personaje en el mapa.
     */
    public Personaje(String nombre, Posicion posicion){
        this.nombre=nombre;
        this.posicion=posicion;
        this.id = identificador++;
        this.vidaMaxima=100;
        this.vidaActual=this.vidaMaxima;
        this.velocidad=10;
        this.ataque=10;
        this.nivel=1;
        this.fuerza=10; 
        this.nivel=1;
    }

    // Métodos getters y setters

   /**
     * Obtiene el nombre del personaje.
     *
     * @return El nombre del personaje.
     */
    public String getNombre() {
        return this.nombre;
    }

     /**
     * Establece el nombre del personaje.
     *
     * @param nombre Nuevo nombre del personaje.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

     /**
     * Obtiene el identificador único del personaje.
     *
     * @return ID del personaje.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Obtiene la vida máxima del personaje.
     *
     * @return Vida máxima.
     */
    public int getVidaMaxima() {
        return this.vidaMaxima;
    }

    /**
     * Establece la vida máxima del personaje.
     *
     * @param vidaMaxima Nueva vida máxima.
     */
    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

     /**
     * Obtiene la vida actual del personaje.
     *
     * @return Vida actual.
     */
    public int getVidaActual() {
        return this.vidaActual;
    }

    /**
     * Establece la vida actual del personaje.
     *
     * @param vidaActual Nueva vida actual.
     */
    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    /**
     * Obtiene la velocidad del personaje.
     *
     * @return Velocidad.
     */
    public int getVelocidad() {
        return this.velocidad;
    }

      /**
     * Establece la velocidad del personaje.
     *
     * @param velocidad Nueva velocidad.
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Obtiene el ataque del personaje.
     *
     * @return Valor de ataque.
     */
    public int getAtaque() {
        return this.ataque;
    }

     /**
     * Establece el valor de ataque del personaje.
     *
     * @param ataque Nuevo valor de ataque.
     */
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    /**
     * Obtiene la defensa del personaje.
     *
     * @return Valor de defensa.
     */
    public int getDefensa() {
        return this.defensa;
    }

     /**
     * Establece el valor de defensa del personaje.
     *
     * @param defensa Nueva defensa.
     */
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

     /**
     * Obtiene el nivel del personaje.
     *
     * @return Nivel actual.
     */
    public int getNivel() {
        return this.nivel;
    }

    /**
     * Establece el nivel del personaje.
     *
     * @param nivel Nuevo nivel.
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * Obtiene la posición actual del personaje.
     *
     * @return Objeto Posicion.
     */
    public Posicion getPosicion() {
        return posicion;
    }

     /**
     * Establece la posición del personaje.
     *
     * @param posicion Nueva posición.
     */
    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

     /**
     * Obtiene la fuerza del personaje.
     *
     * @return Valor de fuerza.
     */
    public int getFuerza() {
        return fuerza;
    }

     /**
     * Establece la fuerza del personaje.
     *
     * @param fuerza Nueva fuerza.
     */
    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    //Mostrar los datos
     /**
     * Devuelve una representación en cadena del personaje.
     *
     * @return Cadena con los atributos principales del personaje.
     */
    @Override
    public String toString() {
        return "{" +
            " nombre='" + getNombre() + "'" +
            ", id='" + getId() + "'" +
            ", vidaMaxima='" + getVidaMaxima() + "'" +
            ", vidaActual='" + getVidaActual() + "'" +
            ", velocidad='" + getVelocidad() + "'" +
            ", ataque='" + getAtaque() + "'" +
            ", defensa='" + getDefensa() + "'" +
            ", nivel='" + getNivel() + "'" +
            "}";
    }

    //Métodos
    //1. Mover. 

      /**
     * Mueve al personaje en la dirección especificada.
     *
     * @param direccion Dirección a la que se moverá el personaje.
     */
    public void mover(Direccion direccion) {
        
        if (direccion == Direccion.ARRIBA) {
            this.posicion.setY(this.posicion.getY() - 1);
        } else if (direccion == Direccion.ABAJO) {
            this.posicion.setY(this.posicion.getY() + 1);
        } else if (direccion == Direccion.IZQUIERDA) {
            this.posicion.setX(this.posicion.getX() - 1);
        } else if (direccion == Direccion.DERECHA) {
            this.posicion.setX(this.posicion.getX() + 1);
        }
    }

    //2. Atacar. Se ejecuta un ataque

     /**
     * Ataca a otro personaje, calculando y aplicando daño según el ataque y defensa.
     *
     * @param objetivo Personaje que recibe el ataque.
     */
    public void atacar(Personaje objetivo) {
        int danio = Math.max(0, this.ataque - objetivo.getDefensa());
        objetivo.recibirDanio(danio);
    }

    //3. Recibir daño. Se reduce la vida

    /**
     * Aplica daño al personaje reduciendo su vida actual.
     *
     * @param danio Cantidad de daño recibido.
     */
    public void recibirDanio(int danio) {
        this.vidaActual -= danio;
        if (this.vidaActual < 0) {
            this.vidaActual = 0;
        }
        notificarObservadores();
    }

    //4. Tiene vida, esta vivo. Comprueba si el personaje tiene vida.

    /**
     * Indica si el personaje sigue con vida.
     *
     * @return true si la vida actual es mayor que cero, false en caso contrario.
     */
    public boolean estaVivo() {
        return this.vidaActual > 0;
    }

    //5. Calcular la distancia, la posicion de los personajes

     /**
     * Calcula la distancia Manhattan entre este personaje y otro.
     *
     * @param otra Otro personaje.
     * @return Distancia entre los dos personajes.
     */
    public int calcularDistancia(Personaje otra) {
        return Math.abs(this.posicion.getX() - otra.getPosicion().getX()) +
               Math.abs(this.posicion.getY() - otra.getPosicion().getY());
    }

    public abstract void realizarTurno(Mapa mapa);
    /**
     * Verifica si dos personajes son iguales por su ID.
     *
     * @param o Objeto a comparar.
     * @return true si tienen el mismo ID, false en caso contrario.
     */

    //6. equals y hashcode.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Personaje)) return false;
        Personaje personaje = (Personaje) o;
        return id == personaje.id;
    }

    /**
     * Devuelve el hashcode del personaje basado en su ID.
     *
     * @return Código hash del personaje.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Agrega un observador al personaje.
     *
     * @param o Observador a agregar.
     */
	@Override
	public void agregarObservador(Observador o) {
		observadores.add(o);
	}

    /**
     * Elimina un observador del personaje.
     *
     * @param o Observador a eliminar.
     */
	@Override
	public void eliminarObservador(Observador o) {
		observadores.remove(o);
	}

     /**
     * Notifica a todos los observadores sobre un cambio en el estado del personaje.
     */
	@Override
	public void notificarObservadores() {
		for (Observador o : observadores) {
            o.actualizar();
        }
	}

    
}
