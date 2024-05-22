package Proyecto_Programacion;

public class LongIntPair {
    private long first;
    private int second;
    private boolean processed; // Campo para indicar si el par ha sido procesado
    public LongIntPair(long first, int second) {
        this.first = first;
        this.second = second;
        this.processed = false; // Por defecto, el par no ha sido procesado
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

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
