package Sintatico;

import ErroSintatico.ErroSintatico;
import Escopo.Escopo;
import Lexico.Lexico;
import Semantico.Semantico;
import Simbolo.Simbolo;
import Tipo.Tipo;
import Token.Token;

import java.util.ArrayList;

public class Sintatico {
    private Token token;
    private Lexico lexico;
    private int index;
    private Token tokenSelecionado;
    private Token lexemaAntigo;
    private Token tokenSeparado;
    private Semantico semantico;

    private Escopo escopo;

    public void start(String conteudo) throws Exception {
        token = new Token();
        lexico = new Lexico(conteudo, token);
        semantico = new Semantico();
        escopo = new Escopo(0);
        index = 0;
        analisaSintatico();
    }

    public ArrayList<Token> getListaToken() {
        return token.getListaToken();
    }

    private void analisaSintatico() throws Exception {
        tokenSeparado = getToken();

        if (tokenSeparado.getSimbolo().equals("sprograma")) {
            tokenSeparado = getToken();
            if (tokenSeparado.getSimbolo().equals("sidentificador")) {
                semantico.getTabelaSimbolo().insereTabela(
                        new Simbolo(
                                tokenSeparado.getLexema(),
                                new Escopo(0),
                                new Tipo("nomePrograma")
                        )
                );
                tokenSeparado = getToken();
                if (tokenSeparado.getSimbolo().equals("sponto_virgula")) {
                    analisaBloco();
                    if(tokenSeparado.getSimbolo().equals("sponto")){
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
        if(tokenSeparado.getSimbolo().equals("svar")){
            tokenSeparado = getToken();
            if(tokenSeparado.getSimbolo().equals("sidentificador")){
                while (tokenSeparado.getSimbolo().equals("sidentificador")){
                    analisaVariavel();
                    if(tokenSeparado.getSimbolo().equals("sponto_virgula")){
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
            if(tokenSeparado.getSimbolo().equals("sidentificador")) {
                if(semantico.getTabelaSimbolo().pesquisaDuplicidadeVariavelTabela(tokenSeparado.getLexema())) {
                    semantico.getTabelaSimbolo().insereTabela(
                            new Simbolo(
                                    tokenSeparado.getLexema(),
                                    new Escopo(escopo.getNivel()),
                                    new Tipo("variavel")
                            )
                    );

                    tokenSeparado = getToken();
                    if((tokenSeparado.getSimbolo().equals("svirgula")) || (tokenSeparado.getSimbolo().equals("sdoispontos"))) {
                        if(tokenSeparado.getSimbolo().equals("svirgula")) {
                            tokenSeparado = getToken();
                            if(tokenSeparado.getSimbolo().equals("sdoispontos")) {
                                error();
                            }
                        }
                    } else {
                        error();
                    }
                } else {
                    /* Erro semantico !!*/
                    System.out.println("Erro Semantico - Variavel duplicada !!!");
                }

            } else {
                error();
            }
        }while (!tokenSeparado.getSimbolo().equals("sdoispontos"));

        tokenSeparado = getToken();
        analisaTipo();
    }

    private void analisaTipo() throws Exception {
        if(tokenSeparado.getSimbolo().equals("sinteiro")){
            semantico.getTabelaSimbolo().atriuirTipoVariaveis(escopo, new Tipo("variavelInteiro"));
            tokenSeparado = getToken();
        } else {
            if(tokenSeparado.getSimbolo().equals("sbooleano")){
                semantico.getTabelaSimbolo().atriuirTipoVariaveis(escopo, new Tipo("variavelBoolean"));
                tokenSeparado = getToken();
            } else {
                error();
            }
        }
    }

    private void analisaSubRotina() throws Exception {
        int flag = 0;

        if((tokenSeparado.getSimbolo().equals("sprocedimento")) || (tokenSeparado.getSimbolo().equals("sfuncao"))) {
            while((tokenSeparado.getSimbolo().equals("sprocedimento")) || (tokenSeparado.getSimbolo().equals("sfuncao"))) {
                if(tokenSeparado.getSimbolo().equals("sprocedimento")) {
                    analisaDeclaracaoProcedimento();
                } else {
                    analisaDeclaracaoFuncao();
                }

                if(tokenSeparado.getSimbolo().equals("sponto_virgula")) {
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
        escopo.adicionarNivel();

        if(tokenSeparado.getSimbolo().equals("sidentificador")) {
            tokenSeparado = getToken();
            if(tokenSeparado.getSimbolo().equals("sponto_virgula")) {
                analisaBloco();
            } else {
                error();
            }
        } else {
            error();
        }

        escopo.removerNivel();
    }

    private void analisaDeclaracaoFuncao() throws Exception {
        tokenSeparado = getToken();
        escopo.adicionarNivel();

        if(tokenSeparado.getSimbolo().equals("sidentificador")) {
            tokenSeparado = getToken();

            if(tokenSeparado.getSimbolo().equals("sdoispontos")) {
                tokenSeparado = getToken();
                if((tokenSeparado.getSimbolo().equals("sinteiro")) || (tokenSeparado.getSimbolo().equals("sbooleano"))) {
                    tokenSeparado = getToken();

                    if(tokenSeparado.getSimbolo().equals("sponto_virgula")) {
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

        escopo.removerNivel();
    }

    private void analisaComandos() throws Exception {
        if(tokenSeparado.getSimbolo().equals("sinicio")) {
            tokenSeparado = getToken();
                analisaComandoSimples();
                while (!tokenSeparado.getSimbolo().equals("sfim")) {
                    if(tokenSeparado.getSimbolo().equals("sponto_virgula")) {
                        tokenSeparado = getToken();
                        if(!tokenSeparado.getSimbolo().equals("sfim")) {
                            analisaComandoSimples();
                        }
                    } else {
                        error();
                    }
                }
                tokenSeparado = getToken();
//                if(tokenSeparado[simbolo].equals("sfim")) {
//                    tokenSeparado = getToken();
//                }
        } else {
            error();
        }
    }

    private void analisaComandoSimples() throws Exception {
        switch (tokenSeparado.getSimbolo()) {
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
        lexemaAntigo = tokenSeparado;

        tokenSeparado = getToken();

        if(tokenSeparado.getSimbolo().equals("satribuicao")) {
            analisaAtribuicao(lexemaAntigo);
        } else {
            analisaChamadaProcedimento(lexemaAntigo);
        }
    }

    private void analisaAtribuicao(Token lexemaAntigo) throws Exception {
        if(semantico.getTabelaSimbolo().pesquisaDeclaracaoVariavelTabela(lexemaAntigo.getLexema())) {
            tokenSeparado = getToken();

            semantico.getPosFixa().limparPosFixa();
            analisaExpressao();
            String tipoExpressao = semantico.getPosFixa().pegaTipoExpressao();

            String tipoVariavelAtribuicao = semantico.getTabelaSimbolo().pegaTipo(lexemaAntigo);

            if(!tipoExpressao.equals(tipoVariavelAtribuicao)) {
                /* Erro semantico - tipos diferentes atribuicao */
                System.out.println("Erro Semantico");
            }
        } else {
            if(semantico.getTabelaSimbolo().pesquisaDeclaracaoFuncaoTabela(lexemaAntigo.getLexema())) {
                tokenSeparado = getToken();

                semantico.getPosFixa().limparPosFixa();
                analisaExpressao();
                String tipoExpressao = semantico.getPosFixa().pegaTipoExpressao();

                String tipoVariavelAtribuicao = semantico.getTabelaSimbolo().pegaTipo(lexemaAntigo);

                if(!tipoExpressao.equals(tipoVariavelAtribuicao)) {
                    /* Erro semantico - tipos diferentes atribuicao */
                    System.out.println("Erro Semantico");
                }
            }
        }
    }

    private void analisaChamadaProcedimento(Token lexemaAntigo) throws Exception {
        if(semantico.getTabelaSimbolo().pesquisaDeclaracaoProcedimentoTabela(lexemaAntigo.getLexema())) {
            /* Geracao de codigo */
        } else {
            System.out.println("ERROR Semantico!!");
        }
    }

    private void analisaSe() throws Exception {
        tokenSeparado = getToken();

        analisaExpressao();

        if(tokenSeparado.getSimbolo().equals("sentao")) {
            tokenSeparado = getToken();
            analisaComandoSimples();

            if(tokenSeparado.getSimbolo().equals("ssenao")) {
                tokenSeparado = getToken();
                analisaComandoSimples();
            }
        } else {
            error();
        }
    }

    private void analisaExpressao() throws Exception {
        analisaExpressaoSimples();

        if(tokenSeparado.getSimbolo().equals("smaior")
                || (tokenSeparado.getSimbolo().equals("smaiorig"))
                || (tokenSeparado.getSimbolo().equals("sig"))
                || (tokenSeparado.getSimbolo().equals("smenor"))
                || (tokenSeparado.getSimbolo().equals("smenorig"))
                || (tokenSeparado.getSimbolo().equals("sdif"))
        ) {
            tokenSeparado = getToken();
            analisaExpressaoSimples();
        }
    }

    private void analisaExpressaoSimples() throws Exception {
        if((tokenSeparado.getSimbolo().equals("smais")) || (tokenSeparado.getSimbolo().equals("smenos"))) {
            tokenSeparado = getToken();
        }

        analisaTermo();

        while ((tokenSeparado.getSimbolo().equals("smais")) || (tokenSeparado.getSimbolo().equals("smenos")) || (tokenSeparado.getSimbolo().equals("sou"))) {
            tokenSeparado = getToken();
            analisaTermo();
        }
    }

    private void analisaTermo() throws Exception {
        analisaFator();

        while ((tokenSeparado.getSimbolo().equals("smult")) || (tokenSeparado.getSimbolo().equals("sdiv")) || (tokenSeparado.getSimbolo().equals("se"))) {
            tokenSeparado = getToken();
            analisaFator();
        }
    }

    private void analisaFator() throws Exception {
        if(tokenSeparado.getSimbolo().equals("sidentificador")) {
            analisaChamadaFuncao();
        } else {
            if(tokenSeparado.getSimbolo().equals("snumero")) {
                tokenSeparado = getToken();
            } else {
                if(tokenSeparado.getSimbolo().equals("snao")) {
                    tokenSeparado = getToken();
                    analisaFator();
                } else {
                    if(tokenSeparado.getSimbolo().equals("sabre_parenteses")) {
                        tokenSeparado = getToken();
                        analisaExpressao();

                        if(tokenSeparado.getSimbolo().equals("sfecha_parenteses")) {
                            tokenSeparado = getToken();
                        } else {
                            error();
                        }
                    } else {
                        if((tokenSeparado.getSimbolo().equals("sverdadeiro")) || (tokenSeparado.getSimbolo().equals("sfalso"))) {
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
        if(tokenSeparado.getSimbolo().equals("sidentificador")) {

        } else {
            error();
        }

        tokenSeparado = getToken();
    }

    private void analisaEnquanto() throws Exception {
        tokenSeparado = getToken();

        analisaExpressao();

        if(tokenSeparado.getSimbolo().equals("sfaca")) {
            tokenSeparado = getToken();
            analisaComandoSimples();
        } else {
            error();
        }

    }

    private void analisaLeia() throws Exception {
        tokenSeparado = getToken();

        if(tokenSeparado.getSimbolo().equals("sabre_parenteses")) {
            tokenSeparado = getToken();

            if(tokenSeparado.getSimbolo().equals("sidentificador")) {
                if(semantico.getTabelaSimbolo().pesquisaDeclaracaoVariavelTabela(tokenSeparado.getLexema())) {
                    tokenSeparado = getToken();
                } else {
                    System.out.println("ERROR Semantico !!");
                }

                if(tokenSeparado.getSimbolo().equals("sfecha_parenteses")) {
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

        if(tokenSeparado.getSimbolo().equals("sabre_parenteses")) {
            tokenSeparado = getToken();

            if(tokenSeparado.getSimbolo().equals("sidentificador")) {
                tokenSeparado = getToken();

                if(semantico.getTabelaSimbolo().pesquisaDeclaracaoVariavelTabela(tokenSeparado.getLexema())) {
                    tokenSeparado = getToken();
                } else {
                    if(semantico.getTabelaSimbolo().pesquisaDeclaracaoFuncaoTabela(tokenSeparado.getLexema())) {
                        tokenSeparado = getToken();
                    } else {
                        System.out.println("ERROR Semantico!!");
                    }
                }

                if(tokenSeparado.getSimbolo().equals("sfecha_parenteses")) {
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
        new ErroSintatico().printaErro(Integer.parseInt(tokenSeparado.getLinha()));
    }

    private Token getToken() throws Exception {
        lexico.adicionaTokenLista();
        if(index != token.listaToken.size()){
            tokenSelecionado = token.listaToken.get(index);
            index ++;
            return tokenSelecionado;
        }
        return tokenSelecionado;
    }
}
