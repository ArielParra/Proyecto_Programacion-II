package Proyecto_Programacion;
import javax.swing.border.AbstractBorder;
import java.awt.*;

public class CircularBorder extends AbstractBorder {
    private Color borderColor;

    public CircularBorder(Color borderColor) {
        this.borderColor = borderColor;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(borderColor);
        g2d.drawOval(x, y, width - 2, height - 2);
        g2d.dispose();
    }
}
