package com.julia.modelos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GestorEnemigos {

     public static ArrayList<Enemigo> cargarEnemigosDesdeArchivo(String rutaArchivo){
        ArrayList<Enemigo> enemigos = new ArrayList<>();

         try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))){
            String linea = br.readLine();

            while ((linea = br.readLine()) != null){
                String[] partes = linea.split(",");

                
                if (partes.length == 8){
                    String nombre = partes[0];
                    int x = Integer.parseInt(partes[1]);
                    int y = Integer.parseInt(partes[2]);
                    Posicion posicion = new Posicion(x, y);
                    int vida = Integer.parseInt(partes[3]);
                    int fuerza = Integer.parseInt(partes[4]);
                    int defensa = Integer.parseInt(partes[5]);
                    int velocidad = Integer.parseInt(partes[6]);
                    int percepcion = Integer.parseInt(partes[7]);

                    Enemigo enemigo = new Enemigo(nombre, posicion, vida, fuerza, defensa, velocidad, percepcion);
                    enemigos.add(enemigo);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return enemigos;
    }

}
