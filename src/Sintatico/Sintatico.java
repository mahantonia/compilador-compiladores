package Sintatico;

import ErroSintatico.ErroSintatico;
import Lexico.Lexico;
import Token.Token;

import java.util.ArrayList;

public class Sintatico {
    private Token token;
    private Lexico lexico;
    private int index;
    private final int lexema = 0;
    private final int simbolo = 1;
    private final int linha = 2;
    private String tokenSelecionado;
    private String[] tokenSeparado;

    public void start(String conteudo) throws Exception {
        token = new Token();
        lexico = new Lexico(conteudo, token);
        index = 0;
        analisaSintatico();
    }

    public ArrayList<String> getListaToken() {
        return token.getListaToken();
    }

    private void analisaSintatico() throws Exception {
        tokenSeparado = getToken();

        if (tokenSeparado[simbolo].equals("sprograma")) {
            tokenSeparado = getToken();
            if (tokenSeparado[simbolo].equals("sidentificador")) {
                tokenSeparado = getToken();
                if (tokenSeparado[simbolo].equals("sponto_virgula")) {
                    analisaBloco();
                    if(tokenSeparado[simbolo].equals("sponto")){
                        System.out.println("Compilado com sucesso =)");
                    } else {
                        error();
                    }
                } else {
                    error();
                }
            }
        } else {
            error();
        }
    }

    private void analisaBloco() throws Exception {
        tokenSeparado = getToken();

        analisaVariaveis();
        analisaSubRotina();
        analisaComandos();
    }

    private void analisaVariaveis() throws Exception {
        if(tokenSeparado[simbolo].equals("svar")){
            tokenSeparado = getToken();
            if(tokenSeparado[simbolo].equals("sidentificador")){
                while (tokenSeparado[simbolo].equals("sidentificador")){
                    analisaVariavel();
                    if(tokenSeparado[simbolo].equals("sponto_virgula")){
                        tokenSeparado = getToken();
                    } else {
                        error();
                    }
                }
            } else {
                error();
            }
        }
    }

    private void analisaVariavel() throws Exception {
        do {
            if(tokenSeparado[simbolo].equals("sidentificador")) {
                tokenSeparado = getToken();
                if((tokenSeparado[simbolo].equals("svirgula")) || (tokenSeparado[simbolo].equals("sdoispontos"))) {
                    if(tokenSeparado[simbolo].equals("svirgula")) {
                        tokenSeparado = getToken();
                        if(tokenSeparado[simbolo].equals("sdoispontos")) {
                            error();
                        }
                    }
                } else {
                    error();
                }
            } else {
                error();
            }
        }while (!tokenSeparado[simbolo].equals("sdoispontos"));

        tokenSeparado = getToken();
        analisaTipo();
    }

    private void analisaTipo() throws Exception {
        if(tokenSeparado[simbolo].equals("sinteiro")){
            tokenSeparado = getToken();
        } else {
            if(tokenSeparado[simbolo].equals("sbooleano")){
                tokenSeparado = getToken();
            } else {
                error();
            }
        }
    }

    private void analisaSubRotina() throws Exception {
        int flag = 0;

        if((tokenSeparado[simbolo].equals("sprocedimento")) || (tokenSeparado[simbolo].equals("sfuncao"))) {
            while((tokenSeparado[simbolo].equals("sprocedimento")) || (tokenSeparado[simbolo].equals("sfuncao"))) {
                if(tokenSeparado[simbolo].equals("sprocedimento")) {
                    analisaDeclaracaoProcedimento();
                } else {
                    analisaDeclaracaoFuncao();
                }


                if(tokenSeparado[simbolo].equals("sponto_virgula")) {
                    tokenSeparado = getToken();
                } else {
                    error();
                }

                if(flag == 1) {

                }
            }
        }
    }

    private void analisaDeclaracaoProcedimento() throws Exception {
        tokenSeparado = getToken();

        if(tokenSeparado[simbolo].equals("sidentificador")) {
            tokenSeparado = getToken();
            if(tokenSeparado[simbolo].equals("sponto_virgula")) {
                analisaBloco();
            } else {
                error();
            }
        } else {
            error();
        }
    }

    private void analisaDeclaracaoFuncao() throws Exception {
        tokenSeparado = getToken();

        if(tokenSeparado[simbolo].equals("sidentificador")) {
            tokenSeparado = getToken();

            if(tokenSeparado[simbolo].equals("sdoispontos")) {
                tokenSeparado = getToken();
                if((tokenSeparado[simbolo].equals("sinteiro")) || (tokenSeparado[simbolo].equals("sbooleano"))) {
                    tokenSeparado = getToken();

                    if(tokenSeparado[simbolo].equals("sponto_virgula")) {
                        analisaBloco();
                    }

                } else {
                    error();
                }
            } else {
                error();
            }
        } else {
            error();
        }
    }

    private void analisaComandos() throws Exception {
        if(tokenSeparado[simbolo].equals("sinicio")) {
            tokenSeparado = getToken();
                analisaComandoSimples();
                while (!tokenSeparado[simbolo].equals("sfim")) {
                    if(tokenSeparado[simbolo].equals("sponto_virgula")) {
                        tokenSeparado = getToken();
                        if(!tokenSeparado[simbolo].equals("sfim")) {
                            analisaComandoSimples();
                        }
                    } else {
                        error();
                    }
                }
                if(tokenSeparado[simbolo].equals("sfim")) {
                    tokenSeparado = getToken();
                }
        } else {
            error();
        }
    }

    private void analisaComandoSimples() throws Exception {
        switch (tokenSeparado[simbolo]) {
            case "sidentificador":
                    analisaAtribuicaoChamadaProcedimento();
                break;
            case "sse":
                    analisaSe();
                break;
            case "senquanto":
                    analisaEnquanto();
                break;
            case "sleia":
                    analisaLeia();
                break;
            case "sescreva":
                    analisaEscreva();
                break;
            default:
                    analisaComandos();
                break;
        }
    }

    private void analisaAtribuicaoChamadaProcedimento() throws Exception {
        tokenSeparado = getToken();

        if(tokenSeparado[simbolo].equals("satribuicao")) {
            analisaAtribuicao();
        } else {
            analisaChamadaProcedimento();
        }
    }

    private void analisaAtribuicao() throws Exception {
        tokenSeparado = getToken();

        analisaExpressao();
    }

    private void analisaChamadaProcedimento() throws Exception {
//        index--;
//        tokenSelecionado = getToken(index);
//        tokenSeparado = tokenSelecionado.split(" ");
//        if(tokenSeparado[simbolo].equals("sidentificador")){
//            index++;
//            if(continuaSintatico()) {
//                tokenSelecionado = getToken(index);
//                tokenSeparado = tokenSelecionado.split(" ");
//            }
//        } else {
//                error();
//        }
    }

    private void analisaSe() throws Exception {
        tokenSeparado = getToken();

        analisaExpressao();

        if(tokenSeparado[simbolo].equals("sentao")) {
            tokenSeparado = getToken();
            analisaComandoSimples();

            if(tokenSeparado[simbolo].equals("ssenao")) {
                tokenSeparado = getToken();
                analisaComandoSimples();
            }
        } else {
            error();
        }
    }

    private void analisaExpressao() throws Exception {
        analisaExpressaoSimples();

        if(tokenSeparado[simbolo].equals("smaior")
                || (tokenSeparado[simbolo].equals("smaiorig"))
                || (tokenSeparado[simbolo].equals("sig"))
                || (tokenSeparado[simbolo].equals("smenor"))
                || (tokenSeparado[simbolo].equals("smenorig"))
                || (tokenSeparado[simbolo].equals("sdif"))
        ) {
            tokenSeparado = getToken();
            analisaExpressaoSimples();
        }
    }

    private void analisaExpressaoSimples() throws Exception {
        if((tokenSeparado[simbolo].equals("smais")) || (tokenSeparado[simbolo].equals("smenos"))) {
            tokenSeparado = getToken();
        }

        analisaTermo();

        while ((tokenSeparado[simbolo].equals("smais")) || (tokenSeparado[simbolo].equals("smenos")) || (tokenSeparado[simbolo].equals("sou"))) {
            tokenSeparado = getToken();
            analisaTermo();
        }
    }

    private void analisaTermo() throws Exception {
        analisaFator();

        while ((tokenSeparado[simbolo].equals("smult")) || (tokenSeparado[simbolo].equals("sdiv")) || (tokenSeparado[simbolo].equals("se"))) {
            tokenSeparado = getToken();
            analisaFator();
        }
    }

    private void analisaFator() throws Exception {
        if(tokenSeparado[simbolo].equals("sidentificador")) {
            analisaChamadaFuncao();
        } else {
            if(tokenSeparado[simbolo].equals("snumero")) {
                tokenSeparado = getToken();
            } else {
                if(tokenSeparado[simbolo].equals("snao")) {
                    tokenSeparado = getToken();
                    analisaFator();
                } else {
                    if(tokenSeparado[simbolo].equals("sabre_parenteses")) {
                        tokenSeparado = getToken();
                        analisaExpressao();

                        if(tokenSeparado[simbolo].equals("sfecha_parenteses")) {
                            tokenSeparado = getToken();
                        } else {
                            error();
                        }
                    } else {
                        if((tokenSeparado[simbolo].equals("verdadeiro")) || (tokenSeparado[simbolo].equals("falso"))) {
                            tokenSeparado = getToken();
                        } else {
                            error();
                        }
                    }
                }
            }
        }
    }

    private void analisaChamadaFuncao() throws Exception {
        if(tokenSeparado[simbolo].equals("sidentificador")) {

        } else {
            error();
        }

        tokenSeparado = getToken();
    }

    private void analisaEnquanto() throws Exception {
        tokenSeparado = getToken();

        analisaExpressao();

        if(tokenSeparado[simbolo].equals("sfaca")) {
            tokenSeparado = getToken();
            analisaComandoSimples();
        } else {
            error();
        }

    }

    private void analisaLeia() throws Exception {
        tokenSeparado = getToken();

        if(tokenSeparado[simbolo].equals("sabre_parenteses")) {
            tokenSeparado = getToken();

            if(tokenSeparado[simbolo].equals("sidentificador")) {
                tokenSeparado = getToken();

                if(tokenSeparado[simbolo].equals("sfecha_parenteses")) {
                    tokenSeparado = getToken();
                } else {
                    error();
                }
            } else {
                error();
            }
        } else {
            error();
        }
    }

    private void analisaEscreva() throws Exception {
        tokenSeparado = getToken();

        if(tokenSeparado[simbolo].equals("sabre_parenteses")) {
            tokenSeparado = getToken();

            if(tokenSeparado[simbolo].equals("sidentificador")) {
                tokenSeparado = getToken();

                if(tokenSeparado[simbolo].equals("sfecha_parenteses")) {
                    tokenSeparado = getToken();
                } else {
                    error();
                }
            } else {
                error();
            }
        } else {
            error();
        }
    }

    private void error() throws Exception {
        new ErroSintatico().printaErro(Integer.parseInt(tokenSeparado[linha]));
    }

    private String[] getToken() throws Exception {
        lexico.adicionaTokenLista();
        tokenSelecionado = token.listaToken.get(index);
        index ++;
        return tokenSelecionado.split(" ");
    }
}
