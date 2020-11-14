package Simbolo;

import Escopo.Escopo;
import Tipo.Tipo;

public class Simbolo {
    private String lexema;
    private Escopo escopo;
    private Tipo tipo;
//    private Memoria memoria;

    public Simbolo(String lexema, Escopo escopo, Tipo tipo) {
        this.lexema = lexema;
        this.escopo = escopo;
        this.tipo = tipo;
//        this.memoria = memoria;
    }

    public String getLexema() { return lexema; }

    public Escopo getEscopo() { return escopo; }

    public Tipo getTipo() { return tipo; }

    public void setTipo(Tipo tipo) { this.tipo = tipo; }

//    public Memoria getMemoria() { return memoria; }
}
