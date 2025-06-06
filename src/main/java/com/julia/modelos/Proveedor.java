package com.julia.modelos;

public class Proveedor {
  private static Proveedor instance;
  private GestorPersonajes gestorPersonajes;
  private Heroe heroe;
  private Mapa mapa;

  private Proveedor() {
    this.gestorPersonajes = new GestorPersonajes();
    this.mapa = null;
  }

  public static Proveedor getInstance() {
    if (instance == null) {
      instance = new Proveedor();
    }
    return instance;
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
