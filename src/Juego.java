import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Juego {
    public AudioInputStream audioInputStream;
    public Clip clip;
    
    public Juego() {


        //soporte a archivos wav, pero mp3 no
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("../audio/startgame.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        System.out.println("Juego creado");
        clip.start();

    }
}
