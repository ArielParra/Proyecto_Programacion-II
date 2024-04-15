package Proyecto_Programacion;

/**
 *
 * @authors Ariel y Miguel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Ventana ventana = new Ventana("Titulo de la ventana");
        Menu menu = new Menu (ventana);
        menu.imprimirMenu();
        ventana.mostrar();

    }
    
}
