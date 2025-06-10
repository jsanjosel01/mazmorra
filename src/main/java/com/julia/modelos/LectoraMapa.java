package com.julia.modelos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.julia.App;

public class LectoraMapa {
    //Leer el archivo, tablero mas enemigos

    private static final String rutaTablero = "dataUrl/tablero.txt";
    private static final String rutaEnemigos = "dataUrl/enemigos.txt";

    /**
     * Lee el archivo tablero.txt y construye el objeto Mapa con celdas y personajes.
     * @throws Exception si hay error leyendo archivos o datos inválidos.
     */
    public Mapa cargarMapa() throws Exception {
        List<String> lineas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(App.class.getResource(rutaTablero).toURI()))))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    lineas.add(linea);
                }
            }
        }

        if (lineas.isEmpty()) {
            throw new Exception("El archivo tablero.txt está vacío o no contiene datos válidos.");
        }

        int alto = lineas.size();
        int ancho = lineas.get(0).length();

        // Validar que todas las líneas tengan la misma longitud
        for (int i = 1; i < alto; i++) {
            if (lineas.get(i).length() != ancho) {
                throw new Exception("Línea " + (i + 1) + " tiene una longitud distinta del resto.");
            }
        }

        Mapa mapa = new Mapa(ancho, alto);

        for (int y = 0; y < alto; y++) {
            String fila = lineas.get(y);
            for (int x = 0; x < ancho; x++) {
                char simbolo = fila.charAt(x);
                TipoCelda tipo = TipoCelda.SUELO;
                switch (simbolo) {
                    case '.':
                        tipo = TipoCelda.SUELO;
                        break;
                    case '#':
                        tipo = TipoCelda.MURO;
                        break;
                    default:
                        throw    new IllegalArgumentException("Símbolo no reconocido en mapa: " + simbolo);
                }
                mapa.setCelda(x, y, new Celda(tipo.isTransitable(), tipo));
            }
        }

        cargarPersonajes(mapa);

        return mapa;
    }

    /**
     * Lee héroe y enemigos desde enemigos.txt y los agrega al escenario.
     * @throws Exception si hay error leyendo el archivo o datos inválidos.
     */
    private void cargarPersonajes(Mapa mapa) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(App.class.getResource(rutaEnemigos).toURI()))))) {

            String linea;
            br.readLine();
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty() || linea.startsWith("nombre")) continue;

                String[] partes = linea.split(",");
                if (partes.length < 8) continue;

                String nombre = partes[0].trim();
                int salud = Integer.parseInt(partes[1].trim());
                int fuerza = Integer.parseInt(partes[2].trim());
                int defensa = Integer.parseInt(partes[3].trim());
                int velocidad = Integer.parseInt(partes[4].trim());
                int percepcion = Integer.parseInt(partes[5].trim());
                int posX = Integer.parseInt(partes[6].trim());
                int posY = Integer.parseInt(partes[7].trim());

                Posicion posicion = new Posicion(posX, posY);

                if (!mapa.esPosicionValida(posicion)) {
                    throw new IllegalArgumentException("Posición inválida para personaje: " + nombre + " en (" + posX + "," + posY + ")");
                }

               if (nombre.equalsIgnoreCase("Jugador") || nombre.equalsIgnoreCase("Heroe")) {
                    Heroe heroe = new Heroe(nombre, posicion, salud, fuerza, defensa, velocidad);
                    mapa.agregarPersonaje(heroe);
                } else {
                    Enemigo enemigo = new Enemigo(nombre, posicion, salud, fuerza, defensa, velocidad, percepcion);
                    mapa.agregarPersonaje(enemigo);
                }

            }
        }

    }
}                
            