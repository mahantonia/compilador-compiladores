package TabelaSimbolo;

import Escopo.Escopo;
import Simbolo.Simbolo;
import Token.Token;

import java.util.ArrayList;

public class TabelaSimbolo {
    private ArrayList<Simbolo> tabelaSimbolo;

    public TabelaSimbolo() {
        this.tabelaSimbolo = new ArrayList<Simbolo>();
    }

    public void insereTabela(Simbolo valor) {
        tabelaSimbolo.add(valor);
    }

    public boolean pesquisaDuplicidadeVariavelTabela(String lexema) {
        for(int i = 0; i < tabelaSimbolo.size(); i++) {
            if(tabelaSimbolo.get(i).getLexema().equals(lexema)) {
                if((tabelaSimbolo.get(i).getTipo().getTipoValor().equals("variavel"))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void colocaTipoTabela(Simbolo tipo) {
        tabelaSimbolo.add(tipo);
    }

    public boolean pesquisaDeclaracaoVariavelTabela(String lexema) {
        for(int i = 0; i < tabelaSimbolo.size(); i++) {
            if(tabelaSimbolo.get(i).getLexema().equals(lexema)) {
                return true;
            }
        }
        return false;
    }

    public boolean pesquisaDeclaracaoFuncaoTabela(String lexema) {
        for(int i = 0; i < tabelaSimbolo.size(); i++) {
            if(tabelaSimbolo.get(i).getLexema().equals(lexema)) {
                if(tabelaSimbolo.get(i).getTipo().getTipoValor().equals("funcaosbooleano") || tabelaSimbolo.get(i).getTipo().getTipoValor().equals("funcaosinteiro")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean pesquisaDeclaracaoProcedimentoTabela(String lexema) {
        for(int i = 0; i < tabelaSimbolo.size(); i++) {
            if(tabelaSimbolo.get(i).getLexema().equals(lexema)) {
                if(tabelaSimbolo.get(i).getTipo().getTipoValor().equals("procedimento")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean pesquisaTabela(String lexema, Escopo escopo) {
        for(int i = 0; i < tabelaSimbolo.size(); i++) {
            if(tabelaSimbolo.get(i).getLexema().equals(lexema)) {
                if(tabelaSimbolo.get(i).getEscopo().getNivel() >= escopo.getNivel()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void excluiValorTabela(Escopo escopo) {

        for(int i = 0; i < tabelaSimbolo.size(); i++) {
            if(tabelaSimbolo.get(i).getEscopo().getNivel() == escopo.getNivel()) {
                if(!tabelaSimbolo.get(i).getTipo().getTipoValor().equals("procedimento")) {
                    tabelaSimbolo.remove(i);
                    i = 0;
                }
            }
        }

        for(int i = 0; i < tabelaSimbolo.size(); i++) {
            if(tabelaSimbolo.get(i).getEscopo().getNivel() == escopo.getNivel()+1) {
                if(tabelaSimbolo.get(i).getTipo().getTipoValor().equals("procedimento")) {
                    tabelaSimbolo.remove(i);
                    i = 0;
                }
            }
        }
    }



}
