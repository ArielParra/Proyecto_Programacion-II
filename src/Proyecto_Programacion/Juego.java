package Proyecto_Programacion;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class Juego extends JPanel {

    private Float volumen;
    private Clip cancion; // Solo un clip para manejar la reproducción de audio
    private JButton amarillo;
    public boolean enPausa = false;
    private JButton azul;
    private JButton rojo;
    private JButton verde;
    public List<Ficha> fichas = new ArrayList<>();

    public Juego(JPanel pausaPanel, JPanel configJuego) {
        // Deshabilitar el comportamiento predeterminado de las teclas traversales
        this.volumen = 0.0f;
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
        setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.emptySet());
        pintarbase();

        // Añadir un KeyAdapter para manejar eventos de teclado
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        amarillo.setBackground(Color.BLACK);
                        break;
                    case KeyEvent.VK_S:
                        azul.setBackground(Color.BLACK);
                        break;
                    case KeyEvent.VK_D:
                        rojo.setBackground(Color.BLACK);
                        break;
                    case KeyEvent.VK_F:
                        verde.setBackground(Color.BLACK);
                        break;
                    case KeyEvent.VK_P:
                        enPausa = !enPausa;
                        
                        if (enPausa) {
                            pausaPanel.setVisible(true);
                            setFocusable(false);
                            PausarSonido();
                        } else {
                            pausaPanel.setVisible(false);
                            setFocusable(true);
                            ReanudarSonido();
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        amarillo.setBackground(Color.YELLOW);
                        break;
                    case KeyEvent.VK_S:
                        azul.setBackground(Color.BLUE);
                        break;
                    case KeyEvent.VK_D:
                        rojo.setBackground(Color.RED);
                        break;
                    case KeyEvent.VK_F:
                        verde.setBackground(Color.GREEN);
                        break;
                }
            }
        });

        setFocusable(true);
    }

    public void Iniciar() {
        PararSonido();
        reproducir("audio/cancion.wav");
        Thread hiloJuego = new Thread(new Runnable() {
            @Override
            public void run() {
                cicloPrincipalJuego();
            }
        });
        hiloJuego.start();

        
    }
    public void Salirdeljuego(){
        synchronized(fichas){
        for(Ficha ficha : fichas){
            fichas.remove(ficha);
        }
        }   
    }
    public void cicloPrincipalJuego() {
        long tiempoViejo = System.nanoTime();
        long tiempoUltimaFicha = tiempoViejo;
    
        while (true) {
            try {

                if(!enPausa) {
                    Thread.sleep(10);
                    long tiempoNuevo = System.nanoTime();
                    float dt = (tiempoNuevo - tiempoViejo) / 1_000_000_000f; 
                    tiempoViejo = tiempoNuevo;
        
                    if ((tiempoNuevo - tiempoUltimaFicha) >= 0_500_000_000f) {
                        crearFicha();
                        tiempoUltimaFicha = tiempoNuevo;
                    }
        
                    synchronized (fichas) {
                        Iterator<Ficha> iterator = fichas.iterator();
                        while (iterator.hasNext()) {
                            Ficha ficha = iterator.next();
                            ficha.fisica(dt);
                            
                            // Si la ficha llegó al final, eliminarla
                            if (ficha.y >= getHeight()) {
                                iterator.remove();
                            }
                        }
                    }
                    repaint();
                }
                else{
                    long tiempoNuevo = System.nanoTime();
                    tiempoViejo = tiempoNuevo;
                    
                }
    
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    

    
    
    public void crearFicha(){
        int columna = (int)(Math.random()*4); 
        Ficha ficha = new Ficha(columna,this);
        fichas.add(ficha);
    }

    public void pintarbase() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.anchor = GridBagConstraints.SOUTH;

        amarillo = new JButton("");
        amarillo.setBackground(Color.YELLOW);
        amarillo.setOpaque(true);
        amarillo.setBorderPainted(false);
        amarillo.setPreferredSize(new Dimension(50, 50));
        add(amarillo, c);

        azul = new JButton("");
        azul.setBackground(Color.BLUE);
        azul.setOpaque(true);
        azul.setBorderPainted(false);
        azul.setPreferredSize(new Dimension(50, 50));
        c.gridx++;
        add(azul, c);

        rojo = new JButton("");
        rojo.setBackground(Color.RED);
        rojo.setOpaque(true);
        rojo.setBorderPainted(false);
        rojo.setPreferredSize(new Dimension(50, 50));
        c.gridx++;
        add(rojo, c);

        verde = new JButton("");
        verde.setBackground(Color.GREEN);
        verde.setOpaque(true);
        verde.setBorderPainted(false);
        verde.setPreferredSize(new Dimension(50, 50));
        c.gridx++;
        add(verde, c);


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        List<Ficha> fichasCopia;
        synchronized (fichas) {
            fichasCopia = new ArrayList<>(fichas);
        }
        
        Color[] color = {Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN};
        for (Ficha ficha : fichasCopia) {
            System.out.println(fichasCopia.size());
            g.setColor(color[ficha.columna]);
            g.fillOval((int) ficha.x, (int) ficha.y, 50, 50);
        }
    }
    

    public void setVolumen(Float gainControl) {
        volumen = gainControl;
    }

    public float getVolumen() {
        return volumen;
    }

    public void reproducir(String ruta) {
        try {
            if (cancion != null) {
                cancion.stop();
                cancion.close();
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(ruta));
            cancion = AudioSystem.getClip();
            cancion.open(audioInputStream);

            FloatControl clipGainControl = (FloatControl) cancion.getControl(FloatControl.Type.MASTER_GAIN);
            if (clipGainControl != null) {
                clipGainControl.setValue(volumen);
            }
            cancion.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void PararSonido() {
        if (cancion != null) {
            cancion.stop();
            cancion.close();
            cancion = null;
        }
    }

    public void PausarSonido() {
        if (cancion != null) {
            cancion.stop();
        }
    }

    public void ReanudarSonido() {
        if (cancion != null) {
            // Reanudar el clip desde la posición actual
            Clip canciontemp;
            int posicion = cancion.getFramePosition();
            cancion.close();
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("audio/cancion.wav"));
                canciontemp = AudioSystem.getClip();
                canciontemp.open(audioInputStream);

                FloatControl clipGainControl = (FloatControl) canciontemp.getControl(FloatControl.Type.MASTER_GAIN);
                if (clipGainControl != null) {
                    clipGainControl.setValue(volumen);
                }

                canciontemp.setFramePosition(posicion);
                cancion = canciontemp;
                cancion.start();
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
    }
}
