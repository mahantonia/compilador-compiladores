package Token;

import java.util.ArrayList;

public class Token {
    String simbolo;
    String lexema;
    String linha;

    public Token(String simbolo, String lexema, String linha) {
        this.simbolo = simbolo;
        this.lexema = lexema;
        this.linha = linha;
    }

    public Token() { }

    public ArrayList<Token> listaToken = new ArrayList<>();

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

    public ArrayList<Token> getListaToken() { return listaToken; }

    public void addListaToken(Token conteudo) { listaToken.add(conteudo); }
}
