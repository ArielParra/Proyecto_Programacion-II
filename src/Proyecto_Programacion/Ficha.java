package Proyecto_Programacion;

public class Ficha {
    float y;
    float x;
    float ventantigua;
    double vy;
    int columna;
    public Juego juego;
    public Ficha(int columna,Juego juego, boolean b){
        this.y=juego.getHeight()-50;
        this.ventantigua = juego.getHeight()-50;
        this.vy=300;
        this.columna=columna;
        this.juego=juego;
        switch(columna){
            case 0:
                this.x=juego.getWidth()/2 - ( 50 * 2);
                break;
            case 1:
                this.x=juego.getWidth()/2 - 50;
                break;
            case 2:
                this.x=juego.getWidth()/2;
                break;
            case 3:
                this.x=juego.getWidth()/2 + 50;
                break;
        }
    }
    public Ficha(int columna,Juego juego){
        this.y=juego.getHeight()/2;
        this.ventantigua = juego.getHeight() /2;
        this.vy=300;
        this.columna=columna;
        this.juego=juego;
        switch(columna){
            case 0:
                this.x=juego.getWidth()/2 - ( 50 * 2);
                break;
            case 1:
                this.x=juego.getWidth()/2 - 50;
                break;
            case 2:
                this.x=juego.getWidth()/2;
                break;
            case 3:
                this.x=juego.getWidth()/2 + 50;
                break;
        }
    }
    public void revalidateposition(){
        switch(columna){
            case 0:
                this.x=juego.getWidth()/2 - ( 50 * 2);
                break;
            case 1:
                this.x=juego.getWidth()/2 - 50;
                break;
            case 2:
                this.x=juego.getWidth()/2;
                break;
            case 3:
                this.x=juego.getWidth()/2 + 50;
                break;
        }
        this.y = this.y - ventantigua;
        this.y = juego.getHeight() - this.y; 
    }
    public void fisica(float dt){
        this.y+=this.vy*dt;
    }
    public void fisicaB(float dt){
        this.y-=this.vy*dt;
    }

}
