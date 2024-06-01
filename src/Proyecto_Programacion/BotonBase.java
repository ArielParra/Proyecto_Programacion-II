package Proyecto_Programacion;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BotonBase extends JButton {
    private BufferedImage image;
    private boolean presionado = false;
    public BotonBase(BufferedImage image) {
        this.image = image;
        setPreferredSize(new Dimension(Juego.fichawidth, Juego.fichaheight)); 
        setContentAreaFilled(false);
        setBorderPainted(false);
        
    }
    public void setPresionado(boolean presionado, BufferedImage image) {
        this.presionado = presionado;
        this.image = image;
        repaint();
    }
    public boolean getPresionado() {
        return presionado;
    }
    public void setEliminando(boolean eliminando, BufferedImage image) {
        this.presionado = eliminando;
        this.image = image;
        repaint();
    }
    public boolean getEliminando() {
        return presionado;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, 80,50, this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(80, 50);
    }
}
