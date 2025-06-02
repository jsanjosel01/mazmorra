package proyecto.modelos;

public class Proveedor {
      private static Proveedor instance;
      private GestorPersonajes gestorPersonajes;

      private Proveedor(){
            this.gestorPersonajes = new GestorPersonajes();
      } 

      public static Proveedor getInstance(){
        if (instance == null){
            instance = new Proveedor();
        }
        return instance;
      }

      public GestorPersonajes getGestorPersonajes(){
        return gestorPersonajes;
      }

      public void setGestorPersonajes(GestorPersonajes gestorPersonajes){
        this.gestorPersonajes = gestorPersonajes;
      }
      

}
