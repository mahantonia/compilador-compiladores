package ErroSintatico;

public class ErroSintatico extends Exception {
    public void printaErro(String conteudo, int linha) throws Exception {
        throw new Exception("Erro Sintatico - " + conteudo + "linha " + (linha));
    }

    public void printaErroSemPontoFim() throws Exception {
        throw new Exception("Erro Sintatico faltou ponto no final ");
    }
}
