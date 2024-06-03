package Proyecto_Programacion;

import javax.imageio.ImageIO;
import javax.swing.*;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;

public class Menu extends JFrame{
    private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    private JFrame frame; // Ventana
    public Juego juego;
    public Float gainControl = 0.0f;
    private Font customFont; 
    // Usar JLayeredPane en lugar de JPanel
    public JLayeredPane layeredPane;
    public VideoPanel videoPanel;
    public JPanel menuPanel,configPanel,pausaPanel,configJuego,CancionesPanelgrabar,CancionesPanelmenu;

    public Menu(){
        // Crear la única ventana JFrame
        this.frame = new JFrame("Meme Hero");
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al cerrar la ventana
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza la ventana al tamaño de la pantalla

        try {
            this.customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Runtoe.ttf")).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // Crear un JLayeredPane para administrar la superposición de paneles
        this.layeredPane = new JLayeredPane();
        this.layeredPane.setPreferredSize(new Dimension(1000, 600));
        this.layeredPane.setLayout(null);

        this.videoPanel = new VideoPanel(); // Crea una instancia de VideoPanel

        // Crear paneles
        this.menuPanel = createMenuPanel();
        this.configPanel = createConfigPanel();
        this.pausaPanel = pauseMenu();
        this.configJuego = createConfigJuego();
        this.CancionesPanelgrabar = createCancionesPanel(false);
        this.CancionesPanelmenu = createCancionesPanel(true);


        // Crear un nuevo juego
        this.juego = new Juego(pausaPanel,configJuego,videoPanel);

        // Agregar paneles a JLayeredPane con niveles de capas
        layeredPane.add(videoPanel, JLayeredPane.DEFAULT_LAYER); // Agrega el VideoPanel al JLayeredPane
        layeredPane.add(juego, JLayeredPane.MODAL_LAYER);
        layeredPane.add(menuPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(CancionesPanelgrabar,JLayeredPane.PALETTE_LAYER);
        layeredPane.add(CancionesPanelmenu,JLayeredPane.PALETTE_LAYER);
        layeredPane.add(pausaPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(configJuego, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(configPanel, JLayeredPane.PALETTE_LAYER);

        // Añadir layeredPane a la ventana
        frame.add(layeredPane);
        juego.setVisible(false);
        videoPanel.setVisible(false);
        menuPanel.setVisible(true);
        // Mostrar la ventana
        frame.setResizable(false);
        frame.setVisible(true);
        
        
        resizewindow();
      

    }
    public void resizewindow(){
        Dimension newSize = frame.getSize();
        // Ajustar el tamaño de los paneles para que coincida con el tamaño de la ventana
        videoPanel.setBounds(0, 0, newSize.width, newSize.height);
         menuPanel.setBounds(0, 0, newSize.width, newSize.height);
         pausaPanel.setBounds(0, 0, newSize.width, newSize.height);
         configPanel.setBounds(0, 0, newSize.width, newSize.height);
         CancionesPanelgrabar.setBounds(0,0, newSize.width,newSize.height);
         CancionesPanelmenu.setBounds(0,0,newSize.width,newSize.height);
         configJuego.setBounds(0, 0, newSize.width, newSize.height);
         juego.setBounds(0, 0, newSize.width, newSize.height);
          
    }
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image backgroundImageConfig = ImageIO.read(new File("images/fondo.png"));
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
        titulo.setFont(customFont.deriveFont(35f));
        titulo.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 200, 0); 
        panel.add(titulo,constraints);
        constraints.insets = new Insets(10, 0, 10, 0); // Márgenes
        // Crear botones
        JButton button1 = createbutton("Jugar");
        constraints.gridy++;
        panel.add(button1, constraints);
        
        JButton button4 = createbutton("Grabar");
        
        constraints.gridy++;
        panel.add(button4,constraints);
    
        JButton button2 = createbutton("Configuracion");
        
        constraints.gridy++;
        panel.add(button2, constraints);
    
        JButton button3 = createbutton("Salir");
        
        constraints.gridy++;
        panel.add(button3, constraints);
    
        
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                CancionesPanelmenu.setVisible(true);
                CancionesPanelgrabar.setVisible(false);
                panel.setVisible(false);
                pausaPanel.setVisible(false);
                configPanel.setVisible(false);
                configJuego.setVisible(false);
                
         

            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configPanel.setVisible(true);
                configJuego.setVisible(false);
                pausaPanel.setVisible(false);
                CancionesPanelgrabar.setVisible(false);
                CancionesPanelmenu.setVisible(false);
                panel.setVisible(false);
                
            }
        });
    
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                CancionesPanelgrabar.setVisible(true);
                pausaPanel.setVisible(false);
                configPanel.setVisible(false);
                configJuego.setVisible(false);
                panel.setVisible(false);
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
        titulo.setFont(customFont.deriveFont(35f));
        titulo.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 100, 0); 
        constraints.anchor = GridBagConstraints.CENTER; // Centra el componente
        panel.add(titulo,constraints);
        constraints.insets = new Insets(10, 0, 10, 0); 

        JButton button1 = createbutton("Continuar");
        
        constraints.gridy++;
        panel.add(button1, constraints);
    
        JButton button2 = createbutton("Configuracion");
        
        constraints.gridy++;
        panel.add(button2, constraints);
    
        JButton button3 = createbutton("Salir");
        
        constraints.gridy++;
        panel.add(button3, constraints);
    
        // Añadir action listeners a los botones
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                juego.setPausa(false);
                videoPanel.reanudarReproduccion();
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
                CancionesPanelgrabar.setVisible(false);
                CancionesPanelmenu.setVisible(false);
                panel.setVisible(false);
                juego.setVisible(false);
                try {
                    if(juego.getGrabando()){
                        juego.Salirdeljuego(true);
                    }else{
                        juego.Salirdeljuego(false);
                        videoPanel.salirDelVideo();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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
        titulo.setFont(customFont.deriveFont(35f));
        titulo.setForeground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 100, 0); 
        constraints.anchor = GridBagConstraints.CENTER; // Centra el componente
        panel.add(titulo,constraints);
        constraints.insets = new Insets(10, 0, 10, 0);  

        JLabel volumenLabel = new JLabel("Volumen");
        volumenLabel.setForeground(Color.BLACK);
        volumenLabel.setFont(new Font("Arial", Font.BOLD, 20));
        constraints.gridy++;
        panel.add(volumenLabel, constraints);
    
        JSlider volumenSlider = new JSlider(-50, 4, 0);
        volumenSlider.setPreferredSize(new Dimension(150, 50));
        constraints.gridy++;
        panel.add(volumenSlider, constraints);
    
        JButton button4 = createbutton("Controles");
        

        constraints.gridy++;
        panel.add(button4, constraints);
    
        JButton button3 = createbutton("Volver");
        
        constraints.gridy++;
        panel.add(button3, constraints);
    
        volumenSlider.addChangeListener(e -> {
            gainControl = (float) volumenSlider.getValue();
            videoPanel.setVolumen((double)gainControl);
            
        });
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                volumenSlider.setValue((int) videoPanel.getVolumen());
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
                    Image backgroundImageConfig = ImageIO.read(new File("images/fondoconfig.png"));
                    g.drawImage(backgroundImageConfig, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    e.printStackTrace();
                    setBackground(Color.BLUE);
                }
            }
        };
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        JLabel titulo = new JLabel("Configuracion");
        titulo.setFont(customFont.deriveFont(35f));
        titulo.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 100, 0); 
        constraints.anchor = GridBagConstraints.CENTER; // Centra el componente
        panel.add(titulo,constraints);
        constraints.insets = new Insets(10, 0, 10, 0); 
    
        JLabel volumenLabel = new JLabel("Volumen");
        volumenLabel.setForeground(Color.WHITE);
        volumenLabel.setFont(new Font("Arial", Font.BOLD, 20));
        constraints.gridy++;
        panel.add(volumenLabel, constraints);
    
        JSlider volumenSlider = new JSlider(-50, 4, 0);
        volumenSlider.setPreferredSize(new Dimension(150, 50));
        constraints.gridy++;
        panel.add(volumenSlider, constraints);
    
        JButton button4 = createbutton("Controles");

        constraints.gridy++;
        panel.add(button4, constraints);
    

        JButton button3 = createbutton("Volver al Menu");
        
        constraints.gridy++;
        panel.add(button3, constraints);
        
      

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                volumenSlider.setValue((int) videoPanel.getVolumen());
            }
        });
        volumenSlider.addChangeListener(e -> {
            gainControl = (float) volumenSlider.getValue();
            videoPanel.setVolumen((double)gainControl);
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
    private JPanel createCancionesPanel(boolean menu) {
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image backgroundImageConfig = ImageIO.read(new File("images/fondocanciones.png"));
                    g.drawImage(backgroundImageConfig, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    e.printStackTrace();
                    setBackground(Color.LIGHT_GRAY);
                }
            }
        };
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
    
        JLabel titulo = new JLabel("Canciones");
        titulo.setFont(customFont.deriveFont(35f));
        titulo.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 100, 0); 
        constraints.anchor = GridBagConstraints.CENTER; // Centra el componente
        panel.add(titulo, constraints);
        constraints.insets = new Insets(10, 0, 10, 0); 

        JButton cancion1 = createbutton("Everlong - Foo Fighters");
        
        constraints.gridy++;
        panel.add(cancion1,constraints);

        JButton cancion2 = createbutton("One - Metallica");

        constraints.gridy++;
        panel.add(cancion2,constraints);
        
        JButton cancion3 = createbutton("Cancion 3");

        constraints.gridy++;
        panel.add(cancion3,constraints);
        
        JButton botonSalir = createbutton("Salir");
        constraints.gridy++;
        panel.add(botonSalir, constraints);
        
        cancion1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                juego.setOpaque(false);
                panel.setVisible(false);
                pausaPanel.setVisible(false);
                CancionesPanelmenu.setVisible(false);
                CancionesPanelgrabar.setVisible(false);
                configPanel.setVisible(false);
                configJuego.setVisible(false);
                
                if(!menu) {
                    try {
                        juego.setVisible(true);
                        juego.IniciarGrabacion(1);
                        juego.setFocusable(true);
                        juego.requestFocusInWindow();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else{
                    try {
                        juego.setVisible(true);
                        juego.Iniciar(1);
                        juego.setFocusable(true);
                        juego.requestFocusInWindow();
  
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        cancion2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                juego.setOpaque(false);
                panel.setVisible(false);
                pausaPanel.setVisible(false);
                CancionesPanelmenu.setVisible(false);
                CancionesPanelgrabar.setVisible(false);
                configPanel.setVisible(false);
                configJuego.setVisible(false);
                
                if(!menu) {
                    try {
                        juego.setVisible(true);
                        juego.IniciarGrabacion(2);
                        juego.setFocusable(true);
                        juego.requestFocusInWindow();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else{
                    try {
                        juego.setVisible(true);
                        juego.Iniciar(2);
                        juego.setFocusable(true);
                        juego.requestFocusInWindow();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuPanel.setVisible(true);
                CancionesPanelgrabar.setVisible(false);
                CancionesPanelmenu.setVisible(false);
            }
        });
    
        return panel;
    }
    private JButton createbutton(String text){
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
      //  button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(customFont.deriveFont(35f));
        FontMetrics fm = button.getFontMetrics(button.getFont());
        int width = fm.stringWidth(text) + 180; // Ajuste adicional para el margen
        int height = fm.getHeight(); // Ajuste adicional para el margen
        button.setPreferredSize(new Dimension(width, height));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.RED);
               // button.setOpaque(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.WHITE); 
               // button.setOpaque(false);
            }
    
            public void mouseClicked(MouseEvent e) {
                button.setForeground(Color.YELLOW); 
               // button.setOpaque(true);
            }
        });
        return button;
    }
    
}
