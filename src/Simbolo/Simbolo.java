package Simbolo;

import Escopo.Escopo;
import Tipo.Tipo;
import Token.Token;

public class Simbolo {
    private Token token;
    private Escopo escopo;
    private Tipo tipo;
//    private Memoria memoria;

    public Simbolo(Token token, Escopo escopo, Tipo tipo) {
        this.token = token;
        this.escopo = escopo;
        this.tipo = tipo;
//        this.memoria = memoria;
    }

    public Simbolo() {
        this.token = new Token();
        this.tipo = new Tipo();
    }

    public Token getToken() { return token; }

    public Tipo getTipo() { return tipo; }

    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public Escopo getEscopo() { return escopo; }

//    public Memoria getMemoria() { return memoria; }
}
