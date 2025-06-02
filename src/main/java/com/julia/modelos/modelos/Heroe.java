package proyecto.modelos;

/**
 * Representa un héroe controlado por el jugador en el juego.
 * Hereda de {@link Personaje} y puede ganar experiencia y moverse según una dirección.
 */
public class Heroe extends Personaje {
    private int experiencia;
    private Direccion direccion;

    /**
     * Crea un nuevo héroe con nombre y posición inicial.
     *
     * @param nombre   El nombre del héroe.
     * @param posicion La posición inicial del héroe.
     */
    public Heroe(String nombre, Posicion posicion){
        super(nombre, posicion);
        this.experiencia=0;

    }

    //Getter y setters
     /**
     * Obtiene la experiencia acumulada del héroe.
     *
     * @return La experiencia actual.
     */
    public int getExperiencia(){
        return this.experiencia;
    }

    
    /**
     * Establece la experiencia del héroe.
     *
     * @param experiencia El nuevo valor de experiencia.
     */
    public void setExperiencia(int experiencia){
        this.experiencia = experiencia;
    }

    /**
     * Asigna una dirección al héroe para su próximo movimiento.
     *
     * @param direccion Dirección hacia la cual se moverá el héroe.
     */
    public void setDireccion(Direccion direccion){
        this.direccion = direccion;
    }

     /**
     * Obtiene la dirección actual del héroe.
     *
     * @return La dirección actual o null si no se ha establecido.
     */
    public Direccion getDireccion(){
        return this.direccion;
    }

    //Mostar datos
    /**
     * Devuelve una representación en texto del héroe, incluyendo su experiencia.
     *
     * @return Cadena con información del héroe.
     */
    @Override
    public String toString(){
        return super.toString()+ "{" +
            " experiencia='" + getExperiencia() + "'" +
            "}";
    }

    
    //Realizar turnos en el mapa
    
    /**
     * Realiza el turno del héroe en el mapa. Se moverá en la dirección indicada si la celda es transitable,
     * o atacará a un enemigo si hay uno en la posición destino.
     *
     * @param mapa El mapa del juego sobre el cual se mueve el héroe.
     */
    @Override
    public void realizarTurno(Mapa mapa){
        int nuevaX = this.getPosicion().getX();
        int nuevaY = this.getPosicion().getY();
        Posicion nuevaPosicion = new Posicion(nuevaX, nuevaY);

        switch (this.direccion) {
            case ARRIBA:
                nuevaY--;
                break;
            case ABAJO:
                nuevaY++;
                break;
            case IZQUIERDA:
                nuevaX--;
                break;
            case DERECHA:
                nuevaX++;
                break;
            default:
                return;
        }

        Personaje posibleEnemigo = mapa.getCelda(nuevaX, nuevaY).getPersonaje();
        if(posibleEnemigo != null){
            atacar(posibleEnemigo);
        }else{
            mapa.moverPersonaje(this, nuevaPosicion);
        }

        // Resetear la dirección después de realizar el turno
        direccion = null; 
    }


}
