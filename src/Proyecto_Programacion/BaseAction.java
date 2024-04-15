package Proyecto_Programacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaseAction implements ActionListener {
    protected Ventana pantalla;

    public BaseAction(Ventana pantalla) {
        this.pantalla = pantalla;
    }

    public void actionPerformed(ActionEvent e) {
    }
}