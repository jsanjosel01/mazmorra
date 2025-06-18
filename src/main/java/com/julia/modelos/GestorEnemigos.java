package com.julia.modelos;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GestorEnemigos {

    public static ArrayList<Enemigo> cargarEnemigosDesdeRecurso(String rutaRecurso) {
        ArrayList<Enemigo> enemigos = new ArrayList<>();

        try (InputStream is = GestorEnemigos.class.getResourceAsStream(rutaRecurso);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String linea = br.readLine(); 
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");

                if (partes.length == 8) {
                    String nombre = partes[0];
                    int vida = Integer.parseInt(partes[1]);
                    int fuerza = Integer.parseInt(partes[2]);
                    int defensa = Integer.parseInt(partes[3]);
                    int velocidad = Integer.parseInt(partes[4]);
                    int percepcion = Integer.parseInt(partes[5]);
                    int x = Integer.parseInt(partes[6]);
                    int y = Integer.parseInt(partes[7]);

                    Posicion posicion = new Posicion(x, y);
                    Enemigo enemigo = new Enemigo(nombre, posicion, vida, fuerza, defensa, velocidad, percepcion);
                    enemigos.add(enemigo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enemigos;
    }
}