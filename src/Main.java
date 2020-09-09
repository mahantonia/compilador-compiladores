import Compilador.Compilador;
import Interface.Interface;

public class Main {
    public static void main (String[] args) {
        System.out.println("Compilador");

        Compilador compilador = new Compilador();

        compilador.start();
    }
}
