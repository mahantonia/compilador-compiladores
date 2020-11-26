package Sintatico;

import ErroSemantico.ErroSematico;
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
    private Simbolo tipoExpressao;

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
                                tokenSeparado,
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
                        error("espero ponto no final do programa");
                    }
                } else {
                    error("esperado ponto e virgula no final");
                }
            }
        } else {
            error("Programa nao definido");
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
                        error("esperado ponto e virgula no final");
                    }
                }
            } else {
                error("esperado variavel");
            }
        }
    }

    private void analisaVariavel() throws Exception {
        do {
            if(tokenSeparado.getSimbolo().equals("sidentificador")) {
                if(semantico.getTabelaSimbolo().pesquisaDuplicidadeVariavelTabela(tokenSeparado.getLexema(), escopo)) {
                    semantico.getTabelaSimbolo().insereTabela(
                            new Simbolo(
                                    tokenSeparado,
                                    new Escopo(escopo.getNivel()),
                                    new Tipo("variavel")
                            )
                    );

                    tokenSeparado = getToken();
                    if((tokenSeparado.getSimbolo().equals("svirgula")) || (tokenSeparado.getSimbolo().equals("sdoispontos"))) {
                        if(tokenSeparado.getSimbolo().equals("svirgula")) {
                            tokenSeparado = getToken();
                            if(tokenSeparado.getSimbolo().equals("sdoispontos")) {
                                error("");
                            }
                        }
                    } else {
                        error("");
                    }
                } else {
                    /* Erro semantico !!*/
//                    System.out.println("Erro Semantico - Variavel duplicada !!!");
                    new ErroSematico().printaErro("Erro Semantico - Variavel duplicada ");
                }
            } else {
                error("esperado ponto e virgula no final");
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
            if(tokenSeparado.getSimbolo().equals("sboolean")){
                semantico.getTabelaSimbolo().atriuirTipoVariaveis(escopo, new Tipo("variavelBoolean"));
                tokenSeparado = getToken();
            } else {
                error("esperado ponto e virgula no final");
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
                    error("esperado ponto e virgula no final");
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
            if(semantico.getTabelaSimbolo().pesquisaDuplicidadeProcedimentoTabela(tokenSeparado.getLexema())) {
                semantico.getTabelaSimbolo().insereTabela(
                        new Simbolo(
                            tokenSeparado,
                            new Escopo(escopo.getNivel()),
                            new Tipo("procedimento")
                        )
                );

                tokenSeparado = getToken();
                if(tokenSeparado.getSimbolo().equals("sponto_virgula")) {
                    analisaBloco();
                } else {
                    error("esperado ponto e virgula no final");
                }
            } else {
                new ErroSematico().printaErro("Erro Semantico - procedimento nao declarado ou fora do nivel de alcance");
            }
        } else {
            error("esperado ponto e virgula no final");
        }

        semantico.getTabelaSimbolo().excluiValorTabela(escopo);
        escopo.removerNivel();
    }

    private void analisaDeclaracaoFuncao() throws Exception {
        tokenSeparado = getToken();
        escopo.adicionarNivel();

        if(tokenSeparado.getSimbolo().equals("sidentificador")) {
            if(semantico.getTabelaSimbolo().pesquisaDeclaracaoFuncaoTabela(tokenSeparado.getLexema())) {
                lexemaAntigo = tokenSeparado;
                tokenSeparado = getToken();

                if(tokenSeparado.getSimbolo().equals("sdoispontos")) {
                    tokenSeparado = getToken();

                    if(tokenSeparado.getSimbolo().equals("sinteiro")) {
                        semantico.getTabelaSimbolo().insereTabela (new Simbolo(
                                lexemaAntigo,
                                new Escopo(escopo.getNivel()),
                                new Tipo("funcaoInteiro")
                        ));
                    } else {
                        if (tokenSeparado.getSimbolo().equals("sboolean")) {
                            semantico.getTabelaSimbolo().insereTabela(new Simbolo(
                                    lexemaAntigo,
                                    new Escopo(escopo.getNivel()),
                                    new Tipo("funcaoBoolean")
                            ));
                        } else {
                            error("BLA4");
                        }
                    }

                    tokenSeparado = getToken();

                    if(tokenSeparado.getSimbolo().equals("sponto_virgula")) {
                        analisaBloco();
                    }
                } else {
                    error("BLA3");
                }
            } else {
                /* Erro Sematico */
//                System.out.println("Erro Semantico - nao existe ou nao esta visivel o nome da funcao");
                new ErroSematico().printaErro("Erro Semantico - nao existe ou nao esta visivel o nome da funcao");
            }
        } else {
            error("BLA2");
        }

        semantico.getTabelaSimbolo().excluiValorTabela(escopo);
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
                        error("esperado ponto e virgula no final");
                    }
                }
                tokenSeparado = getToken();
        } else {
            error("BLA");
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
        String tipoVariavelAtribuicao;

        if(semantico.getTabelaSimbolo().pesquisaDeclaracaoVariavelTabela(lexemaAntigo.getLexema())) {
            tokenSeparado = getToken();

            semantico.getPosFixa().limparPosFixa();
            analisaExpressao();
            tipoExpressao = semantico.getPosFixa().pegaTipoExpressao();

            tipoVariavelAtribuicao = semantico.getTabelaSimbolo().pegaTipo(lexemaAntigo);

            if(tipoVariavelAtribuicao.equals("variavelInteiro") || tipoVariavelAtribuicao.equals("funcaoInteiro")) {
                tipoVariavelAtribuicao = "snumero";
            }

            if(tipoVariavelAtribuicao.equals("variavelBoolean") || tipoVariavelAtribuicao.equals("funcaoBoolean")) {
                tipoVariavelAtribuicao = "sboolean";
            }

            if( tipoExpressao.getTipo().getTipoValor().equals("sinteiro") ||
                tipoExpressao.getTipo().getTipoValor().equals("variavelInteiro") ||
                tipoExpressao.getTipo().getTipoValor().equals("funcaoInteiro")) {
                tipoExpressao.getTipo().setTipoValor("snumero");
            }

            if( tipoExpressao.getTipo().getTipoValor().equals("sverdadeiro") ||
                tipoExpressao.getTipo().getTipoValor().equals("sfalso") ||
                tipoExpressao.getTipo().getTipoValor().equals("variavelBoolean") ||
                tipoExpressao.getTipo().getTipoValor().equals("funcaoBoolean")) {
                tipoExpressao.getTipo().setTipoValor("sboolean");
            }

            if(!tipoExpressao.getTipo().getTipoValor().equals(tipoVariavelAtribuicao)) {
                /* Erro semantico - tipos diferentes atribuicao */
                new ErroSematico().printaErro("Erro Semantico - tipos diferentes");
            }
        } else {
            if(!semantico.getTabelaSimbolo().pesquisaDeclaracaoFuncaoTabela(lexemaAntigo.getLexema())) {
                tokenSeparado = getToken();

                semantico.getPosFixa().limparPosFixa();
                analisaExpressao();
                tipoExpressao = semantico.getPosFixa().pegaTipoExpressao();

                tipoVariavelAtribuicao = semantico.getTabelaSimbolo().pegaTipo(lexemaAntigo);

                if(tipoVariavelAtribuicao.equals("variavelInteiro") || tipoVariavelAtribuicao.equals("funcaoInteiro")) {
                    tipoVariavelAtribuicao = "snumero";
                }

                if(tipoVariavelAtribuicao.equals("variavelBoolean") || tipoVariavelAtribuicao.equals("funcaoBoolean")) {
                    tipoVariavelAtribuicao = "sboolean";
                }

                if( tipoExpressao.getTipo().getTipoValor().equals("sinteiro") ||
                    tipoExpressao.getTipo().getTipoValor().equals("variavelInteiro") ||
                    tipoExpressao.getTipo().getTipoValor().equals("funcaoInteiro")) {
                    tipoExpressao.getTipo().setTipoValor("snumero");
                }

                if( tipoExpressao.getTipo().getTipoValor().equals("sverdadeiro") ||
                        tipoExpressao.getTipo().getTipoValor().equals("sfalso") ||
                        tipoExpressao.getTipo().getTipoValor().equals("variavelBoolean") ||
                        tipoExpressao.getTipo().getTipoValor().equals("funcaoBoolean")) {
                    tipoExpressao.getTipo().setTipoValor("sboolean");
                }

                if(!tipoExpressao.getTipo().getTipoValor().equals(tipoVariavelAtribuicao)) {
                    new ErroSematico().printaErro("Erro Semantico - tipos diferentes");
                }
            }
        }
    }

    private void analisaChamadaProcedimento(Token lexemaAntigo) throws Exception {
        if(semantico.getTabelaSimbolo().pesquisaDeclaracaoProcedimentoTabela(lexemaAntigo.getLexema())) {
            /* Geracao de codigo */
        } else {
//            System.out.println("ERROR Semantico!!");
            new ErroSematico().printaErro("Erro Semantico");
        }
    }

    private void analisaSe() throws Exception {
        tokenSeparado = getToken();

        semantico.getPosFixa().limparPosFixa();
        analisaExpressao();

        tipoExpressao = semantico.getPosFixa().pegaTipoExpressao();

        if(tipoExpressao.getTipo().getTipoValor().equals("variavelBoolean") ||
            tipoExpressao.getTipo().getTipoValor().equals("funcaoBoolean") ||
            tipoExpressao.getTipo().getTipoValor().equals("sfalso") ||
            tipoExpressao.getTipo().getTipoValor().equals("sverdadeiro")) {
            tipoExpressao.getTipo().setTipoValor("sboolean");
        }

        if(!tipoExpressao.getTipo().getTipoValor().equals("sboolean")) {
            new ErroSematico().printaErro("Erro Semantico - expressao nao retorna booleano");
        } else {
            if(tokenSeparado.getSimbolo().equals("sentao")) {
                tokenSeparado = getToken();
                analisaComandoSimples();

                if(tokenSeparado.getSimbolo().equals("ssenao")) {
                    tokenSeparado = getToken();
                    analisaComandoSimples();
                }
            } else {
                error("esperado ponto e virgula no final");
            }
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
            semantico.getPosFixa().adiconarPosFixa(tokenSeparado);
            tokenSeparado = getToken();
            analisaExpressaoSimples();
        }
    }

    private void analisaExpressaoSimples() throws Exception {
        if(tokenSeparado.getSimbolo().equals("smais")) {
            tokenSeparado = getToken();
        }

        if(tokenSeparado.getSimbolo().equals("smenos")) {
            tokenSeparado.setSimbolo("smenosu");
            semantico.getPosFixa().adiconarPosFixa(tokenSeparado);
            tokenSeparado = getToken();
        }

        analisaTermo();

        while ((tokenSeparado.getSimbolo().equals("smais")) || (tokenSeparado.getSimbolo().equals("smenos")) || (tokenSeparado.getSimbolo().equals("sou"))) {
            semantico.getPosFixa().adiconarPosFixa(tokenSeparado);
            tokenSeparado = getToken();
            analisaTermo();
        }
    }

    private void analisaTermo() throws Exception {
        analisaFator();

        while ((tokenSeparado.getSimbolo().equals("smult")) || (tokenSeparado.getSimbolo().equals("sdiv")) || (tokenSeparado.getSimbolo().equals("se"))) {
            semantico.getPosFixa().adiconarPosFixa(tokenSeparado);
            tokenSeparado = getToken();
            analisaFator();
        }
    }

    private void analisaFator() throws Exception {
        if(tokenSeparado.getSimbolo().equals("sidentificador")) {
            if(semantico.getTabelaSimbolo().pesquisaTabela(tokenSeparado.getLexema(), escopo)) {
                semantico.getPosFixa().adiconarPosFixa(tokenSeparado);
                if(semantico.getTabelaSimbolo().pesquisaDeclaracaoVariavelTabela(tokenSeparado.getLexema())) {
                    tokenSeparado = getToken();
                } else {
                    analisaChamadaFuncao();
                }
            } else {
                /* Erro semantico - nao existe nome bla */
//                System.out.println("Erro semantico - nome de variavel fora do escopo ou nao declarada");
                new ErroSematico().printaErro("Erro Semantico -  nome de variavel fora do escopo ou nao declarada");
            }

        } else {
            if(tokenSeparado.getSimbolo().equals("snumero")) {
                semantico.getPosFixa().adiconarPosFixa(tokenSeparado);
                tokenSeparado = getToken();
            } else {
                if(tokenSeparado.getSimbolo().equals("snao")) {
                    semantico.getPosFixa().adiconarPosFixa(tokenSeparado);
                    tokenSeparado = getToken();
                    analisaFator();
                } else {
                    if(tokenSeparado.getSimbolo().equals("sabre_parenteses")) {
                        semantico.getPosFixa().adiconarPosFixa(tokenSeparado);

                        tokenSeparado = getToken();
                        analisaExpressao();

                        if(tokenSeparado.getSimbolo().equals("sfecha_parenteses")) {
                            semantico.getPosFixa().adiconarPosFixa(tokenSeparado);
                            tokenSeparado = getToken();
                        } else {
                            error("esperado ponto e virgula no final");
                        }
                    } else {
                        if((tokenSeparado.getSimbolo().equals("sverdadeiro")) || (tokenSeparado.getSimbolo().equals("sfalso"))) {
                            semantico.getPosFixa().adiconarPosFixa(tokenSeparado);
                            tokenSeparado = getToken();
                        } else {
                            error("esperado ponto e virgula no final");
                        }
                    }
                }
            }
        }
    }

    private void analisaChamadaFuncao() throws Exception {
        if(tokenSeparado.getSimbolo().equals("sidentificador")) {
            semantico.getTabelaSimbolo().pesquisaDeclaracaoFuncaoTabela(tokenSeparado.getLexema());

        } else {
            error("esperado ponto e virgula no final");
        }

        tokenSeparado = getToken();
    }

    private void analisaEnquanto() throws Exception {
        tokenSeparado = getToken();
        semantico.getPosFixa().limparPosFixa();

        analisaExpressao();

        tipoExpressao = semantico.getPosFixa().pegaTipoExpressao();

        if(!tipoExpressao.getTipo().getTipoValor().equals("sboolean")) {
            new ErroSematico().printaErro("Erro Semantico !");
        } else {
            if(tokenSeparado.getSimbolo().equals("sfaca")) {
                tokenSeparado = getToken();
                analisaComandoSimples();
            } else {
                error("esperado ponto e virgula no final");
            }
        }
    }

    private void analisaLeia() throws Exception {
        tokenSeparado = getToken();

        if(tokenSeparado.getSimbolo().equals("sabre_parenteses")) {
            tokenSeparado = getToken();

            if(tokenSeparado.getSimbolo().equals("sidentificador")) {
                if(semantico.getTabelaSimbolo().pesquisaDeclaracaoVariavelFuncao(tokenSeparado.getLexema())) {
                    tokenSeparado = getToken();
                } else {
//                    System.out.println("ERROR Semantico !!");
                    new ErroSematico().printaErro("Erro semantico }}}");
                }
                if(tokenSeparado.getSimbolo().equals("sfecha_parenteses")) {
                    tokenSeparado = getToken();
                } else {
                    error("esperado ponto e virgula no final");
                }
            } else {
                error("esperado ponto e virgula no final");
            }
        } else {
            error("esperado ponto e virgula no final");
        }
    }

    private void analisaEscreva() throws Exception {
        tokenSeparado = getToken();

        if(tokenSeparado.getSimbolo().equals("sabre_parenteses")) {
                tokenSeparado = getToken();
            if(tokenSeparado.getSimbolo().equals("sidentificador")) {
                if(semantico.getTabelaSimbolo().pesquisaDeclaracaoVariavelFuncao(tokenSeparado.getLexema())) {
                    tokenSeparado = getToken();

                    if(tokenSeparado.getSimbolo().equals("sfecha_parenteses")) {
                        tokenSeparado = getToken();
                    } else {
                        error("esperado ponto e virgula no final");
                    }
                } else {
                    /* Erro semantico */
                    new ErroSematico().printaErro("Erro semantico - variavel nao declarada no escopo ou fora de alcance q");
                }
            } else {
                error("esperado ponto e virgula no final");
            }
        } else {
            error("esperado ponto e virgula no final");
        }
    }

    private void error(String s) throws Exception {
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
