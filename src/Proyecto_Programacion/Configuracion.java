       
package Proyecto_Programacion;


import java.io.File;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;



class Configuracion {
    Ventana ventana;
    Menu menu;
    

    public Configuracion(Ventana ventana, Menu menu) {
        this.ventana = ventana;
        this.menu = menu; 
    }
    
    public void imprimirConfiguracion() {
        ventana.setBackground(new File("img/fondoConfiguracion.png"));
        ventana.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Configuracion", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);
        ventana.add(titleLabel, BorderLayout.NORTH);
        
             JButton boton1 = new JButton("boton1");
        JButton volver = new JButton("volver a menu");

        Dimension buttonSize = new Dimension(200, 25);
        boton1.setPreferredSize(buttonSize);
        volver.setPreferredSize(buttonSize);

        JPanel configuracionPanel = new JPanel(new GridLayout(2,1));
        configuracionPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.VERTICAL;

        configuracionPanel.add(boton1);
        configuracionPanel.add(volver);

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setOpaque(false);
        outerPanel.setBorder(BorderFactory.createEmptyBorder(500, 800, 100, 800));

        outerPanel.add(configuracionPanel, BorderLayout.CENTER);

        //ventana.add(outerPanel, BorderLayout.CENTER);
        //ventana.getContentPane().removeAll();
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
        menu.imprimirMenu(); // Utilizar la instancia existente de Menu
        ventana.revalidate(); // Volver a validar la ventana
        ventana.repaint(); // Volver a pintar la ventana
    }

}