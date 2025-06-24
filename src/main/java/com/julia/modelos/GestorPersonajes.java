package com.julia.modelos;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image; //IMAGENES DEL HEROE, ENEMIGOS Y TRAMPA O MALDICION
/**
 * Clase utilitaria para gestionar la carga y caché de imágenes en el juego.
 * Permite optimizar el rendimiento evitando cargar varias veces la misma imagen.
 */

public class GestorPersonajes{
     /**
     * Mapa estático que almacena las imágenes ya cargadas para reutilizarlas
     */
    private static final Map<String, Image> cache = new HashMap<>();

    /**
     * Carga una imagen desde la ruta especificada y la almacena en caché para futuros usos.
     * Si la imagen ya fue cargada previamente, la devuelve directamente desde la caché.
     * @param ruta Ruta relativa del recurso de imagen
     * @return La imagen cargada o null si no se pudo cargar.
     */
    public static Image getImagen(String ruta) {
        if (!cache.containsKey(ruta)) {
            try {
                Image imagen = new Image(GestorPersonajes.class.getResourceAsStream(ruta));
                cache.put(ruta, imagen);
            } catch (Exception e) {
                System.err.println("No se pudo cargar la imagen: " + ruta);
            }
        }
        return cache.get(ruta);
    }

    /**
     * Ruta de la imagen del heroe mirando hacia arriba, abajo, izquierda, derecha
     */
    public static final String PROTA_ARRIBA = "/com/julia/imagenes/imagenes/prota_espalda.png";
    public static final String PROTA_ABAJO = "/com/julia/imagenes/imagenes/prota_delante.png";
    public static final String PROTA_IZQUIERDA = "/com/julia/imagenes/imagenes/prota_izquierda.png";
    public static final String PROTA_DERECHA = "/com/julia/imagenes/imagenes/prota_derecha.png";
    /**
     * Ruta de la imagen del enemigo mirando hacia delante.
     */
    public static final String ENE_ABAJO = "/com/julia/imagenes/imagenes/goblin_delante.png";
    /**
     * Ruta de la imagen de la celda de pared y muro.
     */
    public static final String SUELO = "/com/julia/imagenes/imagenes/Escenario.png";
    public static final String MURO = "/com/julia/imagenes/imagenes/muro.jpg";

    //TRAMPA
    //public static final String TRAMPA = "/com/julia/imagenes/imagenes/trampa.jpg";

    //MALDICION
    //public static final String MALDICION = "/com/julia/imagenes/imagenes/maldicion.jpg";

    
}