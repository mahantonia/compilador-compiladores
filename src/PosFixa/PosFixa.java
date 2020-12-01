package PosFixa;

import ErroSemantico.ErroSematico;
import GeracaoCodigo.GeracaoCodigo;
import InstrucoesPosFixas.InstrucoesPosFixas;
import Simbolo.Simbolo;
import TabelaSimbolo.TabelaSimbolo;
import Token.Token;

import java.util.ArrayList;
import java.util.List;

public class PosFixa {
    ArrayList<Token> pilha;
    ArrayList<Token> expressaoFinalPosFixa;
    InstrucoesPosFixas instrucoesPosFixas;
    TabelaSimbolo tabelaSimbolo;
    GeracaoCodigo geracaoCodigo;

    public PosFixa(TabelaSimbolo tabelaSimbolo, GeracaoCodigo geracaoCodigo) {
        pilha = new ArrayList<>();
        expressaoFinalPosFixa = new ArrayList<>();
        instrucoesPosFixas = new InstrucoesPosFixas();
        this.tabelaSimbolo = tabelaSimbolo;
        this.geracaoCodigo = geracaoCodigo;
    }

    public Token getTopoPilha() {
        return pilha.get(pilha.size() - 1);
    }

    public void removeTopoPilha() {
        if(pilha.size() == 0) {
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
                        break;
                    }
                }
            } else {
                if( token.getSimbolo().equals("sidentificador") ||
                    token.getSimbolo().equals("snumero") ||
                    token.getSimbolo().equals("sverdadeiro") ||
                    token.getSimbolo().equals("sfalso")) {
                    expressaoFinalPosFixa.add(token);
                } else {
                    if(pilha.size() == 0) {
                        pilha.add(token);
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
                                pilha.add(token);
                            } else {
                                expressaoFinalPosFixa.add(token);
                            }
                        }
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


    public Simbolo pegaTipoExpressao() throws Exception {
        int i = 0;
        boolean tipoCorreto = false;
        Simbolo tokenAuxiliar = new Simbolo();
        List<String> tiposRequeritos;

        getPosFixa();
        geraCodigoPosFixa(expressaoFinalPosFixa);

        if(expressaoFinalPosFixa.size() == 1) {
            tokenAuxiliar.getToken().setSimbolo(expressaoFinalPosFixa.get(0).getSimbolo());
            tokenAuxiliar.getToken().setLexema(expressaoFinalPosFixa.get(0).getLexema());
            tokenAuxiliar.getTipo().setTipoValor(expressaoFinalPosFixa.get(0).getSimbolo());


            if(tokenAuxiliar.getToken().getSimbolo().equals("sidentificador")) {
                tokenAuxiliar.getTipo().setTipoValor(tabelaSimbolo.pegaTipo(tokenAuxiliar.getToken()));
            }

        } else {
            while (expressaoFinalPosFixa.size() != 1) {
                if(instrucoesPosFixas.existeExpressao(expressaoFinalPosFixa.get(i).getSimbolo())){
                    int quantidadeOperadores = instrucoesPosFixas.retornaQuantidadeOperadores(expressaoFinalPosFixa.get(i).getSimbolo());

                    for(int k = 0; k < quantidadeOperadores; k++) {
                        tipoCorreto = false;

                        tokenAuxiliar.getToken().setSimbolo(expressaoFinalPosFixa.get(i-1).getSimbolo());
                        tokenAuxiliar.getToken().setLexema(expressaoFinalPosFixa.get(i-1).getLexema());
                        tokenAuxiliar.getTipo().setTipoValor(expressaoFinalPosFixa.get(i-1).getSimbolo());


                        if(tokenAuxiliar.getToken().getSimbolo().equals("sidentificador")) {
                            tokenAuxiliar.getTipo().setTipoValor(tabelaSimbolo.pegaTipo(tokenAuxiliar.getToken()));
                        }

                        if((expressaoFinalPosFixa.get(i).getSimbolo().equals("sig") ||
                            expressaoFinalPosFixa.get(i).getSimbolo().equals("sdif")) &&
                            k == 0
                        ) {
                            if(!instrucoesPosFixas.getOperadoresInteiro().contains(expressaoFinalPosFixa.get(i - 1).getSimbolo())) {
                                expressaoFinalPosFixa.get(i).setSimbolo("sdifB");
                            }
                        }


                        tiposRequeritos = new ArrayList<>(instrucoesPosFixas.retornaTipoSimboloRequerido(expressaoFinalPosFixa.get(i).getSimbolo()));
                        for(String tipo : tiposRequeritos ) {
                            if(tokenAuxiliar.getTipo().getTipoValor().equals(tipo)) {
                                tipoCorreto = true;
                                expressaoFinalPosFixa.remove(i - 1);
                                i--;
                            }
                        }

                        if(!tipoCorreto) {
                            new ErroSematico().printaErro("Erro Semantico - variaveis ou funcoes com tipos diferentes", Integer.parseInt(expressaoFinalPosFixa.get(i).getLinha()));
                        }
                    }

                    tokenAuxiliar.getTipo().setTipoValor(instrucoesPosFixas.retornaTipoSimboloResultante(expressaoFinalPosFixa.get(i).getSimbolo()));

                    expressaoFinalPosFixa.get(i).setSimbolo(tokenAuxiliar.getTipo().getTipoValor());
                } else {
                    i++;
                }
            }
        }

        return tokenAuxiliar;
    }

    public void geraCodigoPosFixa(ArrayList<Token> token) {
        for(int i = 0; i < token.size(); i++) {
            identificaSimboloPosFixa(token.get(i).getSimbolo(), token.get(i).getLexema(), token.get(i).getSimbolo());
        }
    }

    private void identificaSimboloPosFixa(String simbolo, String lexema, String s) {
        switch (simbolo) {
            case "sidentificador":
                if(tabelaSimbolo.pesquisaDeclaracaoVariavelTabela(lexema)) {
                    int posicao = tabelaSimbolo.retornaPosicaoRotulo(lexema);
                    if(posicao != -1) {
                        geracaoCodigo.LDV(posicao);
                    }
                }
                break;
            case "snumero":
                    geracaoCodigo.LDC(lexema);
                break;
            case "sverdadeiro":
                    geracaoCodigo.LDC("1");
                break;
            case "sfalso":
                    geracaoCodigo.LDC("0");
                break;
            case "smenosu":
                    geracaoCodigo.INV();
                break;
            case "smais":
                    geracaoCodigo.ADD();
                break;
            case "smenos":
                    geracaoCodigo.SUB();
                break;
            case "smult":
                    geracaoCodigo.MULT();
                break;
            case "sdiv":
                    geracaoCodigo.DIVI();
                break;
            case "smenor":
                    geracaoCodigo.CME();
                break;
            case "smaior":
                geracaoCodigo.CMA();
                break;
            case "smaiorig":
                    geracaoCodigo.CMAQ();
                break;
            case "smenorig":
                    geracaoCodigo.CMEQ();
                break;
            case "sig":
                    geracaoCodigo.CEQ();
                break;
            case "sdif":
                    geracaoCodigo.CDIF();
                break;
            case "sigB":
                    geracaoCodigo.CEQ();
                break;
            case "sdifB":
                    geracaoCodigo.CDIF();
                break;
            case "snao":
                    geracaoCodigo.NEG();
                break;
            case "se":
                    geracaoCodigo.AND();
                break;
            case "sou":
                    geracaoCodigo.OR();
                break;
        }
    }
}
