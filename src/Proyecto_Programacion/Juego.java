    package Proyecto_Programacion;

    import java.io.File;
    import java.io.IOException;

    import javax.sound.sampled.*;
    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.KeyAdapter;
    import java.awt.event.KeyEvent;
    import java.util.Collections;
    import java.util.Iterator;
    import java.util.ArrayList;
    import java.util.List;

    public class Juego extends JPanel {
        private int cicloSleep;
        private Float volumen;
        private String mensajeFalla = "";
        private String mensajegood = "";
        private String mensajeperfect = "";
        private Clip cancion;
        private Thread hiloJuego;
        private boolean amarpress = false;
        private boolean azupress = false;
        private boolean rojopress = false;
        private boolean verpress = false;
        private CircularButton amarillo;
        private CircularButton azul;
        private CircularButton rojo;
        private CircularButton verde;
        private Color GOLD = new Color(200, 200, 8); // Valores RGB para el color dorado


        public long tiempotranscurrido;
        public int score = 0;
        public int combo = 0;
        public int fails = 0;
        public int grabar;
        public int canciongrab;
        public boolean enPausa = false;
        public List<Ficha> fichas = new ArrayList<>();
        public List<LongIntPair> cancion1 = new ArrayList<>();
        public List<LongIntPair> cancion2 = new ArrayList<>();
        public List<LongIntPair> cancion3 = new ArrayList<>();


        public Juego(JPanel pausaPanel, JPanel configJuego) {
            Runtime runtime = Runtime.getRuntime();
            
            if(runtime.availableProcessors()>2){
                this.cicloSleep = 1;
            } else {
                this.cicloSleep = 10;
            }

            this.volumen = 0.0f;
            // Deshabilitar el comportamiento predeterminado de las teclas traversales

            setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
            setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.emptySet());
            pintarbase();

            // Añadir un KeyAdapter para manejar eventos de teclado
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (grabar == 2) {
                        char tecla = e.getKeyChar(); // Obtener el carácter de la tecla presionada
                        System.out.println(tecla);
                        // Convertir el carácter a minúscula para la comparación
                        switch (Character.toLowerCase(tecla)) {
                            case 'a':
                                cancion1.add(new LongIntPair(tiempotranscurrido, 0));
                                break;
                            case 's':
                                cancion1.add(new LongIntPair(tiempotranscurrido, 1));
                                break;
                            case 'd':
                                cancion1.add(new LongIntPair(tiempotranscurrido, 2));
                                break;
                            case 'f':
                                cancion1.add(new LongIntPair(tiempotranscurrido, 3));
                                break;
                            default:
                                break;
                        }
                    }
                }
                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_A:
                            amarillo.setColor(GOLD);
                            break;
                        case KeyEvent.VK_S:
                            azul.setColor(GOLD);
                            break;
                        case KeyEvent.VK_D:
                            rojo.setColor(GOLD);
                            break;
                        case KeyEvent.VK_F:
                            verde.setColor(GOLD);
                            break;
                        case KeyEvent.VK_P:
                            enPausa = !enPausa;
                                pausaPanel.setVisible(true);
                                setFocusable(false);
                                PausarSonido();                       
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_A:
                            amarillo.setColor(Color.YELLOW);
                            amarpress = false;
                            break;
                        case KeyEvent.VK_S:
                            azul.setColor(Color.BLUE);
                            azupress = false;
                            break;
                        case KeyEvent.VK_D:
                            rojo.setColor(Color.RED);
                            rojopress = false;
                            break;
                        case KeyEvent.VK_F:
                            verde.setColor(Color.GREEN);
                            verpress = false;
                            break;
                    }
                }
            });

            setFocusable(true);
        }

        public void Iniciar() {
            
            reproducir("audio/cancion.wav");
            hiloJuego = new Thread(new Runnable() {
                @Override
                public void run() {
                    cicloPrincipalJuego(1);
                }
            });
            hiloJuego.start();

            
        }
        public void IniciarGrabacion(int cancion){
            
            switch(cancion){
                case 1:
                    reproducir("audio/cancion.wav");
                    this.canciongrab = 1;
                    break;
                default:break;
            }
            hiloJuego = new Thread(new Runnable(){
                public void run(){
                    cicloPrincipalJuego(2);
                }
            });
            hiloJuego.start();
        }
        public void Salirdeljuego(){
        
            score = 0;
            combo = 0;
            fails = 0;
            enPausa = false;
            mensajeFalla = "";
            

            synchronized(fichas){
                fichas.clear();
            }
            PararSonido();
            // Detener el hilo de juego
            if (hiloJuego != null && hiloJuego.isAlive()) {
            hiloJuego.interrupt();
            hiloJuego = null;
        }
        }
        public void cicloPrincipalJuego(int banderagrabar) {
            long tiempoViejo = System.nanoTime();
            long tiempoInicial = System.nanoTime();
            long tiempoUltimaFicha = tiempoViejo;
            int aumento = 5;
            boolean perfecto = false;
            combo = 0;
            this.grabar = banderagrabar;
            while (!Thread.currentThread().isInterrupted()) { // Aquí se comprueba si el hilo ha sido interrumpido
                try {
                    if (!enPausa) {
                        Thread.sleep(cicloSleep);
                        long tiempoNuevo = System.nanoTime();
                        float dt = (tiempoNuevo - tiempoViejo) / 1_000_000_000f;
                        tiempoViejo = tiempoNuevo;
                        tiempotranscurrido = tiempoNuevo - tiempoInicial;
                        System.out.println(tiempotranscurrido);
                        if(cancion1!=null && banderagrabar==1){
                        
                        Iterator<LongIntPair> iterator2 = cancion1.iterator();
                        while (iterator2.hasNext()) {
                            LongIntPair pair = iterator2.next();
                            long first = pair.getFirst();
                            int second = pair.getSecond();
                            if (tiempotranscurrido >= first) {
                                crearFicha(second);
                                iterator2.remove(); 
                            }
                        }
                        }   
                    
                        requestFocusInWindow();

                        if(banderagrabar==1){
                        synchronized (fichas) {
                            Iterator<Ficha> iterator = fichas.iterator();
                            while (iterator.hasNext()) {
                                Ficha ficha = iterator.next();
                                ficha.fisica(dt);
                                if (ficha.y + 50 >= getHeight() - 50) {
                                    if(ficha.y + 50 >= getHeight() - 50 && ficha.y + 50 <= getHeight()-47){
                                
                                    switch(ficha.columna){
                                        case 0:
                                            if(amarillo.getColor() == GOLD){
                                                amarpress = true;
                                            }
                                            break;
                                        case 1:
                                            if(azul.getColor() == GOLD){
                                                azupress = true;
                                            }
                                            break;
                                        case 2:
                                            if(rojo.getColor() == GOLD){
                                                rojopress = true;
                                            }
                                            break;
                                        case 3:
                                            if(verde.getColor() == GOLD){
                                                verpress = true;
                                            }
                                            break;
                                    }
                                    }
                                    if(ficha.y + 50 >= getHeight() - 10 && ficha.y <= getHeight()- 40) {
                                        aumento =10;
                                        perfecto = true;
                                    }else{
                                        aumento = 5;
                                        perfecto = false;
                                    }
                                    
                                    switch (ficha.columna) {
                                        case 0:
                                            
                                            if (amarillo.getColor() == GOLD && !amarpress){
                                                score += aumento;
                                                combo++;
                                                if(perfecto){
                                                    mensajeperfect = "¡Perfecto!";
                                                    mensajegood = "";
                                                }else{
                                                    mensajegood = "¡Bien!";
                                                    mensajeperfect = "";
                                                }
                                                mensajeFalla = "";
                                                iterator.remove();
                                            }
                                            break;
                                        case 1:
                                            if (azul.getColor() == GOLD && !azupress) {
                                                score += aumento;
                                                combo++;
                                                if(perfecto){
                                                    mensajeperfect = "¡Perfecto!";
                                                    mensajegood = "";
                                                }else{
                                                    mensajegood = "¡Bien!";
                                                    mensajeperfect = "";
                                                }
                                                mensajeFalla = "";
                                                iterator.remove();
                                            }
                                            break;
                                        case 2:
                                            if (rojo.getColor() == GOLD && !rojopress) {
                                                score += aumento;
                                                combo++;
                                                if(perfecto){
                                                    mensajeperfect = "¡Perfecto!";
                                                    mensajegood = "";
                                                }else{
                                                    mensajegood = "¡Bien!";
                                                    mensajeperfect = "";
                                                }
                                                mensajeFalla = "";
                                                iterator.remove();
                                            }
                                            break;
                                        case 3:
                                            if (verde.getColor() == GOLD && !verpress) {
                                                score += aumento;
                                                combo++;
                                                if(perfecto){
                                                    mensajeperfect = "¡Perfecto!";
                                                    mensajegood = "";
                                                }else{
                                                    mensajegood = "¡Bien!";
                                                    mensajeperfect = "";
                                                }
                                                mensajeFalla = "";
                                                iterator.remove();
                                            }
                                            break;
                                    }
                                }
                                // Si la ficha llegó al final, eliminarla
                                if (ficha.y >= getHeight()) {
                                    iterator.remove();
                                    // pintar fallaste
                                    combo = 0;
                                    fails++;
                                    mensajeFalla = "¡Fallaste!";
                                }
                            }
                        }
                        }
                        repaint();
                    } else {
                        Thread.sleep(cicloSleep); 
                        long tiempoNuevo = System.nanoTime();
                        tiempoViejo = tiempoNuevo;
        
                    }
        
                } catch (InterruptedException e) {
                    // Manejo de la excepción
                    Thread.currentThread().interrupt();Thread.currentThread().interrupt(); // Restablecer la bandera de interrupción
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        

        
        
        public void crearFicha(int columna){
            Ficha ficha = new Ficha(columna,this);
            fichas.add(ficha);
        }

        public void pintarbase() {
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.weighty = 1;
            c.anchor = GridBagConstraints.SOUTH;
        
            amarillo = new CircularButton(Color.YELLOW); 
            add(amarillo, c);
        
            azul = new CircularButton(Color.BLUE);
            c.gridx++;
            add(azul, c);
        
            rojo = new CircularButton(Color.RED);
            c.gridx++;
            add(rojo, c);
        
            verde = new CircularButton(Color.GREEN);
            c.gridx++;
            add(verde, c);

            amarillo.requestFocus();

        }
        

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            //score
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + score, 10, 20);
            //combo
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Combo: " + combo, 10, 40);
            //fails
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Fails: " + fails, 10, 60);

            //mensaje de bien
            g.setColor(Color.GREEN);    
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(mensajegood, getWidth()/2 - 50, getHeight()/2 - 50);
            //mensaje de perfecto
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(mensajeperfect, getWidth()/2 - 50, getHeight()/2 - 50);



            //mensaje de falla
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(mensajeFalla, getWidth()/2 - 50, getHeight()/2);

            List<Ficha> fichasCopia;
            synchronized (fichas) {
                fichasCopia = new ArrayList<>(fichas);
            }
            
            Color[] color = {Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN};
            for (Ficha ficha : fichasCopia) {
                g.setColor(color[ficha.columna]);
                g.fillOval((int) ficha.x, (int) ficha.y, 50, 50);
            }
        }
        public void fichasrevalidate(){
            List<Ficha> fichasCopia;
            synchronized (fichas) {
                fichasCopia = new ArrayList<>(fichas);
            }
            for (Ficha ficha : fichasCopia) {
                ficha.revalidateposition();
            }
        }

        public void setVolumen(Float gainControl) {
            volumen = gainControl;
        }

        public float getVolumen() {
            return volumen;
        }

        public void reproducir(String ruta) {
            try {
                if (cancion != null) {
                    cancion.stop();
                    cancion.close();
                }
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(ruta));
                cancion = AudioSystem.getClip();
                cancion.open(audioInputStream);

                FloatControl clipGainControl = (FloatControl) cancion.getControl(FloatControl.Type.MASTER_GAIN);
                if (clipGainControl != null) {
                    clipGainControl.setValue(volumen);
                }
                cancion.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        public void PararSonido() {
            if (cancion != null) {
                cancion.stop();
                cancion.close();
                cancion = null;
            }
        }

        public void PausarSonido() {
            if (cancion != null) {
                cancion.stop();
            }
        }

        public void ReanudarSonido() {
            if (cancion != null) {
                // Reanudar el clip desde la posición actual
                Clip canciontemp;
                int posicion = cancion.getFramePosition();
                cancion.close();
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("audio/cancion.wav"));
                    canciontemp = AudioSystem.getClip();
                    canciontemp.open(audioInputStream);

                    FloatControl clipGainControl = (FloatControl) canciontemp.getControl(FloatControl.Type.MASTER_GAIN);
                    if (clipGainControl != null) {
                        clipGainControl.setValue(volumen);
                    }

                    canciontemp.setFramePosition(posicion);
                    cancion = canciontemp;
                    cancion.start();
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                    e.printStackTrace();
                }
            }
        }
    }
