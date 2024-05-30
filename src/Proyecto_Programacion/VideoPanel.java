package Proyecto_Programacion;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class VideoPanel extends JPanel {
    private String rutaVideo;
    private JFXPanel fxPanel;
    private MediaPlayer mediaPlayer;

    public VideoPanel() {
        setOpaque(false); // Hace que el panel sea transparente
        // Inicializar JFXPanel para JavaFX
        fxPanel = new JFXPanel();
        setLayout(new BorderLayout());
        add(fxPanel, BorderLayout.CENTER);
    }

    public void reproducir(String ruta) {
        this.rutaVideo = ruta;
        // Crear un nuevo objeto de archivo de medios con la ruta del video
        File file = new File(ruta);
        // Convertir la ruta del archivo a una URL
        String mediaUrl = file.toURI().toString();
        // Crear un objeto Media con la URL del archivo de video
        Media media = new Media(mediaUrl);
        // Crear un reproductor de medios con el objeto Media
        mediaPlayer = new MediaPlayer(media);
        // Crear un visor de medios y agregarlo al panel de JavaFX
        javafx.scene.media.MediaView mediaView = new javafx.scene.media.MediaView(mediaPlayer);
        Scene scene = new Scene(new javafx.scene.layout.StackPane(mediaView));
        fxPanel.setScene(scene);
        // Reproducir el video
        mediaPlayer.play();
    }
    public void detenerReproduccion() {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Detener la reproducción del video
            mediaPlayer.dispose(); // Liberar recursos del reproductor de medios
        }
    }
    public void salirDelVideo() {
        // Detener la reproducción del video
        detenerReproduccion();
        
        // Eliminar el componente de JFXPanel
        fxPanel.setScene(null);
        
        // Liberar los recursos asociados al reproductor de medios
        mediaPlayer.dispose();
    }
    public void reanudarReproduccion() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }        
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Aquí puedes agregar dibujos personalizados si 
    }
}