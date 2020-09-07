package Compilador;

import Arquivo.Arquivo;
import Lexico.Lexico;

public class Compilador {

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
        try {
            lexico.separaConteudo(arquivo.getLinha());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void print() {
        System.out.println("Simbolos lexico: " + lexico.getTokens());
    }
}
