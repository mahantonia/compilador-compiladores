package Token;

import java.util.ArrayList;

public class Token {
    String simbolo;
    String lexema;
    String linha;

    public ArrayList<String> listaToken = new ArrayList<>();

    public String getSimbolo() { return simbolo; }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getLinha() {
        return linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    public ArrayList<String> getListaToken() { return listaToken; }

    public void addListaToken(String conteudo) { listaToken.add(conteudo); }
}
