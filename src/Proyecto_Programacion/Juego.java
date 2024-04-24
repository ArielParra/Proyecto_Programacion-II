package Proyecto_Programacion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Collections;

public class Juego extends JPanel {

    private Float volumen = 0.0f;
    private List<Clip> clips; 
    private JButton amarillo;
    private JButton azul;
    private JButton rojo;
    private JButton verde;


    public Juego(JPanel pausaPanel, JPanel configJuego) {
        // Deshabilita el comportamiento predeterminado de la tecla Tab
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
        setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.emptySet());

        pintarbase();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
              
                switch(e.getKeyCode()){
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
                        pausaPanel.setVisible(true);
                        setVisible(true);
                        PausarSonido();
                        setFocusable(false);
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
        setFocusable(true); // Hacer que el panel sea enfocable para capturar eventos de teclado
        clips = new ArrayList<>(); // Inicializar la lista de Sonidos
    }

    public void Iniciar() {
        PararSonido();
        reproducir("audio/startgame.wav");    
    }
    public void pintarbase(){
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
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
    }

    public void setVolumen(Float gainControl) {
        volumen = gainControl;
    }
    public void reproducir(String ruta) {
        try {
            // Cargar el archivo de audio
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(ruta));
            
            // Crear un nuevo Clip
            Clip nuevoClip = AudioSystem.getClip();
            nuevoClip.open(audioInputStream);
            
            // Configurar el volumen
            FloatControl gainControl = (FloatControl) nuevoClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volumen);
            
            // Iniciar la reproducción
            nuevoClip.start();
            
            // Agregar el Clip a la lista
            clips.add(nuevoClip);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    
    }
    public void PararSonido() {
        // Detener todos los Clips en la lista
        for (Clip clip : clips) {
            if (clip != null && clip.isRunning()) {
                clip.stop(); // Detener el Clip
            }
        }
        // Limpia la lista después de detener los clips si ya no los necesitas
        clips.clear();
    }
    public void PausarSonido() {
        // Pausar todos los Clips en la lista
        for (Clip clip : clips) {
            if (clip != null && clip.isRunning()) {
                clip.stop(); // Pausar el Clip
            }
        }
    }
    public void ReanudarSonido() {
        // Reanudar todos los Clips en la lista
        for (Clip clip : clips) {
            if (clip != null && !clip.isRunning()) {
                clip.start(); // Reanudar el Clip
            }
        }
    }
}