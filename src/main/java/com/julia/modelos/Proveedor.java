package com.julia.modelos;

import java.util.ArrayList;

public class Proveedor {
  private static Proveedor instance;
  private GestorPersonajes gestorPersonajes;
  private Heroe heroe;
  private Mapa mapa;
 

  private Proveedor() {
      this.gestorPersonajes = new GestorPersonajes();
        try {
          this.mapa = new LectoraMapa().cargarMapa();
          //this.heroe = new Heroe();
          cargarEnemigos();

        } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public static Proveedor getInstance() {
      if (instance == null) {
        instance = new Proveedor();
      }
      return instance;
    }
    

      public void cargarEnemigos() {
        String rutaArchivo = "/com/julia/dataUrl/enemigos.txt";

        ArrayList<Enemigo> enemigosDesdeArchivo = GestorEnemigos.cargarEnemigosDesdeRecurso(rutaArchivo);

        for (Enemigo enemigo : enemigosDesdeArchivo) {
            gestorPersonajes.insertarPersonaje(enemigo);
        }
    }

  

  public GestorPersonajes getGestorPersonajes() {
    return gestorPersonajes;
  }

  public void setGestorPersonajes(GestorPersonajes gestorPersonajes) {
    this.gestorPersonajes = gestorPersonajes;
  }

  public Heroe getHeroe() {
    return this.heroe;
  }

  public void setHeroe(Heroe heroe) {
    this.heroe = heroe;
  }

  //Getters y setters Mapa
   public Mapa getMapa() {
    return this.mapa;
  }

  public void setMapa(Mapa mapa) {
    this.mapa = mapa;
  }


}
