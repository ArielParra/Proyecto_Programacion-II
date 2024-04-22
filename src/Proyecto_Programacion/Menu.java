package Proyecto_Programacion;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Menu {
    private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];//necesario pantalla completa
    private JFrame frame; //Ventana
    private CardLayout cardLayout;
    private JPanel cardPanel;
    public Juego juego;
    public Float gainControl = 0.0f;

    public Menu() {

        //Crear un nuevo juego
        this.juego = new Juego();
        
        //Crear la unica ventana JFrame
        this.frame = new JFrame("Guitar Hero");//titulo de la ventana permanentemente
        this.frame.setMinimumSize(new Dimension(800, 600));
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un CardLayout para administrar diferentes paneles
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(cardLayout);
    
        // paneles
        JPanel menuPanel = createMenuPanel();
        JPanel configPanel = createConfigPanel();
        
        JPanel juegoPanel = createJuegoPanel();
        // Añadir paneles al CardLayout
        cardPanel.add(menuPanel, "menu");
        cardPanel.add(juegoPanel, "juego");
        cardPanel.add(juego, "bolita");
        cardPanel.add(configPanel, "config");

        // Añadir cardPanel a la ventana
        frame.add(cardPanel);

        // Poner visible la ventana
        // Centrar el layout en la ventana
        frame.setLayout(cardLayout);
        frame.setVisible(true);
    }
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image backgroundImageConfig = ImageIO.read(new File("images/fondo.jpg"));
                    g.drawImage(backgroundImageConfig, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    e.printStackTrace();
                    setBackground(Color.LIGHT_GRAY);
                }
            }
        };
        
        // Usar GridBagLayout
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
    
        // Crear etiqueta de título
        JLabel titulo = new JLabel("Menu");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 200, 0); 
        panel.add(titulo,constraints);
        constraints.insets = new Insets(10, 0, 10, 0); // Márgenes
        // Crear botones
        JButton button1 = new JButton("Jugar");
        button1.setPreferredSize(new Dimension(150, 50));
        constraints.gridy++;
        panel.add(button1, constraints);
    
        JButton button2 = new JButton("Configuracion");
        button2.setPreferredSize(new Dimension(150, 50));
        constraints.gridy++;
        panel.add(button2, constraints);
    
        JButton button3 = new JButton("Salir");
        button3.setPreferredSize(new Dimension(150, 50));
        constraints.gridy++;
        panel.add(button3, constraints);
    
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
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image backgroundImageConfig = ImageIO.read(new File("images/fondoconfig.jpg"));
                    g.drawImage(backgroundImageConfig, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    e.printStackTrace();
                    setBackground(Color.BLUE);
                }
            }
        };
    
        // Usa GridBagLayout
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Crear etiqueta de título
        JLabel titulo = new JLabel("Configuracion");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 100, 0); 
        constraints.anchor = GridBagConstraints.CENTER; // Centra el componente
        panel.add(titulo,constraints);
        constraints.insets = new Insets(10, 0, 10, 0); 


        // Crear botones
        JButton button1 = new JButton("Pantalla completa");
        button1.setPreferredSize(new Dimension(150, 50));
        constraints.gridy++;
        panel.add(button1, constraints);
    
        JLabel volumenLabel = new JLabel("Volumen");
        volumenLabel.setForeground(Color.WHITE);
        volumenLabel.setFont(new Font("Arial", Font.BOLD, 20));
        constraints.gridy++;
        panel.add(volumenLabel, constraints);
    
        // Crear un JSlider para ajustar el volumen
        JSlider volumenSlider = new JSlider(-50, 4, 0);
        volumenSlider.setPreferredSize(new Dimension(150, 50));
        constraints.gridy++;
        panel.add(volumenSlider, constraints);
    
        JButton button4 = new JButton("Controles");
        button4.setPreferredSize(new Dimension(150, 50));

        constraints.gridy++;
        panel.add(button4, constraints);
    
        JButton button3 = new JButton("Volver al Menu");
        button3.setPreferredSize(new Dimension(150, 50));
        constraints.gridy++;
        panel.add(button3, constraints);
    
        // Añadir action listeners a los botones
        volumenSlider.addChangeListener(e -> {
            gainControl = (float) volumenSlider.getValue();
            juego.setVolumen(gainControl);
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (device.getFullScreenWindow() != null && device.getFullScreenWindow().equals(frame)) {
                    device.setFullScreenWindow(null);
                } else {
                    frame.dispose(); // para hacer cambios visibles
                    device.setFullScreenWindow(frame);
                }
            }
        });
    
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "menu");
            }
        });
    
        return panel;
    }
    private JPanel createJuegoPanel() {
        JPanel panel = new JPanel();
    
        // Usa GridBagLayout
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
    
        // Crear etiqueta de título
        JLabel titulo = new JLabel("Probando Juego");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 200, 0); 
        constraints.anchor = GridBagConstraints.CENTER; // Centra el componente
        panel.add(titulo, constraints);
        constraints.insets = new Insets(10, 0, 10, 0); 

    
        // Crear botón
        JButton botonSalir = new JButton("Salir");
        constraints.gridy++;
        panel.add(botonSalir, constraints);
    
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "menu");
                juego.PararSonido();
            }
        });
    
        return panel;
    }
    
  


}
