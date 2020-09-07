package Teste;

import Arquivo.Arquivo;
import Lexico.Lexico;
import Token.Token;

public class Teste {

    Arquivo arquivo = new Arquivo();
    Lexico lexico = new Lexico();

    public void start() throws Exception {
        arquivo.carregaArquivo();
        printArquivo();
        separaToken();
        print();
    }

    public void printArquivo() {
        System.out.println("Conteudo arquivo: " + arquivo.getLinha());
    }

    public void separaToken(){
        lexico.separaConteudo(arquivo.getLinha());
    }

    public void print() {
        System.out.println("Simbolos lexico: " + lexico.getTokens());
    }
}
