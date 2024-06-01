package Proyecto_Programacion;

public class Ficha {
    float y;
    float x;
    float ventantigua;
    double vy;
    int columna;
    public long tiempo;
    public Juego juego;
    public Ficha(long tiempo,int columna,Juego juego, boolean b){
        this.y=juego.getHeight()-Juego.fichaheight;
        this.ventantigua = juego.getHeight()-Juego.fichaheight;
        this.vy=300;
        this.columna=columna;
        this.juego=juego;
        this.tiempo=tiempo;
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
        this.y=juego.getHeight()/2;
        this.ventantigua = juego.getHeight() /2;
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
  
    public void fisica(float dt){
        this.y+=this.vy*dt;
    }
    public void fisicaB(float dt){
        this.y-=this.vy*dt;
    }

}
