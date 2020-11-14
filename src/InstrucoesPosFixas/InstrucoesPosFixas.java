package InstrucoesPosFixas;

import Operador.Operador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstrucoesPosFixas {
    Map<String, Operador> listaPrioridades = new HashMap<>();
    List<String> operadoresInteiro =  List.of(  "sinteiro",
            "variavelInteiro",
            "funcaoInteiro",
            "snumero"
    );
    List<String> operadoresBoolean =  List.of(  "sbooleano",
            "variavelBoolean",
            "funcaoBoolean",
            "sverdadeiro",
            "sfalso"
    );

    public InstrucoesPosFixas() {
        criaTabelaPrioridades();
    }

    public void criaTabelaPrioridades() {
        listaPrioridades.put("smenosu", new Operador(6, 1, operadoresInteiro, "sinteiro"));
        listaPrioridades.put( "snao", new Operador(5, 1, operadoresBoolean, "sboolean"));
        listaPrioridades.put( "smult", new Operador(4, 2, operadoresInteiro, "sinteiro"));
        listaPrioridades.put( "sdiv", new Operador(4, 2, operadoresInteiro, "sinteiro"));
        listaPrioridades.put( "smais", new Operador(3, 2, operadoresInteiro, "sinteiro"));
        listaPrioridades.put( "smenos", new Operador(3, 2, operadoresInteiro, "sinteiro"));
        listaPrioridades.put( "smenor", new Operador(2, 2, operadoresInteiro, "sboolean"));
        listaPrioridades.put( "smaior", new Operador(2, 2, operadoresInteiro, "sboolean"));
        listaPrioridades.put( "smaiorig", new Operador(2, 2, operadoresInteiro, "sboolean"));
        listaPrioridades.put( "smenorig", new Operador(2, 2, operadoresInteiro, "sboolean"));
        listaPrioridades.put( "sig", new Operador(2, 2, operadoresInteiro , "sboolean"));
        listaPrioridades.put( "sdif", new Operador(2, 2,operadoresInteiro, "sboolean"));
        listaPrioridades.put( "sigB", new Operador(2, 2, operadoresBoolean , "sboolean"));
        listaPrioridades.put( "sdifB", new Operador(2, 2,operadoresBoolean, "sboolean"));
        listaPrioridades.put( "se", new Operador(1, 2, operadoresBoolean, "sboolean"));
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

    public List<String> getOperadoresInteiro() { return operadoresInteiro; }

    public List<String> getOperadoresBoolean() { return operadoresBoolean; }
}
