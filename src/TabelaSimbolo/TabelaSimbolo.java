package TabelaSimbolo;

import Escopo.Escopo;
import Simbolo.Simbolo;
import Tipo.Tipo;
import Token.Token;

import java.util.ArrayList;

public class TabelaSimbolo {
    private ArrayList<Simbolo> tabelaSimbolo;

    public TabelaSimbolo() {
        this.tabelaSimbolo = new ArrayList<>();
    }

    public void insereTabela(Simbolo valor) {
        tabelaSimbolo.add(valor);
    }

    public void atriuirTipoVariaveis(Escopo escopo, Tipo tipo) {
        for(int i = 0; i < tabelaSimbolo.size(); i++){
            if(tabelaSimbolo.get(i).getTipo().getTipoValor().equals("variavel") &&
                    tabelaSimbolo.get(i).getEscopo().equals(escopo)
            ){
                tabelaSimbolo.get(i).setTipo(tipo);
            }
        }
    }

    public boolean pesquisaDuplicidadeVariavelTabela(String lexema, Escopo escopo) {
        for(int i = 0; i < tabelaSimbolo.size(); i++) {
           if(tabelaSimbolo.get(i).getToken().getLexema().equals(lexema)){
               if(  tabelaSimbolo.get(i).getTipo().getTipoValor().equals("variavel") ||
                    tabelaSimbolo.get(i).getTipo().getTipoValor().equals("variavelInteiro") ||
                    tabelaSimbolo.get(i).getTipo().getTipoValor().equals("variavelBoolean")
               ){
                   if(tabelaSimbolo.get(i).getEscopo().getNivel() == escopo.getNivel()) {
                       return false;
                   }
               }
           }
        }
        return true;
    }

    public boolean pesquisaDuplicidadeProcedimentoTabela(String lexema) {
        for(int i = 0; i < tabelaSimbolo.size(); i++) {
            if(tabelaSimbolo.get(i).getToken().getLexema().equals(lexema)){
                if(  tabelaSimbolo.get(i).getTipo().getTipoValor().equals("procedimento")){
                    return true;
                }
            }
        }
        return true;
    }

    public void colocaTipoTabela(Escopo nivel, Tipo tipo) {
        for(int i = 0; i <tabelaSimbolo.size(); i++) {
            if(tabelaSimbolo.get(i).getEscopo().equals(nivel)) {
                if(tabelaSimbolo.get(i).getTipo().getTipoValor().equals("variavel")) {
                    tabelaSimbolo.get(i).setTipo(tipo);
                }
            }
        }
    }

    public boolean pesquisaDeclaracaoVariavelTabela(String lexema) {
        for(int i = 0; i < tabelaSimbolo.size(); i++) {
            if(tabelaSimbolo.get(i).getToken().getLexema().equals(lexema)) {
                if( tabelaSimbolo.get(i).getTipo().getTipoValor().equals("variavel") ||
                    tabelaSimbolo.get(i).getTipo().getTipoValor().equals("variavelBoolean") ||
                    tabelaSimbolo.get(i).getTipo().getTipoValor().equals("variavelInteiro")
                ) {
                   return true;
                }
            }
        }
        return false;
    }

    public boolean pesquisaDeclaracaoFuncaoTabela(String lexema) {
        for(int i = 0; i < tabelaSimbolo.size(); i++) {
            if(tabelaSimbolo.get(i).getToken().getLexema().equals(lexema)) {
                if(tabelaSimbolo.get(i).getTipo().getTipoValor().equals("funcaoBoolean") ||
                        tabelaSimbolo.get(i).getTipo().getTipoValor().equals("funcaoInteiro")
                ) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean pesquisaDeclaracaoProcedimentoTabela(String lexema) {
        for(int i = 0; i < tabelaSimbolo.size(); i++) {
            if(tabelaSimbolo.get(i).getToken().getLexema().equals(lexema)) {
                if(tabelaSimbolo.get(i).getTipo().getTipoValor().equals("procedimento")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean pesquisaTabela(String lexema, Escopo escopo) {
        for (int i = 0; i < tabelaSimbolo.size(); i++) {
            if (tabelaSimbolo.get(i).getToken().getLexema().equals(lexema)) {
                if (tabelaSimbolo.get(i).getEscopo().getNivel() >= escopo.getNivel()) {
                    return true;
                } else {
                    if (tabelaSimbolo.get(i).getEscopo().getNivel() == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void excluiValorTabela(Escopo escopo) {
        for(int i = 0; i < tabelaSimbolo.size(); i++) {
            if(tabelaSimbolo.get(i).getEscopo().getNivel() == escopo.getNivel()) {
                if( !tabelaSimbolo.get(i).getTipo().getTipoValor().equals("procedimento") &&
                    !tabelaSimbolo.get(i).getTipo().getTipoValor().equals("funcaoBoolean") &&
                    !tabelaSimbolo.get(i).getTipo().getTipoValor().equals("funcaoInteiro")) {
                    tabelaSimbolo.remove(i);
                    i = 0;
                }
            }
        }

        for(int i = 0; i < tabelaSimbolo.size(); i++) {
            if(tabelaSimbolo.get(i).getEscopo().getNivel() == escopo.getNivel()+1) {
                if(tabelaSimbolo.get(i).getTipo().getTipoValor().equals("procedimento") ||
                    tabelaSimbolo.get(i).getTipo().getTipoValor().equals("funcaoBoolean") ||
                    tabelaSimbolo.get(i).getTipo().getTipoValor().equals("funcaoInteiro")) {
                    tabelaSimbolo.remove(i);
                    i = 0;
                }
            }
        }
    }

    public String pegaTipo(Token token) {
       for(int i = 0; i < tabelaSimbolo.size(); i++) {
           if(tabelaSimbolo.get(i).getToken().getLexema().equals(token.getLexema())) {
               return tabelaSimbolo.get(i).getTipo().getTipoValor();
           }
       }

       return "LANCAR EXCECAO !!!!!" ;
    }
}
