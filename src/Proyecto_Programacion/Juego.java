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
    private String mensaje2= "";

    private Thread hiloJuego;

    private boolean perfecto = false;
    private boolean grabando;
    private boolean retroceder = false;
    private boolean enPausa = false;
    private boolean amarpress,amarpress2 = false;
    private boolean azupress,azupress2 = false;
    private boolean rojopress,rojopress2 = false;
    private boolean verpress,verpress2 = false;
    private boolean amaractiv,amaractiv2 = false;
    private boolean azuactiv,azuactiv2 = false;
    private boolean rojoactiv,rojoactiv2 = false;
    private boolean veractiv,veractiv2 = false;
    private boolean multiplayer;
    public boolean running = true;


    private BotonBase amarillo,amarillo2;
    private BotonBase azul,azul2;
    private BotonBase rojo,rojo2;
    private BotonBase verde,verde2;

    private int cicloSleep;
    private int score,score2 = 0;
    private int combo,combo2 = 0;
    private int consecutivas,consecutivas2 = 0;
    private int failsconsecutivas,failsconsecutivas2 = 0;
    private int fails,fails2 = 0;
    private int canciongrab;
    private int aumento = 5;
    public static final int fichaheight =50;
    public static final int fichawidth = 80;
    private long tiempoInicial;
    private long tiempotranscurrido;

    private List<Ficha> fichas = new ArrayList<>();
    private List<Ficha> fichas2 = new ArrayList<>();
    private List<LongIntPair> cancion1 = new ArrayList<>();
    private List<LongIntPair> cancion2 = new ArrayList<>();
    private List<LongIntPair> cancion3 = new ArrayList<>();
    private List<LongIntPair> listaCancion = new ArrayList<>();
    private List<Ficha> fichasParaEliminar = new ArrayList<>();

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

    private BufferedImage scorebackground;

    private VideoPanel videoPanel;
    private Menu menu;
    private BufferedImage[] fichaimagenes = new BufferedImage[4];
    private Font customFont,customFont2;
    public Juego(Menu menu) {
        this.menu = menu;
        this.videoPanel = menu.videoPanel;
        this.multiplayer = false;
        try {
            this.customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Runtoe.ttf")).deriveFont(12f);
            this.customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Gameplay.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            ge.registerFont(customFont2);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

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
            scorebackground = ImageIO.read(new File("images/score.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
        // Deshabilitando el comportamiento predeterminado de las teclas traversales
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
        setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.emptySet());

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
                    case KeyEvent.VK_H:
                        if(amaractiv2){
                            amarillo2.setPresionado(true, amarillopressimage);
                        }else{
                            amarillo2.setPresionado(true, amarilloelim);
                        }
                        break;
                    case KeyEvent.VK_J:
                        if(azuactiv2){
                            azul2.setPresionado(true, azulpressimage);
                        }else{
                            azul2.setPresionado(true, azulelim);
                        }
                        break; 
                    case KeyEvent.VK_K:
                        if(rojoactiv2){
                            rojo2.setPresionado(true, rojopressimage);
                        }else{
                            rojo2.setPresionado(true, rojoelim);
                        }
                        break;
                    case KeyEvent.VK_L:
                        if(veractiv2){
                            verde2.setPresionado(true, verdepressimage);
                        }else{
                            verde2.setPresionado(true, verdeelim);
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
                        menu.pausaPanel.setVisible(true);
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
                    case KeyEvent.VK_H:
                        amarillo2.setPresionado(false, amarillobase);
                        amarpress2 = false;
                        break;
                    case KeyEvent.VK_J:
                        azul2.setPresionado(false, azulbase);
                        azupress2 = false;
                        break;
                    case KeyEvent.VK_K:
                        rojo2.setPresionado(false, rojobase);
                        rojopress2 = false;
                        break;
                    case KeyEvent.VK_L:
                        verde2.setPresionado(false, verdebase);
                        verpress2 = false;
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
    private void CicloPrincipalMultijugador(){
        long tiempoViejo = System.nanoTime();
        tiempoInicial = System.nanoTime();    
        long tiempoRelativo = System.nanoTime();
        long tiempoPausa = 0;
        long tiempoViejoPausa = 0;
        long tiempoPausaNuevo = 0;
        fails = 0;
        fails2 = 0;
        score = 0;
        score2 = 0;
        enPausa = false;
        mensaje = "";
        combo = 1;
        combo2 = 1;
        failsconsecutivas = 0;
        failsconsecutivas2 = 0;
        consecutivas = 0;
        consecutivas2 = 0;
        grabando = false;
        listaCancion = null;
        running = true;
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
        while (running) {
            setFocusable(true);
            if(videoPanel.mediaPlayer != null){
                if(videoPanel.isSTOPPED() && !enPausa){
                    running = false;
                }
                
             }
            try {
                if (!enPausa) {
                    Thread.sleep(5);
                    tiempoRelativo = System.nanoTime();
                    float dt = (tiempoRelativo - tiempoViejo) / 1_000_000_000f;
                    tiempoViejo = tiempoRelativo;
                    tiempoViejoPausa = tiempoPausa;
                    tiempotranscurrido = tiempoRelativo - (tiempoInicial + tiempoPausa);
    
                    if (listaCancion != null) {
                        ListIterator<LongIntPair> iterator2 = listaCancion.listIterator();
                        while (iterator2.hasNext()) {
                        LongIntPair pair = iterator2.next();
                        long first = pair.getFirst();
                        int second = pair.getSecond();
                        if (tiempotranscurrido >= first - 900_000_000L) {
                            crearFicha(first,second);
                            crearFicha2(first,second);
                            iterator2.remove(); 
                        }
                         }
                    }
                
                    

                    synchronized (fichas) {
                        Iterator<Ficha> iterator = fichas.iterator();
                        while (iterator.hasNext()) {
                            Ficha ficha = iterator.next();
                            ficha.fisica(dt);
                            if (ficha.y + 50 >= getHeight() - 50) {
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
                                    aumento = 7;
                                    perfecto = true;
                                } else {
                                    aumento = 5;
                                    perfecto = false;
                                }
                                
                                switch (ficha.columna) {
                                    case 0:
                                        procesarFichaPresionada(amarillo,amarpress,iterator);
                                        break;
                                    case 1:
                                        procesarFichaPresionada(azul,azupress,iterator);
                                        break;
                                    case 2:
                                        procesarFichaPresionada(rojo,rojopress,iterator);
                                        break;
                                    case 3:
                                        procesarFichaPresionada(verde,verpress,iterator);
                                        break;
                                }
                            }
                        }
                        iterator = fichas.listIterator();
                        while (iterator.hasNext()) {
                            Ficha ficha = iterator.next();
                            if (fichasParaEliminar.contains(ficha)) {
                                iterator.remove();
                            }
                        }
                }
                synchronized (fichas2) {
                    Iterator<Ficha> iterator = fichas2.iterator();
                    while (iterator.hasNext()) {
                        Ficha ficha = iterator.next();
                        ficha.fisica(dt);
                        if (ficha.y + 50 >= getHeight() - 50) {
                            switch(ficha.columna){
                                case 0:
                                    amaractiv2 = true;
                                    break;
                                case 1:
                                    azuactiv2 = true;
                                    break;
                                case 2:
                                    rojoactiv2 = true;
                                    break;
                                case 3:
                                    veractiv2 = true;
                                    break;
                                
                            }
                            if (ficha.y + 50 >= getHeight() - 50 && ficha.y + 50 <= getHeight() - 47) {
                                switch (ficha.columna) {
                                    case 0:
                                        amarpress2 = TeclaSiendoPulsada(amarillo2, amarpress2);
                                        break;
                                    case 1:
                                        azupress2 =TeclaSiendoPulsada(azul2, azupress2);
                                        break;
                                    case 2:
                                        rojopress2 =TeclaSiendoPulsada(rojo2, rojopress2);
                                        break;
                                    case 3:
                                        verpress2 = TeclaSiendoPulsada(verde2, verpress2);
                                        break;
                                }
                            }
                            if (ficha.y + 50 >= getHeight() - 10 && ficha.y <= getHeight() - 40) {
                                aumento = 7;
                                perfecto = true;
                            } else {
                                aumento = 5;
                                perfecto = false;
                            }
                            
                            switch (ficha.columna) {
                                case 0:
                                    procesarFichaPresionada2(amarillo2,amarpress2,iterator);
                                    break;
                                case 1:
                                    procesarFichaPresionada2(azul2,azupress2,iterator);
                                    break;
                                case 2:
                                    procesarFichaPresionada2(rojo2,rojopress2,iterator);
                                    break;
                                case 3:
                                    procesarFichaPresionada2(verde2,verpress2,iterator);
                                    break;
                            }
                        }
                    }
                    iterator = fichas2.listIterator();
                    while (iterator.hasNext()) {
                        Ficha ficha = iterator.next();
                        if (fichasParaEliminar.contains(ficha)) {
                            iterator.remove();
                        }
                    }
            }
               
                synchronized (fichas) {
                    Iterator<Ficha> iterator = fichas.iterator();
                    while (iterator.hasNext()) {
                        Ficha ficha = iterator.next();
                        if (ficha.y >= getHeight()) {
                            if(ficha.x < getWidth()/2 + 80){
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
                                combo = 1;
                                fails++;
                                failsconsecutivas++;
                                consecutivas = 0;
                                mensaje= "¡Fallaste!";
                            

                            }else{
                                switch(ficha.columna){
                                    case 0:
                                        amaractiv2 = false;
                                        break;
                                    case 1:
                                        azuactiv2 = false;
                                        break;
                                    case 2:
                                        rojoactiv2 = false;
                                        break;
                                    case 3:
                                        veractiv2 = false;
                                        break;
                                }
                                iterator.remove();
                                combo2 = 1;
                                fails2++;
                                failsconsecutivas2++;
                                consecutivas2 = 0;
                                mensaje2= "¡Fallaste!";
                            }
                           
                        }
                    }
                }
                synchronized(fichas2){
                    Iterator<Ficha> iterator = fichas2.iterator();
                    while (iterator.hasNext()) {
                        Ficha ficha = iterator.next();
                        if (ficha.y >= getHeight()) {
                            switch(ficha.columna){
                                case 0:
                                    amaractiv2 = false;
                                    break;
                                case 1:
                                    azuactiv2 = false;
                                    break;
                                case 2:
                                    rojoactiv2 = false;
                                    break;
                                case 3:
                                    veractiv2 = false;
                                    break;
                            }
                            iterator.remove();
                            combo2 = 1;
                            fails2++;
                            failsconsecutivas2++;
                            consecutivas2 = 0;
                            mensaje2= "¡Fallaste!";
                        }
                    }
                }
                repaint();

                } else {
                    Thread.sleep(5); 
                    tiempoViejo = System.nanoTime();
                    tiempoPausaNuevo = System.nanoTime() - tiempoRelativo;
                    tiempoPausa = tiempoPausaNuevo + tiempoViejoPausa;

                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try{  
            Salirdeljuego(false);
        }catch(IOException e){
            e.printStackTrace();
        }
 }

    private void CicloPrincipalJuego() {
        long tiempoViejo = System.nanoTime();
        tiempoInicial = System.nanoTime(); 
        long tiempoRelativo = System.nanoTime();
        long tiempoPausa = 0;
        long tiempoViejoPausa = 0;
        long tiempoPausaNuevo = 0;     
        fails = 0;
        score = 0;
        enPausa = false;
        mensaje = "";
        combo = 1;
        failsconsecutivas = 0;
        consecutivas = 0;
        grabando = false;
        listaCancion = null;
        running = true;
        repaint();
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
        while (running) {
            if(videoPanel.mediaPlayer != null){
            if(videoPanel.isSTOPPED() && !enPausa){
                running = false;
            }
             }
            try {
                if (!enPausa) {
                    Thread.sleep(5);
                    tiempoRelativo = System.nanoTime();
                    float dt = (tiempoRelativo - tiempoViejo) / 1_000_000_000f;
                    tiempoViejo = tiempoRelativo;
                    tiempoViejoPausa = tiempoPausa;
                    tiempotranscurrido = tiempoRelativo - (tiempoInicial + tiempoPausa);
    
                    
                    if (listaCancion != null) {
                        ListIterator<LongIntPair> iterator2 = listaCancion.listIterator();
                        while (iterator2.hasNext()) {
                        LongIntPair pair = iterator2.next();
                        long first = pair.getFirst();
                        int second = pair.getSecond();
                        if (tiempotranscurrido >= first -  900_000_000L) {
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
                            if (ficha.y + 50 >= getHeight() - 50) {
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
                                    aumento = 7;
                                    perfecto = true;
                                } else {
                                    aumento = 5;
                                    perfecto = false;
                                }
                                
                                switch (ficha.columna) {
                                    case 0:
                                        procesarFichaPresionada(amarillo,amarpress,iterator);
                                        break;
                                    case 1:
                                        procesarFichaPresionada(azul,azupress,iterator);
                                        break;
                                    case 2:
                                        procesarFichaPresionada(rojo,rojopress,iterator);
                                        break;
                                    case 3:
                                        procesarFichaPresionada(verde,verpress,iterator);
                                        break;
                                }
                            }
                        }
                        iterator = fichas.listIterator();
                        while (iterator.hasNext()) {
                            Ficha ficha = iterator.next();
                            if (fichasParaEliminar.contains(ficha)) {
                                iterator.remove();
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
                            combo = 1;
                            fails++;
                            failsconsecutivas++;
                           /*  if(failsconsecutivas==10){
                                Salirdeljuego(false);
                                break;
                            }*/
                            consecutivas = 0;
                            mensaje= "¡Fallaste!";
                        }
                    }
                }
                repaint();

                } else {
                    Thread.sleep(5); 
                    tiempoViejo = System.nanoTime();
                    tiempoPausaNuevo = System.nanoTime() - tiempoRelativo;
                    tiempoPausa = tiempoPausaNuevo + tiempoViejoPausa;

                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();Thread.currentThread().interrupt(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try{
            Salirdeljuego(false);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void CicloEditar() {
        tiempoInicial = System.nanoTime();
        long tiempoViejo = System.nanoTime();
        grabando = true;
        listaCancion = null;
       
        long tiempoNuevo = System.nanoTime();
        long tiempoRelativo = System.nanoTime();
        long tiempoPausa = 0;
        long tiempoViejoPausa = 0;
        long tiempoPausaNuevo = 0;
        long tiempoRetroceso = 0;
        running = true;
        setFocusable(true);
        requestFocus();
        while (running) {
            
            try {
                if(!enPausa){
                Thread.sleep(5);

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
                
                if (listaCancion != null) {
                    ListIterator<LongIntPair> iterator = listaCancion.listIterator();
                    synchronized(listaCancion){
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
                Thread.sleep(5);
                tiempoViejo = System.nanoTime();
                tiempoPausaNuevo = System.nanoTime() - tiempoRelativo;
                tiempoPausa = tiempoPausaNuevo + tiempoViejoPausa;
            }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try{
            Salirdeljuego(grabando);
        }catch(IOException e){
            e.printStackTrace();
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
        removeAll();
        pintarbase();
        revalidate();
        repaint();
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

        if(multiplayer){
            hiloJuego = new Thread(new Runnable() {
                @Override
                public void run() {
                    
                    CicloPrincipalMultijugador();
                }
            });  
            hiloJuego.start();
        }else{
            hiloJuego = new Thread(new Runnable(){
                @Override
                public void run() {
                    CicloPrincipalJuego();
                }
            });
            hiloJuego.start();
        }
        
    }
    public void IniciarGrabacion(int cancion) throws IOException{
        removeAll();
        pintarbase();
        revalidate();
        repaint();
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
    public void Salirdeljuego(boolean GuardarGrabacion) throws IOException {
        
        synchronized (fichas) {
            fichas.clear();
        }
        if (GuardarGrabacion) {
            switch (this.canciongrab) {
                case 1:
                    cancion1 = listaCancion;
                    guardarDatosCancion(cancion1, new File("cancion1.txt"));
                    break;
                case 2:
                    cancion2 = listaCancion;
                    guardarDatosCancion(cancion2, new File("cancion2.txt"));
                    break;
                case 3:
                    cancion3 = listaCancion;
                    guardarDatosCancion(cancion3, new File("cancion3.txt"));
                    break;
                default:
                    break;
            }
        }
       
        try{
            if(hiloJuego!=null && hiloJuego.isAlive()){
                hiloJuego.interrupt();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(!GuardarGrabacion){
        videoPanel.setVisible(false);
        setVisible(false);
        menu.puntaje.setText("Puntaje: " + score);
        menu.fails.setText("Fails: " + fails);
        menu.finalscore.setVisible(true);
        }else{
            videoPanel.setVisible(false);
            setVisible(false);
            menu.menuPanel.setVisible(true);
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
        if(!multiplayer){
            Ficha ficha = new Ficha(tiempo,columna,this);
            fichas.add(ficha);
        }else{
            Ficha ficha = new Ficha(tiempo,columna,this,"Jugador1");
          //  Ficha ficha2 = new Ficha(tiempo,columna,this,"Jugador2");
            fichas.add(ficha);
          //  fichas.add(ficha2);
        }
    }
    public void crearFicha2(long tiempo,int columna){
        Ficha ficha = new Ficha(tiempo,columna,this,"Jugador2");
        fichas2.add(ficha);
    }
    public void crearFichaAbajo(long tiempo, int columna){
        Ficha ficha = new Ficha(tiempo,columna,this,"Grabacion");
        fichas.add(ficha);
    }

    public void pintarbase() {
        if (multiplayer) {
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.weighty = 1;
            c.anchor = GridBagConstraints.SOUTH;
        
            amarillo = new BotonBase(amarillobase); 
            add(amarillo, c);
        
            c.gridx++;
        
            azul = new BotonBase(azulbase);
            add(azul, c);
        
            c.gridx++;
        
            rojo = new BotonBase(rojobase);
            add(rojo, c);
        
            c.gridx++;
        
            verde = new BotonBase(verdebase);
            add(verde, c);
            amarillo.requestFocus();
        
            c.gridx++;  // Incrementar la fila para el segundo conjunto de botones
            c.insets = new Insets(0, 160, 0, 0);
            amarillo2 = new BotonBase(amarillobase);
            add(amarillo2, c);  
            c.insets = new Insets(0, 0, 0, 0);
            c.gridx++;
            azul2 = new BotonBase(azulbase);
            add(azul2, c);

            c.gridx++;   
            rojo2 = new BotonBase(rojobase);
            add(rojo2, c);
    
            c.gridx++;
    
            verde2 = new BotonBase(verdebase);
            add(verde2, c);
    
            amarillo2.requestFocus();
        }else{
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.weighty = 1;
            c.anchor = GridBagConstraints.SOUTH;
        
            amarillo = new BotonBase(amarillobase); 
            add(amarillo, c);
        
            c.gridx++;
        
            azul = new BotonBase(azulbase);
            add(azul, c);
        
            c.gridx++;
        
            rojo = new BotonBase(rojobase);
            add(rojo, c);
        
            c.gridx++;
        
            verde = new BotonBase(verdebase);
            add(verde, c);
            amarillo.requestFocus();
        
        }
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!grabando){
        if(multiplayer){
            g.drawImage(scorebackground, 80, 550, 180,60, null);
            dibujarStats(g, Integer.toString(score), 100, 588);
    
            g.setColor(Color.WHITE);
            g.setFont(customFont2.deriveFont(20f));
            g.drawString("x", 130, 548);
            g.setFont(customFont2.deriveFont(45f));
            g.drawString(Integer.toString(combo), 150, 548);

            g.drawImage(scorebackground, 1110, 550, 180,60, null);
            dibujarStats(g, Integer.toString(score2), 1130, 588);

            g.setColor(Color.WHITE);
            g.setFont(customFont2.deriveFont(20f));
            g.drawString("x", 1160, 548);
            g.setFont(customFont2.deriveFont(45f));
            g.drawString(Integer.toString(combo2), 1180, 548);


        }else{
            g.drawImage(scorebackground, 250, 550, 180,60, null);
            dibujarStats(g, Integer.toString(score), 270, 588);
            g.setColor(Color.WHITE);
            g.setFont(customFont2.deriveFont(20f));
            g.drawString("x", 300, 548);
            g.setFont(customFont2.deriveFont(45f));
            g.drawString(Integer.toString(combo), 330, 548);
        }


        if(multiplayer){
            if(mensaje=="¡Bien!"){
                dibujarmensaje(g,Color.GREEN,mensaje, getWidth()/3 - fichaheight, getHeight()/2 - fichaheight);
            }else if(mensaje=="¡Perfecto!"){
                dibujarmensaje(g,Color.BLUE,mensaje, getWidth()/3 - fichaheight, getHeight()/2 - fichaheight);
            }else{
                dibujarmensaje(g,Color.RED,mensaje, getWidth()/3 - fichaheight, getHeight()/2 - fichaheight);
            }
            if(mensaje2=="¡Bien!"){
                dibujarmensaje(g,Color.GREEN,mensaje2, (getWidth()/4)*3 - fichaheight, getHeight()/2 - fichaheight);
            }else if(mensaje2=="¡Perfecto!"){
                dibujarmensaje(g,Color.BLUE,mensaje2, (getWidth()/4)*3 - fichaheight, getHeight()/2 - fichaheight);
            }else{
                dibujarmensaje(g,Color.RED,mensaje2, (getWidth()/4)*3 - fichaheight, getHeight()/2 - fichaheight);
            }
    
        }else{
            
        if(mensaje=="¡Bien!"){
            dibujarmensaje(g,Color.GREEN,mensaje, getWidth()/2 - fichaheight+20, getHeight()/2 - fichaheight);
        }else if(mensaje=="¡Perfecto!"){
            dibujarmensaje(g,Color.BLUE,mensaje, getWidth()/2 - fichaheight+20, getHeight()/2 - fichaheight);
        }else{
            dibujarmensaje(g,Color.RED,mensaje, getWidth()/2 - fichaheight+20, getHeight()/2 - fichaheight);
        }

        }
        }else{
            //Dibujar TiempoTranscurrido
            g.setColor(Color.WHITE);
            g.setFont(customFont2.deriveFont(20f));
            g.drawString("Tiempo: " + tiempotranscurrido/1_000_000_000, 270, 588);
        }
        List<Ficha> fichasCopia;
        synchronized (fichas) {
            fichasCopia = new ArrayList<>(fichas);
        }
        
        for (Ficha ficha : fichasCopia) {
            g.drawImage(fichaimagenes[ficha.columna],(int)ficha.x,(int) ficha.y, 80, 50, null);
        }
        List<Ficha> fichasCopia2;
        synchronized (fichas2) {
            fichasCopia2 = new ArrayList<>(fichas2);
        }
        for(Ficha ficha : fichasCopia2){
            g.drawImage(fichaimagenes[ficha.columna],(int)ficha.x,(int) ficha.y, 80, 50, null);
        }
    }
    private void dibujarmensaje(Graphics g,Color color, String mensaje, int x, int y){
        g.setColor(color);
        g.setFont(customFont.deriveFont(20f));
        g.drawString(mensaje, x, y);
    }
    private void dibujarStats(Graphics g, String mensaje, int x, int y){
        g.setColor(Color.GREEN);
        g.setFont(customFont2.deriveFont(20f));
        g.drawString(mensaje, x, y);
    }
 
    public void setPausa(boolean pausa){
        enPausa = pausa;
    }
    public void setMultiplayer(boolean multiplayer){
        this.multiplayer = multiplayer;
    }   
    public boolean getMultiplayer(){
        return multiplayer;
    }
    public boolean getGrabando(){
        return grabando;
    }
    public int getFinalscore(){
        return score;
    }
    public int getFails(){
        return fails;
    }
    private void procesarFichaPresionada2(BotonBase color,boolean press, Iterator<Ficha> iterator) {
        if (color.getPresionado() && !press) {
            switch(consecutivas2){
                case 20:
                    combo2 = 2;
                    break;
                case 40:
                    combo2 = 3;
                    break;
                case 70:
                    combo2 = 4;
                    break;
            }
            score2 += aumento * combo2;
            consecutivas2++;
            failsconsecutivas2= 0;
            if (perfecto) {
                mensaje2= "¡Perfecto!";
            } else {
                mensaje2= "¡Bien!";
            }
            if(color == amarillo2){
                amaractiv2 = false;
            }
            if(color == azul2){
                azuactiv2 = false;
            }
            if(color == rojo2){
                rojoactiv2 = false;
            }
            if(color == verde2){
                veractiv2 = false;
            }
            iterator.remove();
        }
    }
    private void procesarFichaPresionada(BotonBase color,boolean press,Iterator<Ficha> iterator) {
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
