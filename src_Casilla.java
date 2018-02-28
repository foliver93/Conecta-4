public class Casilla {
    private Jugador jugador;
    private final int fila;
    private final int columna;

    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        jugador = null;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setJugador(Jugador jugador) {
        if (this.jugador == null){
            this.jugador = jugador;
        }
    }
}
