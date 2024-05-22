        package Proyecto_Programacion;

        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileReader;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.io.PrintWriter;
        import javax.sound.sampled.*;
        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.KeyAdapter;
        import java.awt.event.KeyEvent;
        import java.util.Collections;
        import java.util.Iterator;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.ListIterator;

        public class Juego extends JPanel {

            private String mensajeFalla = "";
            private String mensajegood = "";
            private String mensajeperfect = "";

            private Clip cancion;

            private Thread hiloJuego;

            private boolean perfecto = false;
            private boolean grabando;
            private boolean retroceder = false;
            private boolean enPausa = false;
            private boolean amarpress = false;
            private boolean azupress = false;
            private boolean rojopress = false;
            private boolean verpress = false;

            private CircularButton amarillo;
            private CircularButton azul;
            private CircularButton rojo;
            private CircularButton verde;

            private Color GOLD = new Color(200, 200, 8); 

            private int cicloSleep;
            private int score = 0;
            private int combo = 0;
            private int fails = 0;
            private int canciongrab;
            private int aumento = 5;
            private long tiempoInicial;
            private long tiempotranscurrido;
            private Float volumen;

            private List<Ficha> fichas = new ArrayList<>();
            private List<LongIntPair> cancion1 = new ArrayList<>();
            private List<LongIntPair> cancion2 = new ArrayList<>();
            private List<LongIntPair> cancion3 = new ArrayList<>();

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
                        char tecla = e.getKeyChar();
                        if (grabando == true && tiempotranscurrido >= 1_100_00_000L) {
                            switch (canciongrab) {
                                case 1:
                                    // Acceder a la lista de la canción 1
                                    switchtecla(tecla,cancion1);
                                    break;
                                case 2:
                                    // Acceder a la lista de la canción 2 
                                    switchtecla(tecla,cancion2);
                                    break;
                                case 3:
                                    // Acceder a la lista de la canción 3 
                                    switchtecla(tecla,cancion3);
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
                            case KeyEvent.VK_LEFT:
                                if (tiempotranscurrido >= 50_000_000L) {
                                    tiempoInicial += 50_000_000L;
                                } else {
                                    tiempoInicial += tiempotranscurrido;
                                }
                                retroceder = true;
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException b) {
                                    // Handle the InterruptedException appropriately
                                    b.printStackTrace();
                                }
                                break;
                            case KeyEvent.VK_RIGHT: 
                                    tiempoInicial -= 100_000_000L;
                                    System.out.println(tiempotranscurrido);
                                    
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
                            case KeyEvent.VK_LEFT:
                                retroceder = false;
                                break;

                                default:break;
                        }
                    }
                });

                setFocusable(true);
            }
            private void switchtecla(char tecla, List<LongIntPair> cancion){
                switch (Character.toLowerCase(tecla)) {
                    case 'a':
                        cancion.add(new LongIntPair(tiempotranscurrido, 0));
                        break;
                    case 's':
                        cancion.add(new LongIntPair(tiempotranscurrido, 1));
                        break;
                    case 'd':
                        cancion.add(new LongIntPair(tiempotranscurrido, 2));
                        break;
                    case 'f':
                        cancion.add(new LongIntPair(tiempotranscurrido, 3));
                        break;
                    default:
                        break;
                }
            }
            public void Iniciar(int cancion) throws IOException {
                
                switch(cancion){
                    case 1:
                        leerDatosCancion(cancion1,new File("cancion1.txt"));
                        reproducir("audio/cancion.wav");
                        this.canciongrab = 1;
                        break;
                    case 2:
                        leerDatosCancion(cancion2, new File("cancion2.txt"));
                        reproducir("audio/cancion2.wav");
                        this.canciongrab = 2;
                        break;
                    case 3:
                        leerDatosCancion(cancion3, new File("cancion3.txt"));
                        reproducir("audio/cancion3.wav");
                        this.canciongrab = 3;
                        break;
                    default:break;
                }
                hiloJuego = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CicloPrincipalJuego();
                    }
                });
                hiloJuego.start();

                
            }
            public void IniciarGrabacion(int cancion) throws IOException{
                 
                switch(cancion){
                    case 1:
                        leerDatosCancion(cancion1,new File("cancion1.txt"));
                        reproducir("audio/cancion.wav");
                        this.canciongrab = 1;
                        break;
                    case 2:
                        leerDatosCancion(cancion2, new File("cancion2.txt"));
                        reproducir("audio/cancion2.wav");
                        this.canciongrab = 2;
                        break;
                    case 3:
                        leerDatosCancion(cancion3, new File("cancion3.txt"));
                        reproducir("audio/cancion3.wav");
                        this.canciongrab = 3;
                        break;
                    default:break;
                }
                
                hiloJuego = new Thread(new Runnable(){
                    public void run(){
                        CicloEditar();
                    }
                });
                hiloJuego.start();
            }
            public void Salirdeljuego(boolean GuardarGrabacion) throws IOException{
            
                score = 0;
                combo = 0;
                fails = 0;
                enPausa = false;
                mensajeFalla = "";
                

                synchronized(fichas){
                    fichas.clear();
                }
                PararSonido();
                if(GuardarGrabacion){
                    switch(this.canciongrab){
                        case 1:
                            guardarDatosCancion(cancion1, new File("cancion1.txt"));
                            break;
                        case 2:
                            guardarDatosCancion(cancion2,new File("cancion2.txt"));
                            break;
                        case 3:
                            guardarDatosCancion(cancion3, new File("cancion3.txt"));
                            break;
                            default:
                            break;
                    }
                }
                if (hiloJuego != null && hiloJuego.isAlive()) {
                hiloJuego.interrupt();
                hiloJuego = null;
            }
            }
            private void leerDatosCancion(List<LongIntPair> cancion, File archivo) throws IOException {
            try (BufferedReader reader = new BufferedReader(new FileReader("saved/"+ archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length == 2) {
                        long first = Long.parseLong(partes[0]);
                        int second = Integer.parseInt(partes[1]);
                        cancion.add(new LongIntPair(first, second));
                    }
                }
            }
            }
            private void guardarDatosCancion(List<LongIntPair> cancion, File archivo) throws IOException {
                try (PrintWriter writer = new PrintWriter(new FileWriter("saved/"+ archivo))) {
                    for (LongIntPair pair : cancion) {
                        writer.println(pair.getFirst() + "," + pair.getSecond());
                    }
                }
            }
            private void CicloEditar() {
                tiempoInicial = System.nanoTime();
                long tiempoViejo = System.nanoTime();
                grabando = true;
                List<LongIntPair> listaCancion = null;
                switch (canciongrab) {
                    case 1:
                        listaCancion = cancion1;
                        break;
                    case 2:
                        listaCancion = cancion2;
                        break;
                    case 3:
                        listaCancion = cancion3;
                        break;
                    default:
                        break;
                }
                long tiempoNuevo = System.nanoTime();
                long tiempoRelativo = System.nanoTime();
                long tiempoRetroceso = 0;
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(cicloSleep);
                       if(!retroceder){
                          tiempoNuevo = System.nanoTime()-tiempoRetroceso;
                          
                       } else{
                          tiempoRetroceso = System.nanoTime() - tiempoNuevo;
                       }
                        tiempoRelativo = System.nanoTime();
                        float dt = (tiempoRelativo - tiempoViejo) / 1_000_000_000f;
                        tiempoViejo = tiempoRelativo;

                        tiempotranscurrido = tiempoNuevo - tiempoInicial;
            
                        System.out.println(tiempotranscurrido);

                        
                        if (listaCancion != null) {
                            for (int i = 0; i < listaCancion.size(); i++) {
                                LongIntPair pair = listaCancion.get(i);
                                long first = pair.getFirst();
                                int second = pair.getSecond();
                                if (tiempotranscurrido <= first - 1_000_000_000L && ((first - 1_000_000_000L) - tiempotranscurrido) < 50_000_000L) {
                                    if (retroceder) {
                                        crearFichaAbajo(second);
                                    } else {
                                        crearFicha(second);
                                    }
                                }
                            }
                        }
                            
                        
                        
            
                        List<Ficha> fichasEliminadas = new ArrayList<>();
            
                        // Dentro del bucle de movimiento de fichas
                        synchronized (fichas) {
                            for (int i = 0; i < fichas.size(); i++) {
                                Ficha ficha = fichas.get(i);
                                if (retroceder) {
                                    ficha.fisicaB(dt);
                                } else {
                                    ficha.fisica(dt);
                                }
                                if (ficha.y >= getHeight()) {
                                    fichasEliminadas.add(ficha);
                                    fichas.remove(i);
                                    i--; // Decrementar el índice después de eliminar para evitar omitir elementos
                                }
                                if (ficha.y <= getHeight() / 2) {
                                    fichas.remove(i);
                                    i--; // Decrementar el índice después de eliminar para evitar omitir elementos
                                }
                            }
                        }
            
                        if (retroceder) {
                            synchronized (fichas) {
                                fichas.addAll(fichasEliminadas);
                                fichasEliminadas.clear();
                            }
                        }
            
                        repaint();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            
            private void CicloPrincipalJuego() {
                long tiempoViejo = System.nanoTime();
                tiempoInicial = System.nanoTime();      
        
                combo = 0;
                grabando = false;
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        if (!enPausa) {
                            Thread.sleep(cicloSleep);
                            long tiempoNuevo = System.nanoTime();
                            float dt = (tiempoNuevo - tiempoViejo) / 1_000_000_000f;
                            tiempoViejo = tiempoNuevo;
                            tiempotranscurrido = tiempoNuevo - tiempoInicial;
                            
                            ListIterator<LongIntPair> iterator2 = null;
                            switch(canciongrab){
                                case 1: 
                                    iterator2 = cancion1.listIterator();
                                    break;
                                case 2:
                                    iterator2 = cancion2.listIterator();
                                    break;
                                case 3:
                                    iterator2 = cancion3.listIterator();
                                    break;
                                    default:break;
                            }
                            
                            while (iterator2.hasNext()) {
                                LongIntPair pair = iterator2.next();
                                long first = pair.getFirst();
                                int second = pair.getSecond();
                                if (tiempotranscurrido >= first - 1_000_000_000L) {
                                    crearFicha(second);
                                    iterator2.remove(); 
                                }
                            }
                        
                            

                            synchronized (fichas) {
                                Iterator<Ficha> iterator = fichas.iterator();
                                while (iterator.hasNext()) {
                                    Ficha ficha = iterator.next();
                                    ficha.fisica(dt);
                                    if (ficha.y + 50 >= getHeight() - 50) {
                                        if (ficha.y + 50 >= getHeight() - 50 && ficha.y + 50 <= getHeight() - 47) {
                                            switch (ficha.columna) {
                                                case 0:
                                                    amarpress = TeclaSiendoPulsada(amarillo, amarpress);
                                                    break;
                                                case 1:
                                                    azupress =TeclaSiendoPulsada(azul, azupress);
                                                    break;
                                                case 2:
                                                    rojopress =TeclaSiendoPulsada(rojo, rojopress);
                                                    break;
                                                case 3:
                                                    verpress = TeclaSiendoPulsada(verde, verpress);
                                                    break;
                                            }
                                        }
                                        if (ficha.y + 50 >= getHeight() - 10 && ficha.y <= getHeight() - 40) {
                                            aumento = 10;
                                            perfecto = true;
                                        } else {
                                            aumento = 5;
                                            perfecto = false;
                                        }
                                        
                                        switch (ficha.columna) {
                                            case 0:
                                                procesarFichaPresionada(amarillo, amarpress, iterator);
                                                break;
                                            case 1:
                                                procesarFichaPresionada(azul, azupress, iterator);
                                                break;
                                            case 2:
                                                procesarFichaPresionada(rojo, rojopress, iterator);
                                                break;
                                            case 3:
                                                procesarFichaPresionada(verde, verpress, iterator);
                                                break;
                                        }
                                    }
                                }
                        }

                        synchronized (fichas) {
                            Iterator<Ficha> iterator = fichas.iterator();
                            while (iterator.hasNext()) {
                                Ficha ficha = iterator.next();
                                if (ficha.y >= getHeight()) {
                                    iterator.remove();
                                    // pintar fallaste
                                    combo = 0;
                                    fails++;
                                    mensajeFalla = "¡Fallaste!";
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
                        Thread.currentThread().interrupt();Thread.currentThread().interrupt(); 
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            
            public void crearFicha(int columna){
                Ficha ficha = new Ficha(columna,this);
                fichas.add(ficha);
            }
            public void crearFichaAbajo(int columna){
                Ficha ficha = new Ficha(columna,this,true);
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
                dibujarStats(g,"Score: " + score,10,20);
                //combo
                dibujarStats(g,"Combo: " + combo, 10, 40);
                //fails
                dibujarStats(g,"Fails: " + fails, 10, 60);

                //mensaje de bien
                dibujarmensaje(g,Color.GREEN,mensajegood, getWidth()/2 - 50, getHeight()/2 - 50);

                //mensaje de perfecto
                dibujarmensaje(g,Color.BLUE,mensajeperfect, getWidth()/2 - 50, getHeight()/2 - 50);

                //mensaje de falla
                dibujarmensaje(g,Color.RED,mensajeFalla, getWidth()/2 - 50, getHeight()/2);

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
            private void dibujarmensaje(Graphics g,Color color, String mensaje, int x, int y){
                g.setColor(color);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString(mensaje, x, y);
            }
            private void dibujarStats(Graphics g, String mensaje, int x, int y){
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString(mensaje, x, y);
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
            public void setPausa(boolean pausa){
                enPausa = pausa;
            }
            public boolean getGrabando(){
                return grabando;
            }
            // Funciones Para el Sonido
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
            
            private void procesarFichaPresionada(CircularButton color, boolean press, Iterator<Ficha> iterator) {
                if (color.getColor() == GOLD && !press) {
                    score += aumento;
                    combo++;
                    if (perfecto) {
                        mensajeperfect = "¡Perfecto!";
                        mensajegood = "";
                    } else {
                        mensajegood = "¡Bien!";
                        mensajeperfect = "";
                    }
                    mensajeFalla = "";
                    iterator.remove();
                }
            }
            private boolean TeclaSiendoPulsada(CircularButton color, boolean press){
                if(color.getColor() == GOLD){
                    return true;
                }
                return press;
            }
        }
