       
package Proyecto_Programacion;


import java.io.File;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;



class Configuracion {
    Ventana ventana;
    //Menu menu; TODO
    

    public Configuracion(Ventana ventana) {
        this.ventana = ventana;
    }
    
    public void imprimirConfiguracion() {
        ventana.setBackground(new File("img/fondoConfiguracion.png"));
        ventana.setLayout(new BorderLayout());

        JPanel configuracionPanel = new JPanel(new GridLayout(3, 2));
        configuracionPanel.setOpaque(false);

        JButton boton1 = new JButton("boton1");
        JButton volver = new JButton("volver a menu");

        configuracionPanel.add(boton1);
        configuracionPanel.add(volver);

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setOpaque(false);
        outerPanel.setBorder(BorderFactory.createEmptyBorder(200, 100, 100, 100));

        outerPanel.add(configuracionPanel, BorderLayout.CENTER);

        ventana.getContentPane().removeAll();
        ventana.getContentPane().add(outerPanel, BorderLayout.CENTER);
        ventana.revalidate();
        ventana.repaint();

        volver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverAlMenu();
            }
        });
    }

    private void volverAlMenu() {
        ventana.getContentPane().removeAll(); // Eliminar todos los componentes
        Menu menu = new Menu(ventana);//crea infinitas clases Menu = Mucha memoria, TODO: cambiar esto
        menu.imprimirMenu(); 
        ventana.revalidate(); // Volver a validar la ventana
        ventana.repaint(); // Volver a pintar la ventana
    }
}