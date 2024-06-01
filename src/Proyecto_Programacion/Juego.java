package Proyecto_Programacion;

import java.io.BufferedReader;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    private String mensaje= "";

    private Thread hiloJuego;

    private boolean perfecto = false;
    private boolean grabando;
    private boolean retroceder = false;
    private boolean enPausa = false;
    private boolean amarpress = false;
    private boolean azupress = false;
    private boolean rojopress = false;
    private boolean verpress = false;
    private boolean amaractiv = false;
    private boolean azuactiv = false;
    private boolean rojoactiv = false;
    private boolean veractiv = false;


    private BotonBase amarillo;
    private BotonBase azul;
    private BotonBase rojo;
    private BotonBase verde;

    private int cicloSleep;
    private int score = 0;
    private int combo = 0;
    private int consecutivas = 0;
    private int failsconsecutivas = 0;
    private int fails = 0;
    private int canciongrab;
    private int aumento = 5;
    public static final int fichaheight =42;
    public static final int fichawidth = 80;
    private long tiempoInicial;
    private long tiempotranscurrido;

    private List<Ficha> fichas = new ArrayList<>();
    private List<LongIntPair> cancion1 = new ArrayList<>();
    private List<LongIntPair> cancion2 = new ArrayList<>();
    private List<LongIntPair> cancion3 = new ArrayList<>();
    private List<LongIntPair> listaCancion = new ArrayList<>();

    private BufferedImage amarillobase;
    private BufferedImage azulbase;
    private BufferedImage rojobase;
    private BufferedImage verdebase;
    
    private BufferedImage amarillopressimage;
    private BufferedImage azulpressimage;
    private BufferedImage rojopressimage;
    private BufferedImage verdepressimage;
    
    private BufferedImage amarilloelim;
    private BufferedImage azulelim;
    private BufferedImage rojoelim;
    private BufferedImage verdeelim;

    private VideoPanel videoPanel;
    private BufferedImage[] fichaimagenes = new BufferedImage[4];
    public Juego(JPanel pausaPanel, JPanel configJuego, VideoPanel videoPanel) {
        this.videoPanel = videoPanel;
        try{
            fichaimagenes[0] = ImageIO.read(new File("images/fichaamarilla.png"));
            fichaimagenes[1] = ImageIO.read(new File("images/fichaazul.png"));
            fichaimagenes[2] = ImageIO.read(new File("images/ficharoja.png"));
            fichaimagenes[3] = ImageIO.read(new File("images/fichaverde.png"));
            amarillobase = ImageIO.read(new File("images/amarillobase.png"));
            azulbase = ImageIO.read(new File("images/azulbase.png"));
            rojobase = ImageIO.read(new File("images/rojobase.png"));
            verdebase = ImageIO.read(new File("images/verdebase.png"));
            amarillopressimage = ImageIO.read(new File("images/amarillopressimage.png"));
            azulpressimage = ImageIO.read(new File("images/azulpressimage.png"));
            rojopressimage = ImageIO.read(new File("images/rojopressimage.png"));
            verdepressimage = ImageIO.read(new File("images/verdepressimage.png"));
            amarilloelim = ImageIO.read(new File("images/amarilloeli.png"));
            azulelim = ImageIO.read(new File("images/azuleli.png"));
            rojoelim = ImageIO.read(new File("images/rojoeli.png"));
            verdeelim = ImageIO.read(new File("images/verdeeli.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
        // Deshabilitando el comportamiento predeterminado de las teclas traversales
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
        setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.emptySet());
        pintarbase();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char tecla = e.getKeyChar();
                if (grabando == true && tiempotranscurrido >= 1_100_00_000L) {
                    switch (canciongrab) {
                        case 1:
                            switchtecla(tecla,cancion1);
                            break;
                        case 2:
                            switchtecla(tecla,cancion2);
                            break;
                        case 3:
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
                        if(amaractiv){
                            amarillo.setPresionado(true, amarillopressimage);
                        }else{
                            amarillo.setPresionado(true, amarilloelim);
                        }
                        break;
                    case KeyEvent.VK_S:
                        if(azuactiv){
                            azul.setPresionado(true, azulpressimage);
                        }else{
                            azul.setPresionado(true, azulelim);
                        }
                        break;
                    case KeyEvent.VK_D:
                        if(rojoactiv){
                            rojo.setPresionado(true, rojopressimage);
                        }else{
                            rojo.setPresionado(true, rojoelim);
                        }
                        break;
                    case KeyEvent.VK_F:
                        if(veractiv){
                            verde.setPresionado(true, verdepressimage);
                        }else{
                            verde.setPresionado(true, verdeelim);
                        }
                        break;
                    case KeyEvent.VK_Q:
                        if(grabando){ amarillo.setEliminando(true,amarilloelim);}
                        break;
                    case KeyEvent.VK_W:
                        if(grabando){azul.setEliminando(true,azulelim);}
                        break;
                    case KeyEvent.VK_E:
                        if(grabando){rojo.setEliminando(true,rojoelim);}
                        break;
                    case KeyEvent.VK_R:
                        if(grabando){verde.setEliminando(true,verdeelim);}
                        break;
                    case KeyEvent.VK_P:
                        enPausa = !enPausa;
                        pausaPanel.setVisible(true);
                        setFocusable(false);
                        videoPanel.pausarReproduccion();
                        break;
                    case KeyEvent.VK_LEFT:
                        videoPanel.pausarReproduccion();
                        if (tiempotranscurrido >= 50_000_000L) {
                            tiempoInicial += 45_000_000L;
                        } else {
                            tiempoInicial += tiempotranscurrido;
                        }
                        retroceder = true;
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException b) {
                            b.printStackTrace();
                        }
                        break;
                    case KeyEvent.VK_RIGHT: 
                        videoPanel.pausarReproduccion();
                            tiempoInicial -= 45_000_000L;
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
                        amarillo.setPresionado(false, amarillobase);
                        amarpress = false;
                        break;
                    case KeyEvent.VK_S:
                        azul.setPresionado(false, azulbase);
                        azupress = false;
                        break;
                    case KeyEvent.VK_D:
                        rojo.setPresionado(false, rojobase);
                        rojopress = false;
                        break;
                    case KeyEvent.VK_F:
                        verde.setPresionado(false, verdebase);
                        verpress = false;
                        break;
                    case KeyEvent.VK_Q:
                        amarillo.setEliminando(false, amarillobase);
                        break;
                    case KeyEvent.VK_W:
                        azul.setEliminando(false, azulbase);
                        break;
                    case KeyEvent.VK_E:
                        rojo.setEliminando(false, rojobase);
                        break;
                    case KeyEvent.VK_R:
                        verde.setEliminando(false, verdebase);
                        break;
                    case KeyEvent.VK_LEFT:
                        retroceder = false;
                        videoPanel.setVideo((double)tiempotranscurrido / 1_000_000_000L);
                        videoPanel.reanudarReproduccion();
                        break;
                    case KeyEvent.VK_RIGHT:
                        retroceder = false;
                        videoPanel.setVideo((double)tiempotranscurrido / 1_000_000_000L);
                        videoPanel.reanudarReproduccion();
                        break;
                        default:break;
                }
            }
        });

        setFocusable(true);
    }
    private void CicloPrincipalJuego() {
        long tiempoViejo = System.nanoTime();
        tiempoInicial = System.nanoTime();      
        fails = 0;
        combo = 0;
        failsconsecutivas = 0;
        consecutivas = 0;
        grabando = false;
        listaCancion = null;
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
        while (!Thread.currentThread().isInterrupted()) {
            /*if(!cancion.isRunning() && !enPausa){
                break;
                //Entrar a menu de score
            }*/
            try {
                if (!enPausa) {
                    Thread.sleep(cicloSleep);
                    long tiempoNuevo = System.nanoTime();
                    float dt = (tiempoNuevo - tiempoViejo) / 1_000_000_000f;
                    tiempoViejo = tiempoNuevo;
                    tiempotranscurrido = tiempoNuevo - tiempoInicial;
                    
                    
                    
                    if (listaCancion != null) {
                        ListIterator<LongIntPair> iterator2 = listaCancion.listIterator();
                        while (iterator2.hasNext()) {
                        LongIntPair pair = iterator2.next();
                        long first = pair.getFirst();
                        int second = pair.getSecond();
                        if (tiempotranscurrido >= first - 900_000_000L) {
                            crearFicha(first,second);
                            iterator2.remove(); 
                        }
                         }
                    }
                
                    

                    synchronized (fichas) {
                        Iterator<Ficha> iterator = fichas.iterator();
                        while (iterator.hasNext()) {
                            Ficha ficha = iterator.next();
                            ficha.fisica(dt);
                            if (ficha.y + fichaheight >= getHeight() - fichaheight) {
                                switch(ficha.columna){
                                    case 0:
                                        amaractiv = true;
                                        break;
                                    case 1:
                                        azuactiv = true;
                                        break;
                                    case 2:
                                        rojoactiv = true;
                                        break;
                                    case 3:
                                        veractiv = true;
                                        break;
                                }
                                if (ficha.y + fichaheight >= getHeight() - fichaheight && ficha.y + fichaheight <= getHeight() - 47) {
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
                                if (ficha.y + fichaheight >= getHeight() - 10 && ficha.y <= getHeight() - 40) {
                                    aumento = 7;
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
                            switch(ficha.columna){
                                case 0:
                                    amaractiv = false;
                                    break;
                                case 1:
                                    azuactiv = false;
                                    break;
                                case 2:
                                    rojoactiv = false;
                                    break;
                                case 3:
                                    veractiv = false;
                                    break;
                            }
                            iterator.remove();
                            // pintar fallaste
                            combo = 0;
                            fails++;
                            failsconsecutivas++;
                            if(failsconsecutivas==10){
                                Thread.currentThread().interrupt();
                                Salirdeljuego(false);
                            }
                            consecutivas = 0;
                            mensaje= "¡Fallaste!";
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
    private void CicloEditar() {
        tiempoInicial = System.nanoTime();
        long tiempoViejo = System.nanoTime();
        grabando = true;
        listaCancion = null;
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
        long tiempoPausa = 0;
        long tiempoViejoPausa = 0;
        long tiempoPausaNuevo = 0;
        long tiempoRetroceso = 0;
        setFocusable(true);
        requestFocus();
        while (!Thread.currentThread().isInterrupted()) {
            
            try {
                if(!enPausa){
                Thread.sleep(cicloSleep);

                if(!retroceder){
                    tiempoNuevo = System.nanoTime()-tiempoRetroceso;
                    
                } else{
                    tiempoRetroceso = System.nanoTime() - tiempoNuevo;
                }
                tiempoRelativo = System.nanoTime();
                float dt = (tiempoRelativo - tiempoViejo) / 1_000_000_000f;
                tiempoViejo = tiempoRelativo;
                tiempoViejoPausa = tiempoPausa;
                tiempotranscurrido = tiempoNuevo - (tiempoInicial + tiempoPausa);
    

                
                if (listaCancion != null) {
                    ListIterator<LongIntPair> iterator = listaCancion.listIterator();
                    while (iterator.hasNext()) {
                        LongIntPair pair = iterator.next();
                        long first = pair.getFirst();
                        int second = pair.getSecond();
                        // Condicion de creacion de ficha TEMPORAL
                        // Se cambiara por la comprobacion de la ficha asociada con el pair (tiempo,columna)
                        if(retroceder){
                            if(tiempotranscurrido <= first && tiempotranscurrido >= first - 900_000_000L){
                                if(pair.getDisponible()){
                                    crearFichaAbajo(first,second);
                                    pair.setDisponible(false);
                                }
                            }
                        
                        }else{
                        if(tiempotranscurrido >= first - 900_000_000L && tiempotranscurrido <= first){
                                if(pair.getDisponible()){
                                    crearFicha(first,second);
                                    pair.setDisponible(false);
                                }
                            }
                        }
                        
                    }
                }
                
                
                
    
                List<Ficha> fichasEliminadas = new ArrayList<>();
    
                // Dentro del bucle de movimiento de fichas
                synchronized (fichas) {
                    ListIterator<Ficha> iterator = fichas.listIterator();
                    while (iterator.hasNext()) {
                        Ficha ficha = iterator.next();
                        if (retroceder) {
                            ficha.fisicaB(dt);
                        } else {
                            ficha.fisica(dt);
                        }
                        if (ficha.y + fichaheight >= getHeight() - fichaheight && ficha.y + fichaheight <= getHeight()) {
                            switch (ficha.columna) {
                                case 0:
                                    if (procesarEliminacionFicha(amarillo, ficha)) {
                                        iterator.remove();
                                    }
                                    break;
                                case 1:
                                    if (procesarEliminacionFicha(azul, ficha)) {
                                        iterator.remove();
                                    }
                                    break;
                                case 2:
                                    if (procesarEliminacionFicha(rojo, ficha)) {
                                        iterator.remove();
                                    }
                                    break;
                                case 3:
                                    if (procesarEliminacionFicha(verde, ficha)) {
                                        iterator.remove();
                                    }
                                    break;
                            }
                        }
                        if (ficha.y >= getHeight()) {
                            fichasEliminadas.add(ficha);

                            for(int i=0;i<listaCancion.size();i++){
                                LongIntPair pair = listaCancion.get(i);
                                if(pair.getFirst() == ficha.tiempo){
                                    pair.setDisponible(true);
                                }
                            }

                            iterator.remove();
                        }
                        if (ficha.y <= getHeight()/2) {
                            iterator.remove();
                            for(int i=0;i<listaCancion.size();i++){
                                LongIntPair pair = listaCancion.get(i);
                                if(pair.getFirst() == ficha.tiempo){
                                    pair.setDisponible(true);
                                }
                            }
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
            }else{
                Thread.sleep(cicloSleep);
                tiempoPausaNuevo = System.nanoTime() - tiempoRelativo;
                tiempoPausa = tiempoPausaNuevo + tiempoViejoPausa;
            }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        videoPanel.setVisible(true);
        videoPanel.setOpaque(false);
        switch(cancion){
            case 1:
                leerDatosCancion(cancion1,new File("cancion1.txt"));
                videoPanel.reproducir("audio/everlong.mp4");
                this.canciongrab = 1;
                break;
            case 2:
                leerDatosCancion(cancion2, new File("cancion2.txt"));
                videoPanel.reproducir("audio/one.mp4");
                this.canciongrab = 2;
                break;
            case 3:
                leerDatosCancion(cancion3, new File("cancion3.txt"));
                //reproducir("audio/cancion3.wav");
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
        videoPanel.setVisible(true);
        videoPanel.setOpaque(false);
        try{
        switch(cancion){
            case 1:
                leerDatosCancion(cancion1,new File("cancion1.txt"));
                videoPanel.reproducir("audio/everlong.mp4");
                this.canciongrab = 1;
                break;
            case 2:
                leerDatosCancion(cancion2, new File("cancion2.txt"));
                videoPanel.reproducir("audio/one.mp4");
                this.canciongrab = 2;
                break;
            case 3:
                leerDatosCancion(cancion3, new File("cancion3.txt"));
                videoPanel.reproducir("audio/cancion3.wav");
                this.canciongrab = 3;
                break;
            default:break;
        }}
        catch(Exception e){
            e.printStackTrace();
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
        mensaje= "";
        

        synchronized(fichas){
            fichas.clear();
        }
        videoPanel.salirDelVideo();
        if(GuardarGrabacion){
            switch(this.canciongrab){
                case 1:
                    cancion1 = listaCancion;
                    guardarDatosCancion(cancion1, new File("cancion1.txt"));
                    break;
                case 2:
                    cancion2 = listaCancion;
                    guardarDatosCancion(cancion2,new File("cancion2.txt"));
                    break;
                case 3:
                    cancion3 = listaCancion;
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
    public void crearFicha(long tiempo,int columna){
        Ficha ficha = new Ficha(tiempo,columna,this);
        fichas.add(ficha);
    }
    public void crearFichaAbajo(long tiempo, int columna){
        Ficha ficha = new Ficha(tiempo,columna,this,true);
        fichas.add(ficha);
    }

    public void pintarbase() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.anchor = GridBagConstraints.SOUTH;
        
       
        amarillo = new BotonBase(amarillobase); 
        add(amarillo, c);
        
        azul = new BotonBase(azulbase);
        c.gridx++;
        add(azul, c);
    
        rojo = new BotonBase(rojobase);
        c.gridx++;
        add(rojo, c);
    
        verde = new BotonBase(verdebase);
        c.gridx++;
        add(verde, c);
        amarillo.requestFocus();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelHeight = getHeight();
        int marginLeft = 10;
        int marginBottom = 20;

        int scoreY = panelHeight - marginBottom;
        int comboY = scoreY - marginBottom;
        int failsY = comboY - marginBottom;
        int tiempoY = failsY - marginBottom;

        dibujarStats(g,"Score: " + score, marginLeft, scoreY);
        dibujarStats(g,"Combo: x" + combo, marginLeft, comboY);
        dibujarStats(g,"Fails: " + fails, marginLeft, failsY);
        dibujarStats(g,"Tiempo: " + tiempotranscurrido / 1_000_000_000L, marginLeft, tiempoY);

     
        if(mensaje=="¡Bien!"){
            dibujarmensaje(g,Color.GREEN,mensaje, getWidth()/2 - fichaheight, getHeight()/2 - fichaheight);
        }else if(mensaje=="¡Perfecto!"){
            dibujarmensaje(g,Color.BLUE,mensaje, getWidth()/2 - fichaheight, getHeight()/2 - fichaheight);
        }else{
            dibujarmensaje(g,Color.RED,mensaje, getWidth()/2 - fichaheight, getHeight()/2 - fichaheight);
        }

        List<Ficha> fichasCopia;
        synchronized (fichas) {
            fichasCopia = new ArrayList<>(fichas);
        }
        
        for (Ficha ficha : fichasCopia) {
            g.drawImage(fichaimagenes[ficha.columna],(int)ficha.x,(int) ficha.y, 80, 50, null);
        }
    }
    private void dibujarmensaje(Graphics g,Color color, String mensaje, int x, int y){
        g.setColor(color);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(mensaje, x, y);
    }
    private void dibujarStats(Graphics g, String mensaje, int x, int y){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(mensaje, x, y);
    }
 
    public void setPausa(boolean pausa){
        enPausa = pausa;
    }
    public boolean getGrabando(){
        return grabando;
    }
    
    private void procesarFichaPresionada(BotonBase color, boolean press, Iterator<Ficha> iterator) {
        if (color.getPresionado() && !press) {
            switch(consecutivas){
                case 20:
                    combo = 2;
                    break;
                case 40:
                    combo = 3;
                    break;
                case 70:
                    combo = 4;
                    break;
            }
            score += aumento * combo;
            consecutivas++;
            failsconsecutivas= 0;
            if (perfecto) {
                mensaje= "¡Perfecto!";
            } else {
                mensaje= "¡Bien!";
            }
            if(color == amarillo){
                amaractiv = false;
            }
            if(color == azul){
                azuactiv = false;
            }
            if(color == rojo){
                rojoactiv = false;
            }
            if(color == verde){
                veractiv = false;
            }

            iterator.remove();
        }
    }
    private boolean procesarEliminacionFicha(BotonBase color, Ficha ficha) {
        boolean eliminado = false;
        if (color.getEliminando()) {
            Iterator<LongIntPair> iterator = listaCancion.iterator();
            while (iterator.hasNext()) {
                LongIntPair pair = iterator.next();
                if (pair.getFirst() == ficha.tiempo) {
                    iterator.remove();
                    eliminado = true;
                    break;
                }
            }
        }
        return eliminado;
    }
    
    private boolean TeclaSiendoPulsada(BotonBase color, boolean press){
        if(color.getPresionado()){
            return true;
        }
        return press;
    }
}
