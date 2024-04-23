package Proyecto_Programacion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;

public class Juego extends JPanel {

    private Float volumen = 0.0f;
    private StringBuilder text = new StringBuilder(); // Variable para almacenar letras ingresadas
    private List<Clip> clips; // Lista para almacenar múltiples Sonidos
    private JButton amarillo;
    private JButton azul;
    private JButton rojo;
    private JButton verde;

    public Juego() {
        // Añadir KeyListener para escuchar eventos de teclado
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Capturar la letra ingresada y agregarla a la variable `text`
                text.append(e.getKeyChar());
                // Volver a pintar el panel
                repaint();
                switch(e.getKeyChar()){
                    case 'a':
                        amarillo.setBackground(Color.BLACK);
                        break;
                    case 's':
                        azul.setBackground(Color.BLACK);
                        break;
                    case 'd':
                        rojo.setBackground(Color.BLACK);
                        break;
                    case 'f':
                        verde.setBackground(Color.BLACK);
                        break;
                    default:
                        // Handle the default case here
                        break;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                switch(e.getKeyChar()){
                    case 'a':
                        amarillo.setBackground(Color.YELLOW);
                        break;
                    case 's':
                        azul.setBackground(Color.BLUE);
                        break;
                    case 'd':
                        rojo.setBackground(Color.RED);
                        break;
                    case 'f':
                        verde.setBackground(Color.GREEN);
                        break;
                    default:
                        // Handle the default case here
                        break;
                }
            }
        });
        setFocusable(true); // Hacer que el panel sea enfocable para capturar eventos de teclado
        clips = new ArrayList<>(); // Inicializar la lista de Sonidos
    }

    public void Iniciar() {
        pintarbase();
        reproducir("audio/startgame.wav");    
    }
    public void pintarbase(){
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.anchor = GridBagConstraints.SOUTH;
        
        //4 button base
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
        g.setColor(Color.BLACK);
        g.drawString(text.toString(), 10, 20); 
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
}