package Proyecto_Programacion;

public class Ficha {
    float y;
    float x;
    double vy;
    int columna;
    public long tiempo;
    public Juego juego;
    public Ficha(long tiempo,int columna,Juego juego, String Tipo){
        this.vy=300;
        this.columna=columna;
        this.juego=juego;
        this.tiempo=tiempo;
        switch(Tipo){
            case "Jugador1":
                Fichajugador1();
                break;
            case "Jugador2":
                Fichajugador2();
                break;
            case "Grabacion":
                FichaGrabacion();
                break;
            default:

        }
    }
    public void FichaGrabacion(){
         this.y=juego.getHeight()-Juego.fichaheight;
        switch(columna){
            case 0:
                this.x=juego.getWidth()/2 - (Juego.fichawidth * 2);
                break;
            case 1:
                this.x=juego.getWidth()/2 - Juego.fichawidth;
                break;
            case 2:
                this.x=juego.getWidth()/2;
                break;
            case 3:
                this.x=juego.getWidth()/2 + Juego.fichawidth;
                break;
        }
    }
    public Ficha(long tiempo,int columna,Juego juego){
        this.y=juego.getHeight() - 384;
        this.vy=300;
        this.columna=columna;
        this.juego=juego;
        this.tiempo=tiempo;
        switch(columna){
            case 0:
                this.x=juego.getWidth()/2 - ( Juego.fichawidth * 2);
                break;
            case 1:
                this.x=juego.getWidth()/2 - Juego.fichawidth;
                break;
            case 2:
                this.x=juego.getWidth()/2;
                break;
            case 3:
                this.x=juego.getWidth()/2 + Juego.fichawidth;
                break;
        }
    }
    public void Fichajugador1(){
        this.y=juego.getHeight() - 384;

        switch(columna){
            case 0:
                this.x=juego.getWidth()/2 - ( Juego.fichawidth * 4 + 80);
                break;
            case 1:
                this.x=juego.getWidth()/2 - (Juego.fichawidth * 3 + 80);
                break;
            case 2:
                this.x=juego.getWidth()/2 - (Juego.fichawidth * 2 + 80);
                break;
            case 3:
                this.x=juego.getWidth()/2 - (Juego.fichawidth * 1 + 80);
                break;
        }
    }
    public void Fichajugador2(){
        this.y=juego.getHeight()-384;

        switch(columna){
            case 0:
                this.x=juego.getWidth()/2 + 80;
                break;
            case 1:
                this.x=juego.getWidth()/2 + (Juego.fichawidth * 1) + 80;
                break;
            case 2:
                this.x=juego.getWidth()/2 + (Juego.fichawidth * 2) + 80;
                break;
            case 3:
                this.x=juego.getWidth()/2 + (Juego.fichawidth * 3 ) + 80;
                break;
        }
    }
    public void fisica(float dt){
        this.y+=this.vy*dt;
    }
    public void fisicaB(float dt){
        this.y-=this.vy*dt;
    }

}
