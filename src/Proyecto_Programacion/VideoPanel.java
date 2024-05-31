package Proyecto_Programacion;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class VideoPanel extends JPanel {
    private String rutaVideo;
    private JFXPanel fxPanel;
    private MediaPlayer mediaPlayer;
    private double volumen = 0.5; // Volumen inicial

    public VideoPanel() {
        setOpaque(false); 
        fxPanel = new JFXPanel();
        setLayout(new BorderLayout());
        add(fxPanel, BorderLayout.CENTER);
    }

    public void reproducir(String ruta) {
        this.rutaVideo = ruta;
        File file = new File(rutaVideo);
        String mediaUrl = file.toURI().toString();
        Media media = new Media(mediaUrl);
        mediaPlayer = new MediaPlayer(media);
        javafx.scene.media.MediaView mediaView = new javafx.scene.media.MediaView(mediaPlayer);
        Scene scene = new Scene(new javafx.scene.layout.StackPane(mediaView));
        fxPanel.setScene(scene);
        mediaPlayer.play();
        mediaPlayer.setVolume(volumen);
    }
    public void setVolumen(double volumen) {
        this.volumen = volumen;
        if(mediaPlayer != null){
        mediaPlayer.setVolume(volumen);
        }
    }
    public double getVolumen() {
        return volumen;
    }
    public void detenerReproduccion() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }
    public void salirDelVideo() {
        detenerReproduccion();
        fxPanel.setScene(null);
        mediaPlayer.dispose();
    }
    public void reanudarReproduccion() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }        
    public void pausarReproduccion() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        }
    }
    
    public void setVideo(double segundos) {
        if (mediaPlayer != null) {
        Duration nuevaDuracion = Duration.seconds(segundos);
            if (nuevaDuracion.lessThanOrEqualTo(mediaPlayer.getTotalDuration()) && nuevaDuracion.greaterThanOrEqualTo(Duration.ZERO)) {
                mediaPlayer.seek(nuevaDuracion);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}