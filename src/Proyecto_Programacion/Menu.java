package Proyecto_Programacion;

import javax.imageio.ImageIO;
import javax.swing.*;


import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;

public class Menu extends JFrame{
    public Juego juego;
    private Font customFont; 
    // Usar JLayeredPane en lugar de JPanel
    public JLayeredPane layeredPane;
    public boolean online=false;
    public VideoPanel videoPanel;
    public JPanel menuPanel,configPanel,pausaPanel,configJuego,CancionesPanelgrabar,CancionesPanelmenu,finalscore,jugarPanel,OnlinePanel,EsperandoPanel,loadingPanel,finalscore2;
    public int cancion;
    public JLabel puntaje,puntaje2,fails,fails2;
    public Thread serverThread;
    private ObjectOutputStream outteclado;
    private ObjectInputStream intTeclado;

    public Menu(){
        // Crear la única ventana JFrame
        setTitle("Meme hero"); // Título de la ventana
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al cerrar la ventana
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza la ventana al tamaño de la pantalla

        this.puntaje = new JLabel();
        this.fails = new JLabel();
        this.puntaje2 = new JLabel();
        this.fails2 = new JLabel();

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


        // Crear un nuevo juego
        this.juego = new Juego(this);

        // Crear paneles
        this.menuPanel = createMenuPanel();
        this.configPanel = createConfigPanel();
        this.pausaPanel = pauseMenu();
        this.jugarPanel = JugarMenu();
        this.loadingPanel = pantallaDeCarga();
        this.configJuego = createConfigJuego();
        this.EsperandoPanel = createEsperandoJugador();
        this.OnlinePanel = createOnlinePanel();
        this.finalscore = createfinalscore();
        this.finalscore2 = createfinalscore2();
        this.CancionesPanelgrabar = createCancionesPanel(false);
        this.CancionesPanelmenu = createCancionesPanel(true);



        // Agregar paneles a JLayeredPane con niveles de capas
        layeredPane.add(videoPanel, JLayeredPane.DEFAULT_LAYER); 
        layeredPane.add(juego, JLayeredPane.MODAL_LAYER);
        layeredPane.add(menuPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(jugarPanel,JLayeredPane.PALETTE_LAYER);
        layeredPane.add(loadingPanel,JLayeredPane.PALETTE_LAYER);
        layeredPane.add(OnlinePanel,JLayeredPane.PALETTE_LAYER);
        layeredPane.add(EsperandoPanel,JLayeredPane.PALETTE_LAYER);
        layeredPane.add(finalscore,JLayeredPane.PALETTE_LAYER);
        layeredPane.add(finalscore2,JLayeredPane.PALETTE_LAYER);
        layeredPane.add(CancionesPanelgrabar,JLayeredPane.PALETTE_LAYER);
        layeredPane.add(CancionesPanelmenu,JLayeredPane.PALETTE_LAYER);
        layeredPane.add(pausaPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(configJuego, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(configPanel, JLayeredPane.PALETTE_LAYER);

        // Añadir layeredPane a la ventana
        add(layeredPane);
        setVisible(false);
        videoPanel.setVisible(false);
        juego.setVisible(false);
        pausaPanel.setVisible(false);
        configPanel.setVisible(false);
        configJuego.setVisible(false);
        OnlinePanel.setVisible(false);
        loadingPanel.setVisible(false);
        EsperandoPanel.setVisible(false);
        CancionesPanelgrabar.setVisible(false);
        CancionesPanelmenu.setVisible(false);
        jugarPanel.setVisible(false);
        finalscore.setVisible(false);
        finalscore2.setVisible(false);
        menuPanel.setVisible(true);
        // Mostrar la ventana
        setResizable(false);
        setVisible(true);
        
        
        resizewindow();
      

    }
    public void resizewindow(){
        Dimension newSize = getSize();
        // Ajustar el tamaño de los paneles para que coincida con el tamaño de la ventana
        videoPanel.setBounds(0, 0, newSize.width, newSize.height);
         menuPanel.setBounds(0, 0, newSize.width, newSize.height);
         finalscore.setBounds(0,0,newSize.width,newSize.height);
         finalscore2.setBounds(0,0,newSize.width,newSize.height);
         OnlinePanel.setBounds(0,0,newSize.width,newSize.height);
         pausaPanel.setBounds(0, 0, newSize.width, newSize.height);
         loadingPanel.setBounds(0, 0, newSize.width, newSize.height);
         EsperandoPanel.setBounds(0, 0, newSize.width, newSize.height);
         jugarPanel.setBounds(0, 0, newSize.width, newSize.height);
         configPanel.setBounds(0, 0, newSize.width, newSize.height);
         CancionesPanelgrabar.setBounds(0,0, newSize.width,newSize.height);
         CancionesPanelmenu.setBounds(0,0,newSize.width,newSize.height);
         configJuego.setBounds(0, 0, newSize.width, newSize.height);
         juego.setBounds(0, 0, newSize.width, newSize.height);
          
    }
    private JPanel createEsperandoJugador(){
        JPanel panel = new JPanel(){
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
        // Usa GridBagLayout
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Crear etiqueta de título
        JLabel titulo = new JLabel("Esperando Jugador");
        titulo.setFont(customFont.deriveFont(35f));
        titulo.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 100, 0); 
        constraints.anchor = GridBagConstraints.CENTER; // Centra el componente
        panel.add(titulo,constraints);
        constraints.insets = new Insets(10, 0, 10, 0); 
    
        JButton button1 = createbutton("Volver");
        constraints.gridy++;
        panel.add(button1, constraints);
    
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverThread = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        
                    }
                });
                EsperandoPanel.setVisible(false);
                online = false;
                menuPanel.setVisible(true);
                jugarPanel.setVisible(false);
            }
        });
        return panel;
    }
    private JPanel createOnlinePanel(){
        JPanel panel = new JPanel(){
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
        // Usa GridBagLayout
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Crear etiqueta de título
        JLabel titulo = new JLabel("Online");
        titulo.setFont(customFont.deriveFont(35f));
        titulo.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 100, 0); 
        constraints.anchor = GridBagConstraints.CENTER; // Centra el componente
        panel.add(titulo,constraints);
        constraints.insets = new Insets(10, 0, 10, 0); 

        JButton button1 = createbutton("Crear Partida");
        constraints.gridy++;
        panel.add(button1, constraints);

        JButton button2 = createbutton("Unirse a Partida");
        constraints.gridy++;
        panel.add(button2, constraints);

        JButton button3 = createbutton("Volver");
        constraints.gridy++;
        panel.add(button3, constraints);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                juego.setMultiplayer(true);
                panel.setVisible(false);
                online = true;
 
                jugarPanel.setVisible(false);
                CancionesPanelmenu.setVisible(true);
            }   
        });
        button2.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                juego.setOpaque(false);
                panel.setVisible(false);
                pausaPanel.setVisible(false);
                CancionesPanelmenu.setVisible(false);
                CancionesPanelgrabar.setVisible(false);
                configPanel.setVisible(false);
                configJuego.setVisible(false);
                juego.setMultiplayer(true);
                online = true;
                jugarPanel.setVisible(false);
                panel.setVisible(false);
                Thread clientThread = new Thread(new Runnable(){
                   @Override
                    public void run(){
                        try {
                            System.out.println("Conectando a servidor...");
                            Socket clientSocket = new Socket("localhost", 5000);
                            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                            cancion = in.readInt();
                            outteclado = new ObjectOutputStream(clientSocket.getOutputStream());
                            intTeclado = new ObjectInputStream(clientSocket.getInputStream());
                            try{
                                loadingPanel.setVisible(true);
                                juego.Iniciar(cancion);
                            }catch(IOException e1){
                                e1.printStackTrace();
                            }   
                            while (true) {
                                try {
                                    TecladoEvento evento = (TecladoEvento) intTeclado.readObject();          
                                    juego.nuevoEvento(evento);
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                clientThread.start();
            }   
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                menuPanel.setVisible(true);
                jugarPanel.setVisible(false);
                panel.setVisible(false);
            }
        });
        return panel;
    }
    private JPanel pantallaDeCarga(){
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image backgroundImageConfig = ImageIO.read(new File("images/loading.png"));
                    g.drawImage(backgroundImageConfig, 0, 0, getWidth(), getHeight(), this);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    setBackground(Color.LIGHT_GRAY);
                }
            }
        };
      
        return panel;
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
                jugarPanel.setVisible(true);
                menuPanel.setVisible(false);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configPanel.setVisible(true);
                menuPanel.setVisible(false);
            }
        });
    
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                juego.setMultiplayer(false);
                CancionesPanelgrabar.setVisible(true);
                menuPanel.setVisible(false);
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
    private JPanel JugarMenu(){
        JPanel panel = new JPanel(){
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
        // Usa GridBagLayout
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Crear etiqueta de título
        JLabel titulo = new JLabel("Jugar");
        titulo.setFont(customFont.deriveFont(35f));
        titulo.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 100, 0); 
        constraints.anchor = GridBagConstraints.CENTER; // Centra el componente
        panel.add(titulo,constraints);
        constraints.insets = new Insets(10, 0, 10, 0); 

        JButton button1 = createbutton("1 Jugador");
        constraints.gridy++;
        panel.add(button1, constraints);

        JButton button2 = createbutton("2 Jugadores");
        constraints.gridy++;
        panel.add(button2, constraints);

        JButton button3 = createbutton("Online");
        constraints.gridy++;
        panel.add(button3, constraints);

        JButton button4 = createbutton("Volver");
        constraints.gridy++;
        panel.add(button4, constraints);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                juego.setMultiplayer(false);
                jugarPanel.setVisible(false);
                CancionesPanelmenu.setVisible(true);
            }   
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                juego.setMultiplayer(true);
                jugarPanel.setVisible(false);
                CancionesPanelmenu.setVisible(true);
            }   
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugarPanel.setVisible(false);
                menuPanel.setVisible(false);
                OnlinePanel.setVisible(true);
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuPanel.setVisible(true);
                jugarPanel.setVisible(false);
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
                if(!online){
                juego.setPausa(false);
                videoPanel.reanudarReproduccion();
                juego.setVisible(true);
                panel.setVisible(false);
                juego.setFocusable(true);
                juego.requestFocusInWindow(); 
                juego.repaint();
                }else{
                    //Nuevo evento tecla P
                    juego.nuevoEvento(new TecladoEvento(KeyEvent.VK_P, 'P', true));
                } 
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
                panel.setVisible(false);
                    if(juego.getGrabando()){
                        juego.running = false;
                        videoPanel.salirDelVideo();
                    
                    }else{
                        juego.running = false;
                        videoPanel.salirDelVideo();
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
    
        JSlider volumenSlider = new JSlider(0, 100, 50);
        volumenSlider.setPreferredSize(new Dimension(150, 50));
        constraints.gridy++;
        panel.add(volumenSlider, constraints);
    
        JButton button3 = createbutton("Volver");
        
        constraints.gridy++;
        panel.add(button3, constraints);
    
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                volumenSlider.setValue((int) (videoPanel.getVolumen()*100));
            }
        });
        volumenSlider.addChangeListener(e -> {
            videoPanel.setVolumen((double)volumenSlider.getValue()/100);
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
    
        JSlider volumenSlider = new JSlider(0,100, 50);
        volumenSlider.setPreferredSize(new Dimension(150, 50));
        constraints.gridy++;
        panel.add(volumenSlider, constraints);

        JButton button3 = createbutton("Volver al Menu");
        
        constraints.gridy++;
        panel.add(button3, constraints);
        
      

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                volumenSlider.setValue((int) (videoPanel.getVolumen()*100));
            }
        });
        volumenSlider.addChangeListener(e -> {
            videoPanel.setVolumen((double)volumenSlider.getValue()/100);
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

        JButton cancion1 = createbutton("Fung Fu Fighting - Carl Douglas");
        
        constraints.gridy++;
        panel.add(cancion1,constraints);

        JButton cancion2 = createbutton("Losing My Religion - R.E.M.");

        constraints.gridy++;
        panel.add(cancion2,constraints);
        
        JButton cancion3 = createbutton("Toxicity - System of a down");

        constraints.gridy++;
        panel.add(cancion3,constraints);

        JButton cancion4 = createbutton("Take Me Out - Franz Ferdinand");

        constraints.gridy++;
        panel.add(cancion4,constraints);
        
        JButton botonSalir = createbutton("Salir");
        constraints.gridy++;
        panel.add(botonSalir, constraints);
        
        cancion1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                createCancion(panel, 1, menu);
            }
        });
        cancion2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                createCancion(panel, 2, menu);
            }
        });
        cancion3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                createCancion(panel, 3, menu);
            }
        });
        cancion4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCancion(panel, 4, menu);
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
    public JPanel createfinalscore2(){
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

        JLabel titulo = new JLabel("Puntaje Final Jugador 1");
        titulo.setFont(customFont.deriveFont(35f));
        titulo.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER; // Centra el componente
        panel.add(titulo, constraints);
        constraints.insets = new Insets(10, 0, 10, 0);

        //Tomar el puntaje final del juego con getFinalscore
        puntaje.setFont(customFont.deriveFont(35f));
        puntaje.setForeground(Color.WHITE);
        constraints.gridy++;
        panel.add(puntaje, constraints);

        //Tomar fails del juego con getFails
        fails.setFont(customFont.deriveFont(35f));
        fails.setForeground(Color.WHITE);
        constraints.gridy++;
        panel.add(fails, constraints);

        JLabel titulo2 = new JLabel("Puntaje Final Jugador 2");
        titulo2.setFont(customFont.deriveFont(35f));
        titulo2.setForeground(Color.WHITE);
        constraints.gridy++;
        panel.add(titulo2, constraints);
    
        //Tomar el puntaje final del juego con getFinalscore
        puntaje2.setFont(customFont.deriveFont(35f));
        puntaje2.setForeground(Color.WHITE);
        constraints.gridy++;
        panel.add(puntaje2, constraints);
    
        //Tomar fails del juego con getFails
        fails2.setFont(customFont.deriveFont(35f));
        fails2.setForeground(Color.WHITE);
        constraints.gridy++;
        panel.add(fails2, constraints);
        JButton botonSalir = createbutton("Salir");
        constraints.gridy++;
        panel.add(botonSalir, constraints);
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuPanel.setVisible(true);
                panel.setVisible(false);
            }
        });
      
        return panel;
    }
    public JPanel createfinalscore(){
      
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
        JLabel titulo = new JLabel("Puntaje Final");
        titulo.setFont(customFont.deriveFont(35f));
        titulo.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 100, 0);
        constraints.anchor = GridBagConstraints.CENTER; // Centra el componente
        panel.add(titulo, constraints);
        constraints.insets = new Insets(10, 0, 10, 0);

        //Tomar el puntaje final del juego con getFinalscore
        puntaje.setFont(customFont.deriveFont(35f));
        puntaje.setForeground(Color.WHITE);
        constraints.gridy++;
        panel.add(puntaje, constraints);

        //Tomar fails del juego con getFails
        fails.setFont(customFont.deriveFont(35f));
        fails.setForeground(Color.WHITE);
        constraints.gridy++;
        panel.add(fails, constraints);
        JButton botonSalir = createbutton("Salir");
        constraints.gridy++;
        panel.add(botonSalir, constraints);
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuPanel.setVisible(true);
                panel.setVisible(false);
            }
        });

      
        return panel;
    
    }
    private void createCancion(JPanel panel, int cancionglobal, boolean menu){
                juego.setOpaque(false);
                panel.setVisible(false);
                pausaPanel.setVisible(false);
                CancionesPanelmenu.setVisible(false);
                CancionesPanelgrabar.setVisible(false);
                configPanel.setVisible(false);
                configJuego.setVisible(false);
                EsperandoPanel.setVisible(true);

                if(online){
                    serverThread = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            try {
                                System.out.println("Esperando Jugador...");
                                cancion = cancionglobal;
                                panel.setVisible(false);
                                ServerSocket serverSocket = new ServerSocket(5000);
                                Socket clientSocket = serverSocket.accept();
                                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                                out.writeInt(cancionglobal);
                                out.flush();
                                outteclado = new ObjectOutputStream(clientSocket.getOutputStream());
                                intTeclado = new ObjectInputStream(clientSocket.getInputStream());
                                try{
                                    loadingPanel.setVisible(true);
                                    juego.Iniciar(cancionglobal);

                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                    while (true) {
                                        try {
                                            TecladoEvento evento = (TecladoEvento) intTeclado.readObject();
                                            juego.nuevoEvento(evento);
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (StreamCorruptedException e) {
                                            System.err.println("Datos corruptos en el flujo: " + e.getMessage());
                                            break; // Rompe el bucle si los datos están corruptos
                                        } catch (EOFException e) {
                                            System.err.println("Fin del flujo de datos: " + e.getMessage());
                                            break; // Rompe el bucle si se alcanza el fin del flujo
                                        } catch (IOException e) {
                                            System.err.println("Error de I/O: " + e.getMessage());
                                            break; // Rompe el bucle si ocurre un error de I/O
                                        }
                                    }
                                
                                
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            
                        }
                    });
                    serverThread.start();
                }else{
                if(!menu) {
                    try {
                        loadingPanel.setVisible(true);
                        juego.IniciarGrabacion(cancionglobal);

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else{
                        try {
                            loadingPanel.setVisible(true);
                            juego.Iniciar(cancionglobal);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                }
            }
    }
    public void enviarEvento(KeyEvent e) {
        if(outteclado != null){
            TecladoEvento eventoTeclado = new TecladoEvento(e.getKeyCode(), e.getKeyChar(), e.getID() == KeyEvent.KEY_PRESSED);
            try {
                outteclado.writeObject(eventoTeclado);
                outteclado.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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
