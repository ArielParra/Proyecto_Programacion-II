package Proyecto_Programacion;


import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

public class Juego extends JPanel {
    public AudioInputStream audioInputStream;
    public Clip clip;
    
    public Juego() {
        setLayout(null);
    }
    public JPanel createJuegoPanel() {
        JPanel panel = new JPanel();

        //Aqui se crean los elementos del juego
        //Lo que mostrara el panel 

        //Se pensara un menu para canciones en este apartado

        return panel;
    }
    public void Iniciar(){

        //soporte a archivos wav, pero mp3 no
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("../../audio/startgame.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        System.out.println("Juego creado");
        
        clip.start();
        
    }
}
