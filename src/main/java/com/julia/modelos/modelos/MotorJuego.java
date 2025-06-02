package proyecto.modelos;

import java.util.Map;

public class MotorJuego {
 private Mapa mapa;

    public MotorJuego(Mapa mapa){
        this.mapa = mapa;
    }

    //El heroe se mueve
    public boolean moverHeroe(Direccion direccion){
        Heroe heroe = mapa.getHeroe();
        if (heroe == null || !heroe.estaVivo()) return false;

        Posicion nuevaPos = calcularNuevaPosicion(heroe.getPosicion(), direccion);

        if (!mapa.esPosicionValida(nuevaPos)){
            return false;
        }

        Personaje personajeEnDestino = mapa.getPosicionesPersonajes().get(nuevaPos);
        if (personajeEnDestino instanceof Enemigo){
           
            combate(heroe, (Enemigo) personajeEnDestino);

            if (!personajeEnDestino.estaVivo()){
                mapa.getPosicionesPersonajes().remove(nuevaPos);
                Celda celdaDestino = mapa.getCelda(nuevaPos.getX(), nuevaPos.getY());
                if (celdaDestino != null) celdaDestino.setPersonaje(null);
            }
            return heroe.estaVivo();
        } else {
            return mapa.moverPersonaje(heroe, nuevaPos);
        }
    }

    //Mueve todos los enemigos
    public void moverEnemigos(){
        Map<Posicion, Personaje> copiaPersonajes = Map.copyOf(mapa.getPosicionesPersonajes());
        for (Personaje personaje : copiaPersonajes.values()){
            if (personaje instanceof Enemigo && personaje.estaVivo()){
                moverEnemigo((Enemigo) personaje);
            }
        }
    }

    private void moverEnemigo(Enemigo enemigo){
        Posicion posEnemigo = enemigo.getPosicion();
        Heroe heroe = mapa.getHeroe();

        if (heroe == null) return;

        int distancia = distanciaManhattan(posEnemigo, heroe.getPosicion());

        Posicion nuevaPos = null;
        if (distancia <= 5) {
            nuevaPos = moverHacia(enemigo, heroe.getPosicion());
        } else {
            nuevaPos = moverAleatorio(enemigo);
        }

        if (nuevaPos != null && nuevaPos.equals(heroe.getPosicion())){
            combate(enemigo, heroe);
        }
    }

    //Combate entre atacante y defensor
    private void combate(Personaje atacante, Personaje defensor){
        int danio = atacante.getAtaque() - defensor.getDefensa();
        if (danio < 0) danio = 0;

        defensor.setVidaActual(defensor.getVidaActual() - danio);

        System.out.println(atacante.getNombre() + " ataca a " + defensor.getNombre() + " con daño: " + danio);

        if (!defensor.estaVivo()) {
            System.out.println(defensor.getNombre() + " ha muerto.");
        }
    }

    //Mueve un enemigo hacia una posición destino, si puede
    private Posicion moverHacia(Enemigo enemigo, Posicion objetivo){
        Posicion actual = enemigo.getPosicion();
        int dx = objetivo.getX() - actual.getX();
        int dy = objetivo.getY() - actual.getY();

        Direccion direccion = null;

        if (Math.abs(dx) > Math.abs(dy)){
            direccion = dx > 0 ? Direccion.DERECHA : Direccion.IZQUIERDA;
        } else if (dy != 0) {
            direccion = dy > 0 ? Direccion.ABAJO : Direccion.ARRIBA;
        }

        if (direccion != null){
            Posicion nuevaPos = calcularNuevaPosicion(actual, direccion);
            boolean movio = mapa.moverPersonaje(enemigo, nuevaPos);
            if (movio) {
                return nuevaPos;
            }
        }
        return actual;
    }

    //Movimiento aleatorio para enemigo
    private Posicion moverAleatorio(Enemigo enemigo) {
        Direccion[] direcciones = Direccion.values();
        for (int i = 0; i < direcciones.length; i++) {
            Direccion direccion = direcciones[(int) (Math.random() * direcciones.length)];
            Posicion nuevaPos = calcularNuevaPosicion(enemigo.getPosicion(), direccion);
            if (mapa.moverPersonaje(enemigo, nuevaPos)) {
                return nuevaPos;
            }
        }
        return enemigo.getPosicion();
    }

    //Calcula nueva posición a partir de dirección
   private Posicion calcularNuevaPosicion(Posicion posicion, Direccion direccion){
    int x = posicion.getX();
    int y = posicion.getY();

    if (direccion == Direccion.ARRIBA){
        y--;
    }else if (direccion == Direccion.ABAJO){
        y++;
    }else if (direccion == Direccion.IZQUIERDA){
        x--;
    }else if (direccion == Direccion.DERECHA){
        x++;
    }

    return new Posicion(x, y);
}

    //Distancia Manhattan entre dos posiciones
    private int distanciaManhattan(Posicion a, Posicion b){
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    //Verifica si el juego terminó
    public boolean isJuegoTerminado(){
        Heroe heroe = mapa.getHeroe();
        if (heroe == null || !heroe.estaVivo()) return true;

        //y sí no quedan enemigos vivos
        for (Personaje p : mapa.getPosicionesPersonajes().values()){
            if (p instanceof Enemigo && p.estaVivo()){
                return false;
            }
        }
        return true;
        
    }
}
