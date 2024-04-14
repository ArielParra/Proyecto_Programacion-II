import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Menu extends JFrame {
    public Menu() {
        // Configurar el JFrame
        setTitle("Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Hacer que la ventana sea de pantalla completa
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true); // Esto elimina la barra de título y los bordes

        // Establecer el tamaño mínimo de la ventana
        setMinimumSize(new Dimension(800, 600));

        // Establecer el fondo
        try {
            File file = new File("fondoMenu.png");
            if(file.exists()) {
                setContentPane(new JLabel(new ImageIcon(file.getAbsolutePath())));
            } else {
                getContentPane().setBackground(Color.GREEN);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        setLayout(new BorderLayout());

        // Crear el título principal en grande
        JLabel titleLabel = new JLabel("Mi Juego", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel, BorderLayout.NORTH);

        // Crear botones para las opciones
        JButton jugarButton = new JButton("Jugar");
        JButton editorButton = new JButton("Editor");
        JButton configuracionButton = new JButton("Configuración");
        JButton salirButton = new JButton("Salir");

        // Establecer el tamaño predeterminado para los botones
        Dimension buttonSize = new Dimension(200, 50);
        jugarButton.setPreferredSize(buttonSize);
        editorButton.setPreferredSize(buttonSize);
        configuracionButton.setPreferredSize(buttonSize);
        salirButton.setPreferredSize(buttonSize);

        // Crear instancias de clases de acción para los botones
        JugarAction jugarAction = new JugarAction();
        EditorAction editorAction = new EditorAction();
        ConfiguracionAction configuracionAction = new ConfiguracionAction();
        SalirAction salirAction = new SalirAction();

        // Agregar ActionListener para cada botón
        jugarButton.addActionListener(jugarAction);
        editorButton.addActionListener(editorAction);
        configuracionButton.addActionListener(configuracionAction);
        salirButton.addActionListener(salirAction);

        // Establecer el diseño del menú
        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setOpaque(false); // Hacer el panel transparente

        // Configurar la restricción para centrar verticalmente los botones
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Establecer la columna
        gbc.weighty = 1; // Establecer peso vertical para centrar
        gbc.fill = GridBagConstraints.VERTICAL; // Rellenar verticalmente los botones

        // Agregar botones al panel
        menuPanel.add(jugarButton, gbc);
        menuPanel.add(editorButton, gbc);
        menuPanel.add(configuracionButton, gbc);
        menuPanel.add(salirButton, gbc);

        // Crear un panel externo para contener el panel de menú
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setOpaque(false); // Hacer el panel transparente
        outerPanel.setBorder(BorderFactory.createEmptyBorder(200, 100, 100, 100)); // Añadir un borde alrededor del panel de menú

        // Agregar el panel de menú al panel externo
        outerPanel.add(menuPanel, BorderLayout.CENTER);

        add(outerPanel, BorderLayout.CENTER);

        setVisible(true);
    }
   // Clase de acción para el botón "Salir"
   private class SalirAction implements ActionListener {
    public void actionPerformed(ActionEvent e) {
            System.exit(0);
    }
}
    // Clase de acción para el botón "Jugar"
    public class JugarAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Lógica para el botón "Jugar"
            JOptionPane.showMessageDialog(Menu.this, "¡Jugar presionado!");
        }
    }

    // Clase de acción para el botón "Editor"
    private class EditorAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Lógica para el botón "Editor"
            JOptionPane.showMessageDialog(Menu.this, "¡Editor presionado!");
        }
    }

    // Clase de acción para el botón "Configuración"
    private class ConfiguracionAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Lógica para el botón "Configuración"
            JOptionPane.showMessageDialog(Menu.this, "¡Configuración presionada!");
        }
    }

 

    public static void main(String[] args) {
        new Menu();
    }
}
