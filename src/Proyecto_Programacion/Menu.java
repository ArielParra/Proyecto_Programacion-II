package Proyecto_Programacion;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;


public class Menu {
    private Ventana ventana;
    private Configuracion configuracion;

    public Menu(Ventana ventana) {
        this.ventana = ventana;
        this.configuracion = new Configuracion(ventana, this); 
    }

    public void imprimirMenu() {
        ventana.setBackground(new File("img/fondoMenu.png"));

        JLabel titleLabel = new JLabel("Mi Juego", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);
        ventana.add(titleLabel, BorderLayout.NORTH);

        JButton jugarButton = new JButton("Jugar");
        JButton editorButton = new JButton("Editor");
        JButton configuracionButton = new JButton("Configuración");
        JButton salirButton = new JButton("Salir");

        Dimension buttonSize = new Dimension(200, 50);
        jugarButton.setPreferredSize(buttonSize);
        editorButton.setPreferredSize(buttonSize);
        configuracionButton.setPreferredSize(buttonSize);
        salirButton.setPreferredSize(buttonSize);

        jugarButton.addActionListener(new JugarAction(ventana));
        editorButton.addActionListener(new EditorAction(ventana));
        configuracionButton.addActionListener(new ConfiguracionAction(ventana,this.configuracion));
        salirButton.addActionListener(new SalirAction());

        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.VERTICAL;

        menuPanel.add(jugarButton, gbc);
        menuPanel.add(editorButton, gbc);
        menuPanel.add(configuracionButton, gbc);
        menuPanel.add(salirButton, gbc);

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setOpaque(false);
        outerPanel.setBorder(BorderFactory.createEmptyBorder(200, 100, 100, 100));

        outerPanel.add(menuPanel, BorderLayout.CENTER);

        ventana.add(outerPanel, BorderLayout.CENTER);
    }

    private static class SalirAction extends BaseAction {
        public SalirAction() {
            super(null);
        }

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private static class JugarAction extends BaseAction {
        public JugarAction(Ventana pantalla) {
            super(pantalla);
        }

        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(pantalla, "¡Jugar presionado!");
        }
    }

    private static class EditorAction extends BaseAction {
        public EditorAction(Ventana pantalla) {
            super(pantalla);
        }

        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(pantalla, "¡Editor presionado!");
        }
    }

    private static class ConfiguracionAction extends BaseAction {
        private Configuracion configuracion;
        public ConfiguracionAction(Ventana ventana,Configuracion configuracion) {
            super(ventana);
            this.configuracion = configuracion;
        }

        public void actionPerformed(ActionEvent e) {
            configuracion.imprimirConfiguracion();
        }
    }
  


}
