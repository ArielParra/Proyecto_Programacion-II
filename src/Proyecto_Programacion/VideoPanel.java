package Proyecto_Programacion;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class VideoPanel extends JPanel {
    private String rutaVideo;
    private JFXPanel fxPanel;
    public MediaPlayer mediaPlayer;
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
        MediaView mediaView = new MediaView(mediaPlayer);
        Scene scene = new Scene(new javafx.scene.layout.StackPane(mediaView));
        fxPanel.setScene(scene);

        mediaView.setPreserveRatio(true);
        mediaView.fitWidthProperty().bind(scene.widthProperty());
        mediaView.fitHeightProperty().bind(scene.heightProperty());
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                if (videoReadyListener != null) {
                    videoReadyListener.onReady();
                }
            }
        });
        
        mediaPlayer.play();
        mediaPlayer.setVolume(volumen);
      

    }
    private VideoReadyListener videoReadyListener;
    
    public interface VideoReadyListener {
        void onReady();
    }

    public void setOnVideoReadyListener(VideoReadyListener listener) {
        this.videoReadyListener = listener;
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
        }
    }

    public void salirDelVideo() {
        detenerReproduccion();
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }
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
    public boolean isSTOPPED(){
        // Devuelve true si el video se está reproduciendo
        return mediaPlayer.getStatus() == MediaPlayer.Status.STOPPED;
    }
    public boolean isRunning(){
        // Devuelve true si el video se está reproduciendo
        return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }
    public void setVideo(double segundos) {
        if (mediaPlayer != null) {
            javafx.util.Duration nuevaDuracion = javafx.util.Duration.seconds(segundos);
            if (nuevaDuracion.lessThanOrEqualTo(mediaPlayer.getTotalDuration()) && nuevaDuracion.greaterThanOrEqualTo(javafx.util.Duration.ZERO)) {
                mediaPlayer.seek(nuevaDuracion);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
