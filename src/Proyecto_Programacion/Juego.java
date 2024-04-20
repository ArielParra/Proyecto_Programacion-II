package Proyecto_Programacion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;
import javax.swing.JPanel;

public class Juego extends JPanel {

    private Float volumen = 0.0f;

    private List<Clip> clips; // Lista para almacenar múltiples Sonidos

    public Juego() {
        setLayout(null);
        clips = new ArrayList<>(); // Inicializar la lista de Sonidos
    }

    public void Iniciar() {
        reproducir("audio/startgame.wav");  
        
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