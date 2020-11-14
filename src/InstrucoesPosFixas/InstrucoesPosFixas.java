package InstrucoesPosFixas;

import Operador.Operador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstrucoesPosFixas {
    Map<String, Operador> listaPrioridades = new HashMap<>();

    public InstrucoesPosFixas() {
        criaTabelaPrioridades();
    }

    public void criaTabelaPrioridades() {
        listaPrioridades.put("smenosu", new Operador(6, 1, List.of("sinteiro"), "sinteiro"));
        listaPrioridades.put( "snao", new Operador(5, 1, List.of("sbooleano"), "sbooleano"));
        listaPrioridades.put( "smult", new Operador(4, 2, List.of("sinteiro"), "sinteiro"));
        listaPrioridades.put( "sdiv", new Operador(4, 2, List.of("sinteiro"), "sinteiro"));
        listaPrioridades.put( "smais", new Operador(3, 2, List.of("sinteiro"), "sinteiro"));
        listaPrioridades.put( "smenos", new Operador(3, 2, List.of("sinteiro"), "sinteiro"));
        listaPrioridades.put( "smenor", new Operador(2, 2, List.of("sinteiro"), "sbooleano"));
        listaPrioridades.put( "smaior", new Operador(2, 2, List.of("sinteiro"), "sbooleano"));
        listaPrioridades.put( "smaiorig", new Operador(2, 2, List.of("sinteiro"), "sbooleano"));
        listaPrioridades.put( "smenorig", new Operador(2, 2, List.of("sinteiro"), "sbooleano"));
        /*Perguntar para a dani se o igual e o diferente sao realmente bool ou podem ser inteiros */
        listaPrioridades.put( "sig", new Operador(2, 2, List.of("sbooleano", "sinteiro"), "sbooleano"));
        listaPrioridades.put( "sdif", new Operador(2, 2, List.of("sbooleano", "sinteiro"), "sbooleano"));
        listaPrioridades.put( "se", new Operador(1, 2, List.of("sbooleano"), "sbooleano"));
        listaPrioridades.put( "sou", new Operador(0, 2, List.of("sbooleano"), "sbooleano"));
    }

    public int retornaValorPrioridade(String simbolo) {
        return listaPrioridades.get(simbolo).getPrioridade();
    }

    public int retornaQuantidadeOperadores(String simbolo) {
        return listaPrioridades.get(simbolo).getQuantidadeOperadores();
    }

    public List<String> retornaTipoSimboloRequerido(String simbolo) {
        return listaPrioridades.get(simbolo).getSimboloRequerido();
    }

    public String retornaTipoSimboloResultante(String simbolo) {
        return listaPrioridades.get(simbolo).getSimboloResultante();
    }

    public boolean existeExpressao(String simbolo) {
        return listaPrioridades.containsKey(simbolo);
    }
}
