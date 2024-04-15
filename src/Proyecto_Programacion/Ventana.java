package Proyecto_Programacion;


import java.io.File;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;



class Ventana extends JFrame {

    public Ventana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        setTitle("TItulo");
    }

    protected void setBackground(File file){
        if(file.exists()) {
            setContentPane(new JLabel(new ImageIcon(file.getAbsolutePath())));
        } else {
            getContentPane().setBackground(Color.BLUE);
        }
    }
    void mostrar(){
        setVisible(true);
    }
    
}