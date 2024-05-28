package Proyecto_Programacion;

public class LongIntPair {
    private long first;
    private int second;
    private boolean disponible;
    public LongIntPair(long first, int second) {
        this.first = first;
        this.second = second;
        this.disponible = true; 
    }

    public boolean getDisponible() {
        return disponible;
    }
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    public long getFirst() {
        return first;
    }

    public void setFirst(long first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }


}
