package Operador;

import java.util.List;

public class Operador {
    private int prioridade;
    private int quantidadeOperadores;
    private List<String> simboloRequerido;
    private String simboloResultante;

    public Operador(int prioridade, int quantidadeOperadores, List<String> simboloRequerido, String simboloResultante) {
        this.prioridade = prioridade;
        this.quantidadeOperadores = quantidadeOperadores;
        this.simboloRequerido = simboloRequerido;
        this.simboloResultante = simboloResultante;
    }

    public int getPrioridade() { return prioridade; }

    public int getQuantidadeOperadores() { return quantidadeOperadores; }

    public List<String> getSimboloRequerido() { return simboloRequerido; }

    public String getSimboloResultante() { return simboloResultante; }
}
