import java.util.Scanner;

public class Conecta4 {
    private int filas, columnas;
    private Casilla[][] tablero;
    private Jugador turno = null;
    private boolean finJuego = false;
    private final Scanner sc = new Scanner(System.in);
    private Jugador jugador1 = new Jugador(1);
    private Jugador jugador2 = new Jugador(2);

    public Conecta4() {
        crearPartida();
    }

    private void crearPartida(){
        System.out.println("Numero de filas: ");
        filas = sc.nextInt();
        System.out.println("Numero de columnas");
        columnas = sc.nextInt();
        tablero = new Casilla[filas][columnas];
        int rand = (int) Math.floor(Math.random() * 2);
        turno = rand == 0 ? jugador1 : jugador2;
        iniciarTablero();
        mostrarTablero();
        meterFichas();
    }

    private void iniciarTablero(){
        for (int i = 0; i < filas; i++){
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = new Casilla(i, j);
            }
        }
    }

    private void mostrarTablero(int x, int y){
        for (int i = 0; i < filas; i++){
            for (int j = 0; j < columnas; j++) {
                if (i == x && j == y)
                    System.out.print("\u001B[31m" + ((tablero[i][j].getJugador() == null) ? "0" : tablero[i][j].getJugador().getNumAsig()) + "   \u001B[0m");
                else
                    System.out.print(((tablero[i][j].getJugador() == null) ? "0" : tablero[i][j].getJugador().getNumAsig()) + "   ");
            }
            System.out.println();
        }
    }

    private void mostrarTablero() {
        mostrarTablero(-1, -1);
    }

    private Casilla getCasillaComprovar1(Casilla c) {
        if (c.getColumna() >= c.getFila())
            return tablero[0][c.getColumna() - c.getFila()];
        else
            return tablero[c.getFila() - c.getColumna()][0];
    }

    private Casilla getCasillaComprovar2(Casilla c) {
        if ((c.getColumna() + c.getFila()) >= (columnas - 1)) {
            return tablero[c.getFila() - (columnas - 1 - c.getColumna())][columnas - 1];
        } else {
            return tablero[0][c.getFila() + c.getColumna()];
        }
    }

    private boolean esVictoria(Casilla darreraCasilla){
        int contador = 0;
        //Horizontal
        for (int i = 0; i < columnas; i++) {
            if (tablero[darreraCasilla.getFila()][i].getJugador() == turno) {
                contador++;
                if (contador == 4) {
                    return true;
                }
            } else {
                contador = 0;
            }
        }
        //Vertical
        for (int i = 0; i < filas; i++) {
            if (tablero[i][darreraCasilla.getColumna()].getJugador() == turno) {
                contador++;
                if (contador == 4) {
                    return true;
                }
            } else {
                contador = 0;
            }
        }
        //Comprovació diagonal 1
        contador = 0;
        Casilla inici = getCasillaComprovar1(darreraCasilla);
        for (int fila = inici.getFila(), columna = inici.getColumna(); fila < filas && columna < columnas; fila++, columna++) {
            if (tablero[fila][columna].getJugador() == turno) {
                contador++;
                if (contador == 4)
                    return true;
            } else
                contador = 0;
        }

        //Comprovació diagonal 2
        contador = 0;
        inici = getCasillaComprovar2(darreraCasilla);
        for (int fila = inici.getFila(), columna = inici.getColumna(); fila < filas && columna >= 0; fila++, columna--) {
            if (tablero[fila][columna].getJugador() == turno) {
                contador++;
                if (contador == 4)
                    return true;
            } else
                contador = 0;
        }
        return false;
    }

    private boolean esEmpate(){
        for (int i = 0; i < filas; i++){
            for (int j = 0; j < columnas; j++){
                if (tablero[i][j].getJugador() == null){
                    return false;
                }
            }
        }
        return true;
    }

    private void meterFichas(){
        while (!finJuego) {
            System.out.println("Introduzca el numero de la columna: ");
            int numCol = (sc.nextInt() - 1);
            if (numCol <= columnas) {
                Casilla darreraCasilla = getDarreraDisponible(numCol);
                if (darreraCasilla != null) {
                    darreraCasilla.setJugador(turno);
                    mostrarTablero(darreraCasilla.getFila(), numCol);
                    if(esVictoria(darreraCasilla)){
                        finJuego = true;
                        System.out.println("El jugador " +turno.getNumAsig() + " ha ganado.");
                    }
                    else if (esEmpate()){
                        finJuego = true;
                        System.out.println("Hay empate.");
                    }
                    turno = turno == jugador1 ? jugador2 : jugador1;
                } else {
                    mostrarTablero();
                    System.out.println("La columna esta llena");
                }
            } else {
                mostrarTablero();
                System.out.println("El numero debe ser menor o igual que " + columnas);
            }
        }
    }

    private Casilla getDarreraDisponible(int columna){
        for (int i = filas - 1; i >= 0; i--){
            if (tablero[i][columna].getJugador() == null) {
                return tablero[i][columna];
            }
        }
        return null;
    }
}