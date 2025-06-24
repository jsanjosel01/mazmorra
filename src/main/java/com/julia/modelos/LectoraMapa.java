package com.julia.modelos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.julia.App;

public class LectoraMapa { //MAPA
    /**
     * Matriz de celdas que representa el mapa del tablero.
     */
    private Celda[][] mapa;

    /**
     * Construye un nuevo lector del tablero y carga el mapa desde el archivo especificado.
     * @param rutaRelativa rutaRelativa Ruta relativa al archivo del mapa.
     * @throws IOException Si ocurre algún error al leer el archivo o el formato es incorrecto.
     */
    public LectoraMapa(String rutaRelativa) throws IOException {
        leerDesdeArchivo(rutaRelativa);
    }

    /**
     * Lee el archivo de mapa, interpreta los símbolos y construye la matriz de celdas.
     * @param path Ruta al archivo de mapa.
     * @throws IOException Si el archivo no existe, está vacío, tiene líneas de distinta longitud
     * o contiene símbolos no reconocidos.
     */
    private void leerDesdeArchivo(String path) throws IOException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
            new FileInputStream(new File(App.class.getResource("dataUrl/tablero.txt").toURI()))))) {
            List<String> lineas = new ArrayList<>();
            String linea;

            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
            //Elimina líneas vacías
            lineas.removeIf(l -> l.trim().isEmpty());

            if (lineas.isEmpty()) {
                throw new IOException("El archivo del mapa está vacío o solo contiene líneas en blanco.");
            }

            int filas = lineas.size();
            int columnas = lineas.get(0).length();
            mapa = new Celda[filas][columnas];

            for (int y = 0; y < filas; y++) {
                String fila = lineas.get(y);

                if (fila.length() != columnas) {
                    throw new IOException("Las líneas del mapa no tienen el mismo largo. Línea " + y
                            + " tiene longitud " + fila.length());
                }
                for (int x = 0; x < columnas; x++) {
                    char simbolo = fila.charAt(x);

                    TipoCelda tipo;
                    switch (simbolo) {
                        case '.':
                            tipo = TipoCelda.SUELO;
                            break;
                        case '#':
                            tipo = TipoCelda.MURO;
                            break;
                        //case 'T': tipo = TipoCelda.TRAMPA; 
                            //break;
                        case '!': tipo = TipoCelda.TELE; //MALDICION 
                            break;
                        default:
                            throw new IllegalArgumentException("Símbolo no reconocido: " + simbolo);
                    }
                    mapa[y][x] = new Celda(tipo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error leyendo el mapa: " + e.getMessage(), e);
        }
    }

    /**
     * Devuelve la celda en la posición indicada.
     * @param fila Índice de la fila.
     * @param columna  Índice de la columna.
     * @return Celda en la posición dada.
     */
    public Celda getCelda(int fila, int columna) {
        return mapa[fila][columna];
    }

    /**
     * 
     * @return Número de filas del mapa (alto del escenario).
     */
    public int getAlto() {
        return mapa.length;
    }

    /**
     * 
     * @return Número de columnas del mapa (ancho del escenario).
     */
    public int getAncho() {
        return mapa[0].length;
    }

}