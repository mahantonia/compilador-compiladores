package Lexico;

import ErroLexico.ErroLexico;
import Token.Token;

public class Lexico {
    private int i = 0;
    private char caracter;
    private String palavra;
    private int linha;
    private String conteudoArquivo;
    private Token token;

    public String getConteudoArquivo() { return conteudoArquivo; }

    public Lexico(String conteudo, Token token) {
        conteudoArquivo = conteudo;
        this.token = token;
        i = 0;
        linha = 1;
    }

    public void adicionaTokenLista() throws Exception {

        palavra = getConteudoArquivo();
        caracter = palavra.charAt(i);

        while (caracter != '\u0000'){
            while ((caracter == '{') || (caracter == ' ') || (caracter == '\n')  || (caracter == '\t')|| (caracter == '/') && (i < palavra.length())) {
                if(caracter == '{'){
                    while ((caracter != '}') && (i < palavra.length())) {
                        if(caracter == '\n'){
                            linha++;
                        }
                        if(caracter == '\u0000'){
                            error();
                        }
                        i++;
                        caracter = palavra.charAt(i);
                    }
                    i++;
                    caracter = palavra.charAt(i);
                } else {
                    if(caracter == '/'){
                        i++;
                        caracter = palavra.charAt(i);
                        if(caracter != '*'){
                            caracter = palavra.charAt(i-1);
                            error();
                        } else {
                            do {
                                while ((caracter != '/') ){
                                    if(caracter == '\n'){
                                        linha++;
                                    }
                                    if(caracter == '\u0000'){
                                        error();
                                    }
                                    i++;
                                    caracter = palavra.charAt(i);
                                }
                                caracter = palavra.charAt(i-1);
                            } while (caracter != '*');
                            i++;
                            caracter = palavra.charAt(i);
                        }
                    }else{
                        if(caracter == ' '){
                            i++;
                            caracter = palavra.charAt(i);
                        } else {
                            if(caracter == '\n'){
                                linha++;
                                i++;
                                caracter = palavra.charAt(i);
                            } else {
                                if(caracter == '\t') {
                                    i++;
                                    caracter = palavra.charAt(i);
                                } else {
                                    error();
                                }
                            }
                        }
                    }
                }
            }
            if(caracter != '\u0000') {
                geraToken();
                break;
            } else {
                if(caracter != palavra.length()) {
                    i++;
                }
            }
        }
    }

    private void geraToken() throws Exception {
        if (Character.isDigit(caracter)) {
            trataDigito();
        } else {
            if (Character.isLetter(caracter)) {
                trataIdentificarPalavraReservada();
            } else {
                if (caracter == ':') {
                    trataAtribuicao();
                } else {
                    if ((caracter == '+') || (caracter == '-') || (caracter == '*')) {
                        trataOperadorAritmetico();
                    } else {
                        if ((caracter == '>') || (caracter == '<') || (caracter == '=') || (caracter == '!')) {
                            trataOperadorRelacional();
                        } else {
                            if ((caracter == ';') || (caracter == ',') || (caracter == '(') || (caracter == ')') || (caracter == '.')) {
                                trataPontuacao();
                            } else {
                                error();
                            }
                        }
                    }
                }
            }
        }
    }

    private void trataDigito() {
        String num = "";

        num = num + caracter;

        if(i != palavra.length()){
            i++;

            caracter = palavra.charAt(i);

            while (Character.isDigit(caracter)){

                num = num + caracter;

                if(i == palavra.length()){
                    break;
                } else {
                    i++;
                    caracter = palavra.charAt(i);
                }
            }
        }

        token.setLexema(num);
        token.setSimbolo("snumero");
        token.setLinha(Integer.toString(linha));

        token.addListaToken(new Token(token.getSimbolo(), token.getLexema(), token.getLinha()));
    }

    private void trataIdentificarPalavraReservada() throws Exception {
        String id = "";

        id = id + caracter;

        if(id.equals("_")){
            throw new Exception("Erro linha " + linha);
        }

        if(i != palavra.length()){
            i++;
            caracter = palavra.charAt(i);

            while ((Character.isLetterOrDigit(caracter)) || (caracter == '_')){
                id = id + caracter;

                if(i == palavra.length()){
                    break;
                } else {
                    i++;
                    caracter = palavra.charAt(i);
                }
            }
        }
        verificaSimboloPalavraReservada(id);
    }

    private void verificaSimboloPalavraReservada(String id) {
        switch (id){
            case "programa":
                token.setSimbolo("sprograma");
                break;
            case "inicio":
                token.setSimbolo("sinicio");
                break;
            case "fim":
                token.setSimbolo("sfim");
                break;
            case "procedimento":
                token.setSimbolo("sprocedimento");
                break;
            case "funcao":
                token.setSimbolo("sfuncao");
                break;
            case "se":
                token.setSimbolo("sse");
                break;
            case "entao":
                token.setSimbolo("sentao");
                break;
            case "senao":
                token.setSimbolo("ssenao");
                break;
            case "enquanto":
                token.setSimbolo("senquanto");
                break;
            case "faca":
                token.setSimbolo("sfaca");
                break;
            case "escreva":
                token.setSimbolo("sescreva");
                break;
            case "leia":
                token.setSimbolo("sleia");
                break;
            case "var":
                token.setSimbolo("svar");
                break;
            case "inteiro":
                token.setSimbolo("sinteiro");
                break;
            case "booleano":
                token.setSimbolo("sboolean");
                break;
            case "div":
                token.setSimbolo("sdiv");
                break;
            case "e":
                token.setSimbolo("se");
                break;
            case "ou":
                token.setSimbolo("sou");
                break;
            case "nao":
                token.setSimbolo("snao");
                break;
            case "verdadeiro":
                token.setSimbolo("sverdadeiro");
                break;
            case "falso":
                token.setSimbolo("sfalso");
                break;
            default:
                token.setSimbolo("sidentificador");
                break;
        }

        token.setLexema(id);
        token.setLinha(Integer.toString(linha));

        token.addListaToken(new Token(token.getSimbolo(), token.getLexema(), token.getLinha()));
    }

    private void trataOperadorAritmetico() {
        switch (caracter){
            case '+':
                token.setLexema("+");
                token.setSimbolo("smais");
                break;
            case '-':
                token.setLexema("-");
                token.setSimbolo("smenos");
                break;
            case '*':
                token.setLexema("*");
                token.setSimbolo("smult");
                break;
        }
        token.setLinha(Integer.toString(linha));
        i++;
        caracter = palavra.charAt(i);

        token.addListaToken(new Token(token.getSimbolo(), token.getLexema(), token.getLinha()));
    }

    private void trataOperadorRelacional() throws Exception {
        String op = "";

        op = op + caracter;

        if(op.equals("=")){
            verificaOperadorRelacional(op);
        } else {
            if((i != palavra.length())){
                i++;
                caracter = palavra.charAt(i);

                if(caracter == '='){
                    op = op + caracter;
                }

                verificaOperadorRelacional(op);
            }
        }
    }

    private void verificaOperadorRelacional(String operador) throws Exception {
        switch(operador){
            case "=":
                token.setSimbolo("sig");
                token.setLexema("=");
                break;
            case "!=":
                token.setSimbolo("sdif");
                token.setLexema("!=");
                break;

            case ">=":
                token.setSimbolo("smaiorig");
                token.setLexema(">=");
                break;

            case "<=":
                token.setSimbolo("smenorig");
                token.setLexema("<=");
                break;

            case ">":
                token.setSimbolo("smaior");
                token.setLexema(">");
                break;
            case "<":
                token.setSimbolo("smenor");
                token.setLexema("<");
                break;
            default:
                error();
                break;
        }
        token.setLinha(Integer.toString(linha));
        if(caracter == '='){
            i++;
            caracter = palavra.charAt(i);
        }

        token.addListaToken(new Token(token.getSimbolo(), token.getLexema(), token.getLinha()));
    }

    private void trataPontuacao() throws Exception {
        switch (caracter){
            case ';':
                token.setSimbolo("sponto_virgula");
                token.setLexema(";");
                break;
            case ',':
                token.setSimbolo("svirgula");
                token.setLexema(",");
                break;
            case '(':
                token.setSimbolo("sabre_parenteses");
                token.setLexema("(");
                break;
            case ')':
                token.setSimbolo("sfecha_parenteses");
                token.setLexema(")");
                break;
            case '.':
                token.setSimbolo("sponto");
                token.setLexema(".");
                break;
            default:
               error();
               break;
        }
        token.setLinha(Integer.toString(linha));
        i++;
        caracter = palavra.charAt(i);

        token.addListaToken(new Token(token.getSimbolo(), token.getLexema(), token.getLinha()));
    }

    private void trataAtribuicao() {
        if(i != palavra.length()){
            i++;
            caracter = palavra.charAt(i);
            if(caracter == '='){
                token.setLexema(":=");
                token.setSimbolo("satribuicao");

                i++;
                caracter = palavra.charAt(i);
            } else {
                token.setLexema(":");
                token.setSimbolo("sdoispontos");
            }
            token.setLinha(Integer.toString(linha));
            token.addListaToken(new Token(token.getSimbolo(), token.getLexema(), token.getLinha()));
        }
    }

    private void error() throws Exception {
        new ErroLexico().printaErro(linha);
    }
}
