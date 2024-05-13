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
    private String mensajeFalla = "";
    private String mensajegood = "";
    private String mensajeperfect = "";
    private Clip cancion;
    private JButton amarillo;
    public boolean enPausa = false;
    private JButton azul;
    private Thread hiloJuego;
    private JButton rojo;
    private JButton verde;
    public int score = 0;
    public int combo = 0;
    public int fails = 0;
    public List<Ficha> fichas = new ArrayList<>();

    public Juego(JPanel pausaPanel, JPanel configJuego) {
        this.volumen = 0.0f;
        // Deshabilitar el comportamiento predeterminado de las teclas traversales

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
         hiloJuego = new Thread(new Runnable() {
            @Override
            public void run() {
                cicloPrincipalJuego();
            }
        });
        hiloJuego.start();

        
    }
    public void Salirdeljuego(){
     
        score = 0;
        combo = 0;
        fails = 0;
        enPausa = false;
        mensajeFalla = "";
        

        synchronized(fichas){
            fichas.clear();
        }
        PararSonido();
           // Detener el hilo de juego
        if (hiloJuego != null && hiloJuego.isAlive()) {
        hiloJuego.interrupt();
        hiloJuego = null;
    }
    }
    public void cicloPrincipalJuego() {
        long tiempoViejo = System.nanoTime();
        long tiempoUltimaFicha = tiempoViejo;
        int aumento = 5;
        boolean perfecto = false;
        combo = 0;
        while (!Thread.currentThread().isInterrupted()) { // Aquí se comprueba si el hilo ha sido interrumpido
            try {
                if (!enPausa) {
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
                            if (ficha.y + 50 >= getHeight() - 50) {
                                if(ficha.y + 50 >= getHeight() - 10){
                                    aumento =10;
                                    perfecto = true;
                                }else{
                                    aumento = 5;
                                    perfecto = false;
                                }

                                switch (ficha.columna) {
                                    case 0:
                                        if (amarillo.getBackground() == Color.BLACK) {
                                            score += aumento;
                                            combo++;
                                            if(perfecto){
                                                mensajeperfect = "¡Perfecto!";
                                                mensajegood = "";
                                            }else{
                                                mensajegood = "¡Bien!";
                                                mensajeperfect = "";
                                            }
                                            mensajeFalla = "";
                                            iterator.remove();
                                        }
                                        break;
                                    case 1:
                                        if (azul.getBackground() == Color.BLACK) {
                                            score += aumento;
                                            combo++;
                                            if(perfecto){
                                                mensajeperfect = "¡Perfecto!";
                                                mensajegood = "";
                                            }else{
                                                mensajegood = "¡Bien!";
                                                mensajeperfect = "";
                                            }
                                            mensajeFalla = "";
                                            iterator.remove();
                                        }
                                        break;
                                    case 2:
                                        if (rojo.getBackground() == Color.BLACK) {
                                            score += aumento;
                                            combo++;
                                            if(perfecto){
                                                mensajeperfect = "¡Perfecto!";
                                                mensajegood = "";
                                            }else{
                                                mensajegood = "¡Bien!";
                                                mensajeperfect = "";
                                            }
                                            mensajeFalla = "";
                                            iterator.remove();
                                        }
                                        break;
                                    case 3:
                                        if (verde.getBackground() == Color.BLACK) {
                                            score += aumento;
                                            combo++;
                                            if(perfecto){
                                                mensajeperfect = "¡Perfecto!";
                                                mensajegood = "";
                                            }else{
                                                mensajegood = "¡Bien!";
                                                mensajeperfect = "";
                                            }
                                            mensajeFalla = "";
                                            iterator.remove();
                                        }
                                        break;
                                }
                            }
                            // Si la ficha llegó al final, eliminarla
                            if (ficha.y >= getHeight()) {
                                iterator.remove();
                                // pintar fallaste
                                combo = 0;
                                fails++;
                                mensajeFalla = "¡Fallaste!";
                            }
                        }
                    }
                    repaint();
                } else {
                    Thread.sleep(10); // Aquí se puede interrumpir el hilo, y se lanzará InterruptedException
                    long tiempoNuevo = System.nanoTime();
                    tiempoViejo = tiempoNuevo;
    
                }
    
            } catch (InterruptedException e) {
                // Manejo de la excepción
                Thread.currentThread().interrupt(); // Restablecer la bandera de interrupción
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
        
        //score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 20);
        //combo
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Combo: " + combo, 10, 40);
        //fails
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Fails: " + fails, 10, 60);

        //mensaje de bien
        g.setColor(Color.GREEN);    
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(mensajegood, getWidth()/2 - 50, getHeight()/2 - 50);
        //mensaje de perfecto
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(mensajeperfect, getWidth()/2 - 50, getHeight()/2 - 50);



        //mensaje de falla
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(mensajeFalla, getWidth()/2 - 50, getHeight()/2);

        List<Ficha> fichasCopia;
        synchronized (fichas) {
            fichasCopia = new ArrayList<>(fichas);
        }
        
        Color[] color = {Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN};
        for (Ficha ficha : fichasCopia) {
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
