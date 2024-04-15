package Proyecto_Programacion;


import java.io.File;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

abstract class BaseAction implements ActionListener {
    protected Ventana pantalla;

    public BaseAction(Ventana pantalla) {
        this.pantalla = pantalla;
    }

    public abstract void actionPerformed(ActionEvent e);
}

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
    
    void imprimirMenu() {
        setBackground(new File("img/fondoMenu.png"));
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Mi Juego", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel, BorderLayout.NORTH);

        JButton jugarButton = new JButton("Jugar");
        JButton editorButton = new JButton("Editor");
        JButton configuracionButton = new JButton("Configuración");
        JButton salirButton = new JButton("Salir");

        Dimension buttonSize = new Dimension(200, 50);
        jugarButton.setPreferredSize(buttonSize);
        editorButton.setPreferredSize(buttonSize);
        configuracionButton.setPreferredSize(buttonSize);
        salirButton.setPreferredSize(buttonSize);

        JugarAction jugarAction = new JugarAction(this);
        EditorAction editorAction = new EditorAction(this);
        ConfiguracionAction configuracionAction = new ConfiguracionAction(this);
        SalirAction salirAction = new SalirAction();

        jugarButton.addActionListener(jugarAction);
        editorButton.addActionListener(editorAction);
        configuracionButton.addActionListener(configuracionAction);
        salirButton.addActionListener(salirAction);

        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.VERTICAL;

        menuPanel.add(jugarButton, gbc);
        menuPanel.add(editorButton, gbc);
        menuPanel.add(configuracionButton, gbc);
        menuPanel.add(salirButton, gbc);

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setOpaque(false);
        outerPanel.setBorder(BorderFactory.createEmptyBorder(200, 100, 100, 100));

        outerPanel.add(menuPanel, BorderLayout.CENTER);

        add(outerPanel, BorderLayout.CENTER);
    }

        // Clase de acción para el botón "Salir"
        private static class SalirAction extends BaseAction {
            public SalirAction() {
                super(null);
            }
    
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }
    
        // Clase de acción para el botón "Jugar"
        private static class JugarAction extends BaseAction {
            public JugarAction(Ventana pantalla) {
                super(pantalla);
            }
    
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(pantalla, "¡Jugar presionado!");
            }
        }
    
        // Clase de acción para el botón "Editor"
        private static class EditorAction extends BaseAction {
            public EditorAction(Ventana pantalla) {
                super(pantalla);
            }
    
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(pantalla, "¡Editor presionado!");
            }
        }
    
        // Clase de acción para el botón "Configuración"
        private static class ConfiguracionAction extends BaseAction {
            public ConfiguracionAction(Ventana pantalla) {
                super(pantalla);
            }
    
            public void actionPerformed(ActionEvent e) {
                (pantalla).configuracionPanel();
            }
        }
    
        // Método para configurar y mostrar el panel de configuración
    void configuracionPanel() {
        // Crear y configurar el panel de configuración
        setBackground(new File("img/fondoConfiguracion.png"));
        setLayout(new BorderLayout());
        JPanel configuracionPanel = new JPanel(new GridLayout(3, 2));
        configuracionPanel.setOpaque(false);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        JButton boton1 = new JButton("boton1");
        JButton volver = new JButton("volver a menu");
    
        configuracionPanel.add(boton1, gbc);
        configuracionPanel.add(volver, gbc);
    
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setOpaque(false);
        outerPanel.setBorder(BorderFactory.createEmptyBorder(200, 100, 100, 100));
    
        outerPanel.add(configuracionPanel, BorderLayout.CENTER);
    
        add(outerPanel, BorderLayout.CENTER);
    
        // Limpiar el contenido actual de la pantalla
        getContentPane().removeAll();
    
        // Añadir el panel de configuración al contenido de la ventana
        getContentPane().add(outerPanel, BorderLayout.CENTER);
    
        // Volver a pintar la pantalla para que se muestren los cambios
        revalidate();
        repaint();
    
        // Agregar ActionListener para el botón "Volver a menu"
        volver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverAlMenu();
            }
        });
    }
    private void volverAlMenu() {
        getContentPane().removeAll(); // Eliminar todos los componentes
        imprimirMenu(); // Vuelve a configurar el menú principal
        revalidate(); // Volver a validar la ventana
        repaint(); // Volver a pintar la ventana
    }
    
    
    

    
}