package Proyecto_Programacion;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;

public class Juego extends JPanel {

    private Float volumen = 0.0f;
    private Clip cancion; // Solo un clip para manejar la reproducción de audio
    private JButton amarillo;
    private JButton azul;
    private JButton rojo;
    private JButton verde;

    public Juego(JPanel pausaPanel, JPanel configJuego) {
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
                        pausaPanel.setVisible(true);
                        PausarSonido();
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
    }

    public void setVolumen(Float gainControl) {
        volumen = gainControl;
    }

    public float getVolumen() {
        return volumen;
    }

    public void reproducir(String ruta) {
        try {
            // Detener la canción actual si está en ejecución
            if (cancion != null) {
                cancion.stop();
                cancion.close();
            }

            // Cargar el archivo de audio
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(ruta));
            cancion = AudioSystem.getClip();
            cancion.open(audioInputStream);

            FloatControl clipGainControl = (FloatControl) cancion.getControl(FloatControl.Type.MASTER_GAIN);
            if (clipGainControl != null) {
                clipGainControl.setValue(volumen);
            }
            // Iniciar la reproducción
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
