package Proyecto_Programacion;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CircularButton extends JButton {
    private Color buttonColor;

    public CircularButton(Color buttonColor) {
        this.buttonColor = buttonColor;
        setPreferredSize(new Dimension(50, 50)); 
        setBorder(new CircularBorder(Color.BLACK)); 
        setContentAreaFilled(false);
    }
    public void setColor(Color color){
        this.buttonColor = color;
    }
    public Color getColor(){
        return this.buttonColor;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Dibujar el círculo
        int diameter = Math.min(getWidth(), getHeight());
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;
        Ellipse2D circle = new Ellipse2D.Double(x, y, diameter, diameter);
        g2d.setColor(buttonColor);
        g2d.fill(circle);

        g2d.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }
}
