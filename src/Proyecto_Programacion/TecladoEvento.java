package Proyecto_Programacion;
import java.awt.event.KeyEvent;
import java.io.Serializable;

public class TecladoEvento implements Serializable {
    private int codigoTecla;
    private char tecla;
    private boolean presionada;

    public TecladoEvento(int codigoTecla, char tecla, boolean presionada) {
        this.codigoTecla = codigoTecla;
        this.tecla = tecla;
        this.presionada = presionada;
    }

    public int getCodigoTecla() {
        return codigoTecla;
    }

    public char getTecla() {
        return tecla;
    }

    public boolean isPresionada() {
        return presionada;
    }

    public int getID() {
        return presionada ? KeyEvent.KEY_PRESSED : KeyEvent.KEY_RELEASED;
    }

    public long getWhen() {
        // Devuelve el tiempo actual en milisegundos
        return System.currentTimeMillis();
    }

    public int getModifiersEx() {
        // No se manejan modificadores, por lo que devuelvo 0
        return 0;
    }

    public int getKeyCode() {
        // Devuelve el código de la tecla
        return codigoTecla;
    }

    public char getKeyChar() {
        // Devuelve el carácter asociado a la tecla
        return tecla;
    }

    public int getKeyLocation() {
        // En este ejemplo, no se maneja la ubicación de la tecla, por lo que devuelvo KeyEvent.KEY_LOCATION_UNKNOWN
        return KeyEvent.KEY_LOCATION_UNKNOWN;
    }

}
