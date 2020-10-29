package Simbolo;

import Escopo.Escopo;
import Memoria.Memoria;
import Tipo.Tipo;
import Token.Token;

public class Simbolo {
    private Token lexema;
    private Escopo escopo;
    private Tipo tipo;
    private Memoria memoria;

    public Simbolo(Token lexema, Escopo escopo, Tipo tipo, Memoria memoria) {
        this.lexema = lexema;
        this.escopo = escopo;
        this.tipo = tipo;
        this.memoria = memoria;
    }

    public Token getLexema() { return lexema; }

    public Escopo getEscopo() { return escopo; }

    public Tipo getTipo() { return tipo; }

    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public Memoria getMemoria() { return memoria; }
}
