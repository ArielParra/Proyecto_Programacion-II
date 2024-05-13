package Proyecto_Programacion;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Menu {
    private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    private JFrame frame; // Ventana
    public Juego juego;
    public Float gainControl = 0.0f;
    
    // Usar JLayeredPane en lugar de JPanel
    public JLayeredPane layeredPane;
    public JPanel menuPanel,configPanel,pausaPanel,configJuego;

    public Menu() {
        // Crear la única ventana JFrame
        this.frame = new JFrame("Guitar Hero");
        this.frame.setMinimumSize(new Dimension(800, 600));
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un JLayeredPane para administrar la superposición de paneles
        this.layeredPane = new JLayeredPane();
        this.layeredPane.setPreferredSize(new Dimension(800, 600));
        this.layeredPane.setLayout(null);

        // Crear paneles
        this.menuPanel = createMenuPanel();
        this.configPanel = createConfigPanel();
        this.pausaPanel = pauseMenu();
        this.configJuego = createConfigJuego();

        // Crear un nuevo juego
        this.juego = new Juego(pausaPanel,configJuego);

        // Agregar paneles a JLayeredPane con niveles de capas
        layeredPane.add(juego, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(menuPanel, JLayeredPane.MODAL_LAYER);
        layeredPane.add(pausaPanel, JLayeredPane.MODAL_LAYER);
        layeredPane.add(configJuego, JLayeredPane.MODAL_LAYER);
        layeredPane.add(configPanel, JLayeredPane.PALETTE_LAYER);
        
        resizewindow();
      

        // Añadir layeredPane a la ventana
        frame.add(layeredPane);

        // Mostrar la ventana
        frame.pack();
        frame.setVisible(true);
    }
    public void resizewindow(){
        Dimension newSize = frame.getSize();
        // Ajustar el tamaño de los paneles para que coincida con el tamaño de la ventana
         menuPanel.setBounds(0, 0, newSize.width, newSize.height);
         pausaPanel.setBounds(0, 0, newSize.width, newSize.height);
         configPanel.setBounds(0, 0, newSize.width, newSize.height);
         configJuego.setBounds(0, 0, newSize.width, newSize.height);
         juego.setBounds(0, 0, newSize.width, newSize.height);
          
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
    
        
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                panel.setVisible(false);
                juego.setVisible(true);
                pausaPanel.setVisible(false);
                configPanel.setVisible(false);
                configJuego.setVisible(false);
                juego.setFocusable(true);
                juego.requestFocusInWindow();
                juego.Iniciar();

            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configPanel.setVisible(true);
                configJuego.setVisible(false);
                panel.setVisible(false);
                pausaPanel.setVisible(false);
                juego.setVisible(false);
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
    private JPanel pauseMenu() {
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
               
            }
        };
        
        panel.setOpaque(false);
        // Usa GridBagLayout
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Crear etiqueta de título
        JLabel titulo = new JLabel("Pausa");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 100, 0); 
        constraints.anchor = GridBagConstraints.CENTER; // Centra el componente
        panel.add(titulo,constraints);
        constraints.insets = new Insets(10, 0, 10, 0); 

        JButton button1 = new JButton("Continuar");
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
                juego.ReanudarSonido();
                juego.enPausa = false;
                juego.setVisible(true);
                panel.setVisible(false);
                configJuego.setVisible(false);
                juego.setFocusable(true);
                juego.requestFocusInWindow(); 
                juego.repaint(); 
            }
        });
    
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configJuego.setVisible(true);
                panel.setVisible(false);
                juego.setVisible(true);
            }
        });
    
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuPanel.setVisible(true);
                panel.setVisible(false);
                juego.setVisible(false);
                juego.Salirdeljuego();
                juego.repaint();
            }
        });
    
        return panel;
    }
    private JPanel createConfigJuego(){
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
               
            }
        };
        // Usa GridBagLayout
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Crear etiqueta de título
        JLabel titulo = new JLabel("Configuracion");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.BLACK);
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
        volumenLabel.setForeground(Color.BLACK);
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
    
        JButton button3 = new JButton("Volver");
        button3.setPreferredSize(new Dimension(150, 50));
        constraints.gridy++;
        panel.add(button3, constraints);
    
        // Añadir action listeners a los botones
        volumenSlider.addChangeListener(e -> {
            gainControl = (float) volumenSlider.getValue();
            juego.setVolumen(gainControl);
            
        });
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                volumenSlider.setValue((int) juego.getVolumen());
            }
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
                resizewindow();
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pausaPanel.setVisible(true);
                panel.setVisible(false);
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
        
      

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                volumenSlider.setValue((int) juego.getVolumen());
            }
        });
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
                resizewindow();
            }
        });
    
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               menuPanel.setVisible(true);
                panel.setVisible(false);
                juego.setVisible(false);
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
                menuPanel.setVisible(true);
                juego.PararSonido();
            }
        });
    
        return panel;
    }
    
  


}
