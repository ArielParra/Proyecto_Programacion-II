package Proyecto_Programacion;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class mouseadapter extends MouseAdapter
{
    private final Color colour;
    private final JButton button;
    public mouseadapter(JButton button, Color colour)
    {
        this.colour = colour;
        this.button = button;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
         button.setForeground(colour);
    }
}