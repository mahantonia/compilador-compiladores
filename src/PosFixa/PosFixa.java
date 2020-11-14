package PosFixa;

import InstrucoesPosFixas.InstrucoesPosFixas;
import TabelaSimbolo.TabelaSimbolo;
import Token.Token;

import java.util.ArrayList;
import java.util.List;

public class PosFixa {
    ArrayList<Token> pilha;
    ArrayList<Token> expressaoFinalPosFixa;
    InstrucoesPosFixas instrucoesPosFixas;
    TabelaSimbolo tabelaSimbolo;

    public PosFixa(TabelaSimbolo tabelaSimbolo) {
        pilha = new ArrayList<>();
        expressaoFinalPosFixa = new ArrayList<>();
        instrucoesPosFixas = new InstrucoesPosFixas();
        this.tabelaSimbolo = tabelaSimbolo;
    }

    public Token getTopoPilha() {
        if(pilha.size() == 1) {
            return pilha.get(0);
        } else {
            return pilha.get(pilha.size() - 1);
        }
    }

    public void removeTopoPilha() {
        if(pilha.size() == 1) {
            pilha.remove(0);
        } else {
            pilha.remove(pilha.size() - 1);
        }
    }

    public void adiconarPosFixa(Token token) {
        if(token.getSimbolo().equals("sabre_parenteses")) {
            pilha.add(token);
        } else {
            if(token.getSimbolo().equals("sfecha_parenteses")) {
                while (!pilha.isEmpty()){
                    if(!getTopoPilha().getSimbolo().equals("sabre_parenteses")) {
                        expressaoFinalPosFixa.add(getTopoPilha());
                        removeTopoPilha();
                    } else {
                        removeTopoPilha();
                    }
                }
            } else {
                if(instrucoesPosFixas.retornaValorPrioridade(getTopoPilha().getSimbolo()) < instrucoesPosFixas.retornaValorPrioridade(token.getSimbolo()) ) {
                    pilha.add(token);
                } else {
                    if(instrucoesPosFixas.retornaValorPrioridade(getTopoPilha().getSimbolo()) > instrucoesPosFixas.retornaValorPrioridade(token.getSimbolo())) {
                        while (!pilha.isEmpty()) {
                            if(instrucoesPosFixas.retornaValorPrioridade(getTopoPilha().getSimbolo()) > instrucoesPosFixas.retornaValorPrioridade(token.getSimbolo())) {
                                expressaoFinalPosFixa.add(getTopoPilha());
                                removeTopoPilha();
                            } else {
                                break;
                            }
                        }
                    } else {
                        expressaoFinalPosFixa.add(token);
                    }
                }
            }
        }
    }

    public ArrayList<Token> getPosFixa() {
        while (!pilha.isEmpty()) {
            expressaoFinalPosFixa.add(getTopoPilha());
            removeTopoPilha();
        }

        return  expressaoFinalPosFixa;
    }

    public void limparPosFixa() {
        pilha.clear();
        expressaoFinalPosFixa.clear();
    }


    public String pegaTipoExpressao() {
        int i = 0;
        boolean tipoCorreto = false;
        Token tokenAuxiliar = new Token();
        List<String> tiposRequeritos;

        getPosFixa();

        while (expressaoFinalPosFixa.size() != 1) {
            if(instrucoesPosFixas.existeExpressao(expressaoFinalPosFixa.get(i).getSimbolo())){
                int quantidadeOperadores = instrucoesPosFixas.retornaQuantidadeOperadores(expressaoFinalPosFixa.get(i).getSimbolo());

                for(int k = 1; k <= quantidadeOperadores; k++) {
                    tipoCorreto = false;

                    tokenAuxiliar.setSimbolo(expressaoFinalPosFixa.get(i - k).getSimbolo());

                    if(tokenAuxiliar.getSimbolo().equals("snumero")) {
                        tokenAuxiliar.setSimbolo("sinteiro");
                    }

                    if(tokenAuxiliar.getSimbolo().equals("sverdadeiro") || tokenAuxiliar.getSimbolo().equals("sfalso")) {
                        tokenAuxiliar.setSimbolo("sbooleano");
                    }

                    if(tokenAuxiliar.getSimbolo().equals("sidentificador")) {
                        tokenAuxiliar.setSimbolo(tabelaSimbolo.pegaTipo(tokenAuxiliar));
                    }

                    tiposRequeritos = new ArrayList<>(instrucoesPosFixas.retornaTipoSimboloRequerido(expressaoFinalPosFixa.get(i).getSimbolo()));
                    for(String tipo : tiposRequeritos ) {
                        if(tokenAuxiliar.getSimbolo().equals(tipo)) {
                            tipoCorreto = true;
                            expressaoFinalPosFixa.remove(i - k);
                        }
                    }
                }
                i = i - quantidadeOperadores;

                if(tipoCorreto) {
                    tokenAuxiliar.setSimbolo(instrucoesPosFixas.retornaTipoSimboloResultante(expressaoFinalPosFixa.get(i).getSimbolo()));
                    expressaoFinalPosFixa.set(i, tokenAuxiliar);

                } else {
                    System.out.println("Erro semantico - tipos diferentes");
                }
            } else {
                i++;
            }
        }

        return expressaoFinalPosFixa.get(0).getSimbolo();
    }
}
