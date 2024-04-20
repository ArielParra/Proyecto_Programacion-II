package Proyecto_Programacion;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class Menu {
    private JFrame frame; //Ventana
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Image backgroundImage;
    private Image backgroundImageConfig;
    public Juego juego = new Juego();
    public Float gainControl = 0.0f;

    public Menu() {
       // Cargar ruta de imagen de fondo
        try {
            backgroundImage = ImageIO.read(new File("../../images/fondo.jpg")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            backgroundImageConfig = ImageIO.read(new File("../../images/fondoconfig.jpg")); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crear ventana JFrame
        frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Crear un CardLayout para administrar diferentes paneles
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
    

        // paneles
        JPanel menuPanel = createMenuPanel();
        JPanel configPanel = createConfigPanel();
        
        JPanel juegoPanel = createJuegoPanel();
        // Añadir paneles al CardLayout
        cardPanel.add(menuPanel, "menu");
        cardPanel.add(juegoPanel, "juego");
        cardPanel.add(configPanel, "config");

        // Añadir cardPanel a la ventana
        frame.add(cardPanel);

        // Poner visible la ventana
        // Centrar el layout en la ventana
        frame.setLayout(cardLayout);
        frame.setVisible(true);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();

        //pintar imagen de fondo a panel con graphics
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        // Crear etiqueta de título
        JLabel titulo = new JLabel("Menu");
        titulo.setBounds(0, 0, 400, 30);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo);

        // Crear botones
        JButton button1 = new JButton("Jugar");
        button1.setBounds(100, 100, 200, 30);
        panel.add(button1);

        JButton button2 = new JButton("Configuracion");
        button2.setBounds(100, 150, 200, 30);
        panel.add(button2);

        JButton button3 = new JButton("Salir");
        button3.setBounds(100, 200, 200, 30);
        panel.add(button3);

        // Añadir action listeners a los botones
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "juego");
                juego.Iniciar();
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "config");
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        return panel;
    }

    private JPanel createConfigPanel() {
        JPanel panel = new JPanel();

        //pintar imagen de fondo a panel con graphics
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImageConfig, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        // Crear etiqueta de título
        JLabel titulo = new JLabel("Menu Configuracion");
        titulo.setBounds(0, 0, 400, 30);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo);

        // Crear botones
        JButton buttonc1 = new JButton("Dificultad");
        buttonc1.setBounds(100, 50, 200, 30);
        panel.add(buttonc1);
        JLabel volumen = new JLabel("Volumen");
        volumen.setBounds(100, 75, 200, 30);
        volumen.setForeground(Color.WHITE);
        volumen.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(volumen);
        // Crear un JSlider para ajustar el volumen
        JSlider volumenSlider = new JSlider(-50, 4, 0);
        volumenSlider.setValue(-10);
        volumenSlider.setBounds(100, 100, 200, 30);
        panel.add(volumenSlider);
        // Agregar un ChangeListener para ajustar el volumen en tiempo real
        volumenSlider.addChangeListener(e -> {
            float nuevoVolumen = volumenSlider.getValue();
            gainControl = nuevoVolumen;
            juego.setVolumen(nuevoVolumen);
        });

        JButton buttonc4 = new JButton("Controles");
        buttonc4.setBounds(100, 150, 200, 30);
        panel.add(buttonc4);

        JButton buttonc3 = new JButton("Volver al Menu");
        buttonc3.setBounds(100, 200, 200, 30);
        panel.add(buttonc3);

        // Añadir action listener a buttonc3
        buttonc3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "menu");
            }
        });

        return panel;
    }
    public JPanel createJuegoPanel() {
        JPanel panel = new JPanel();

        //Aqui se crean los elementos del juego
        //Lo que mostrara el panel 
        JLabel titulo = new JLabel("Probando Juego");
        titulo.setBounds(0, 0, 400, 30);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        JButton boton = new JButton("Salir");
        boton.setBounds(100, 100, 100, 50);
        panel.add(titulo);
        panel.add(boton);

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "menu");
                juego.PararSonido();
            }
        });

        //Se pensara un menu para canciones en este apartado

        return panel;
    }

}
