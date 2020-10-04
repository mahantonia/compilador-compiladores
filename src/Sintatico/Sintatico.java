package Sintatico;

import ErroSintatico.ErroSintatico;
import Token.Token;

public class Sintatico {
    private Token listaToken;
    private int index;
    private int tamanhoListaToken;
    private int lexema = 0;
    private int simbolo = 1;
    private int linha = 2;
    private String tokenSelecionado;
    private String tokenSeparado[];
    private boolean continua = false;

    public void start(Token token) throws Exception {
        index = 0;
        listaToken = token;
        tamanhoListaToken = token.listaToken.size();
        analisaSintatico();
    }

    private void analisaSintatico() throws Exception {
        tokenSelecionado = getToken(index);
        tokenSeparado = tokenSelecionado.split(" ");

        if ((tokenSeparado[simbolo].equals("sprograma")) || (continua == true)) {
            continua = true;
            index++;

            tokenSelecionado = getToken(index);
            tokenSeparado = tokenSelecionado.split(" ");

             if (tokenSeparado[simbolo].equals("sidentificador")) {
                index++;
                tokenSelecionado = getToken(index);
                tokenSeparado = tokenSelecionado.split(" ");
                if (tokenSeparado[simbolo].equals("sponto_virgula")) {
                    analisaBloco();
                    if(tokenSeparado[simbolo].equals("sponto")){
                        if(!fimArquivo()){
                            error();
                        }
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
        index++;
        if(fimArquivo()){
            tokenSelecionado = getToken(index);
            tokenSeparado = tokenSelecionado.split(" ");

            analisaVariaveis();
            analisaSubRotina();
            analisaComandos();
        }
    }

    private void analisaVariaveis() throws Exception {
        if(tokenSeparado[simbolo].equals("svar")){
            index++;

            if(fimArquivo()){
                tokenSelecionado = getToken(index);
                tokenSeparado = tokenSelecionado.split(" ");

                if(tokenSeparado[simbolo].equals("sidentificador")){
                    while (tokenSeparado[simbolo].equals("sidentificador")){
                        analisaVariavel();
                        if(tokenSeparado[simbolo].equals("sponto_virgula")){
                            index++;
                        } else {
                            error();
                        }
                    }
                } else {
                    error();
                }
            }
        }
    }

    private void analisaVariavel() throws Exception {
        do {
            if(tokenSeparado[simbolo].equals("sidentificador")) {
                index++;
                tokenSelecionado = getToken(index);
                tokenSeparado = tokenSelecionado.split(" ");

                if((tokenSeparado[simbolo].equals("svirgula")) || (tokenSeparado[simbolo].equals("sdoispontos"))) {
                    if(tokenSeparado[simbolo].equals("svirgula")) {
                        index++;
                        tokenSelecionado = getToken(index);
                        tokenSeparado = tokenSelecionado.split(" ");

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
        }while (tokenSeparado[simbolo].equals("sdoispontos"));
        index++;
        tokenSelecionado = getToken(index);
        tokenSeparado = tokenSelecionado.split(" ");

        analisaTipo();
    }

    private void analisaTipo() throws Exception {
        if(tokenSeparado[simbolo].equals("sinteiro")){
            index++;
            tokenSelecionado = getToken(index);
            tokenSeparado = tokenSelecionado.split(" ");
        } else {
            if(tokenSeparado[simbolo].equals("sboolean")){
                index++;
                tokenSelecionado = getToken(index);
                tokenSeparado = tokenSelecionado.split(" ");
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
                    index++;
                    tokenSelecionado = getToken(index);
                    tokenSeparado = tokenSelecionado.split(" ");
                } else {
                    error();
                }

                if(flag == 1) {
//
                }
            }
        }
    }

    private void analisaDeclaracaoProcedimento() throws Exception {
        index++;
        tokenSelecionado = getToken(index);
        tokenSeparado = tokenSelecionado.split(" ");

        if(tokenSeparado[simbolo].equals("sidentificador")) {
            index++;
            tokenSelecionado = getToken(index);
            tokenSeparado = tokenSelecionado.split(" ");

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
        index++;
        tokenSelecionado = getToken(index);
        tokenSeparado = tokenSelecionado.split(" ");

        if(tokenSeparado[simbolo].equals("sidentificador")) {
            index++;
            tokenSelecionado = getToken(index);
            tokenSeparado = tokenSelecionado.split(" ");

            if((tokenSeparado[simbolo].equals("sinteiro")) || (tokenSeparado[simbolo].equals("sboolean"))) {
                index++;
                tokenSelecionado = getToken(index);
                tokenSeparado = tokenSelecionado.split(" ");

                if(tokenSeparado[simbolo].equals("sponto_virgula")) {
                    analisaBloco();
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
            index++;
            tokenSelecionado = getToken(index);
            tokenSeparado = tokenSelecionado.split(" ");

            analisaComandoSimples();
            while (tokenSeparado[simbolo].equals("sfim")) {
                if(tokenSeparado[simbolo].equals("sponto_virgula")) {
                    index++;
                    tokenSelecionado = getToken(index);
                    tokenSeparado = tokenSelecionado.split(" ");

                    if(!tokenSeparado[simbolo].equals("sfim")) {
                        analisaComandoSimples();
                    }
                } else {
                    error();
                }

                index++;
                tokenSelecionado = getToken(index);
                tokenSeparado = tokenSelecionado.split(" ");
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
        index++;
        tokenSelecionado = getToken(index);
        tokenSeparado = tokenSelecionado.split(" ");

        if(tokenSeparado[simbolo].equals("satribuicao")) {
            analisaAtribuicao();
        } else {
            analisaChamadaProcedimento();
        }
    }

  private void analisaAtribuicao() throws Exception {
      index++;
      tokenSelecionado = getToken(index);
      tokenSeparado = tokenSelecionado.split(" ");

      if(tokenSeparado[simbolo].equals("sidentificador")){
          index++;
          tokenSelecionado = getToken(index);
          tokenSeparado = tokenSelecionado.split(" ");

          if(tokenSeparado[simbolo].equals("satribuicao")) {
              analisaExpressao();
          } else {
              error();
          }
      } else {
          error();
      }
    }

    private void analisaChamadaProcedimento() throws Exception {
        index++;
        tokenSelecionado = getToken(index);
        tokenSeparado = tokenSelecionado.split(" ");

        if(tokenSeparado[simbolo].equals("sidentificador")){

        } else {
            error();
        }

    }

    private void analisaSe() throws Exception {
        index++;
        tokenSelecionado = getToken(index);
        tokenSeparado = tokenSelecionado.split(" ");

        analisaExpressao();
        if(tokenSeparado[simbolo].equals("sentao")) {
            index++;
            tokenSelecionado = getToken(index);
            tokenSeparado = tokenSelecionado.split(" ");

            analisaComandoSimples();

            if(tokenSeparado[simbolo].equals("ssenao")) {
                index++;
                tokenSelecionado = getToken(index);
                tokenSeparado = tokenSelecionado.split(" ");

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
                || (tokenSeparado[simbolo].equals("ig"))
                || (tokenSeparado[simbolo].equals("smenor"))
                || (tokenSeparado[simbolo].equals("smenorig"))
                || (tokenSeparado[simbolo].equals("sdif"))
        ) {
            index++;
            tokenSelecionado = getToken(index);
            tokenSeparado = tokenSelecionado.split(" ");

            analisaExpressaoSimples();
        }
    }

    private void analisaExpressaoSimples() throws Exception {
        if((tokenSeparado[simbolo].equals("smais")) || (tokenSeparado[simbolo].equals("smenos"))) {
            index++;
            tokenSelecionado = getToken(index);
            tokenSeparado = tokenSelecionado.split(" ");

            analisaTermo();

            while ((tokenSeparado[simbolo].equals("smais")) || (tokenSeparado[simbolo].equals("smenos")) || (tokenSeparado[simbolo].equals("sou"))) {
                index++;
                tokenSelecionado = getToken(index);
                tokenSeparado = tokenSelecionado.split(" ");

                analisaTermo();
            }
        }
    }

    private void analisaTermo() throws Exception {
        analisaFator();

        while ((tokenSeparado[simbolo].equals("smult")) || (tokenSeparado[simbolo].equals("sdiv")) || (tokenSeparado[simbolo].equals("se"))) {
            index++;
            tokenSelecionado = getToken(index);
            tokenSeparado = tokenSelecionado.split(" ");

            analisaFator();
        }
    }

    private void analisaFator() throws Exception {
        if(tokenSeparado[simbolo].equals("sidentificador")) {
            analisaChamadaFuncao();
        } else {
            if(tokenSeparado[simbolo].equals("snumero")) {
                index++;
                tokenSelecionado = getToken(index);
                tokenSeparado = tokenSelecionado.split(" ");

            } else {
                if(tokenSeparado[simbolo].equals("snao")) {
                    index++;
                    tokenSelecionado = getToken(index);
                    tokenSeparado = tokenSelecionado.split(" ");

                    analisaFator();
                } else {
                    if(tokenSeparado[simbolo].equals("sabre_parenteses")) {
                        index++;
                        tokenSelecionado = getToken(index);
                        tokenSeparado = tokenSelecionado.split(" ");

                        analisaExpressao();

                        if(tokenSeparado[simbolo].equals("sfecha_parenteses")) {
                            index++;
                            tokenSelecionado = getToken(index);
                            tokenSeparado = tokenSelecionado.split(" ");

                        } else {
                            error();
                        }
                    } else {
                        if((tokenSeparado[simbolo].equals("verdadeiro")) || (tokenSeparado[simbolo].equals("falso"))) {
                            index++;
                            tokenSelecionado = getToken(index);
                            tokenSeparado = tokenSelecionado.split(" ");

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

        index++;
        tokenSelecionado = getToken(index);
        tokenSeparado = tokenSelecionado.split(" ");
    }

    private void analisaEnquanto() throws Exception {
        index++;
        tokenSelecionado = getToken(index);
        tokenSeparado = tokenSelecionado.split(" ");

        analisaExpressao();

        if(tokenSeparado[simbolo].equals("sfaca")) {
            index++;
            tokenSelecionado = getToken(index);
            tokenSeparado = tokenSelecionado.split(" ");

            analisaComandoSimples();
        } else {
            error();
        }
    }

    private void analisaLeia() throws Exception {
        index++;
        tokenSelecionado = getToken(index);
        tokenSeparado = tokenSelecionado.split(" ");

        if(tokenSeparado[simbolo].equals("sabre_parenteses")) {
            index++;
            tokenSelecionado = getToken(index);
            tokenSeparado = tokenSelecionado.split(" ");

            if(tokenSeparado[simbolo].equals("sidentificador")) {
                index++;
                tokenSelecionado = getToken(index);
                tokenSeparado = tokenSelecionado.split(" ");

                if(tokenSeparado[simbolo].equals("sfecha_parenteses")) {
                    index++;
                    tokenSelecionado = getToken(index);
                    tokenSeparado = tokenSelecionado.split(" ");

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
        index++;
        tokenSelecionado = getToken(index);
        tokenSeparado = tokenSelecionado.split(" ");

        if(tokenSeparado[simbolo].equals("sabre_parenteses")) {
            index++;
            tokenSelecionado = getToken(index);
            tokenSeparado = tokenSelecionado.split(" ");

            if(tokenSeparado[simbolo].equals("sidentificador")) {
                index++;
                tokenSelecionado = getToken(index);
                tokenSeparado = tokenSelecionado.split(" ");

                if(tokenSeparado[simbolo].equals("sfecha_parenteses")) {
                    index++;
                    tokenSelecionado = getToken(index);
                    tokenSeparado = tokenSelecionado.split(" ");
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

    private String getToken(int posicao) {
        return listaToken.listaToken.get(posicao);
    }

    private boolean fimArquivo() {
        if(index != tamanhoListaToken) {
            return true;
        } else {
            return false;
        }
    }
}
