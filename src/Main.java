import Compilador.Compilador;

public class Main {
    public static void main (String[] args) throws Exception {
        System.out.println("Compilador");

        Compilador compilador = new Compilador();

        compilador.start();
    }
}
