package com.julia.modelos;

public class Proveedor {
  private static Proveedor instance;
  private GestorPersonajes gestorPersonajes;
  private Heroe heroe;

  private Proveedor() {
    this.gestorPersonajes = new GestorPersonajes();
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

}
