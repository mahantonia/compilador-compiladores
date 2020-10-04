package ErroSintatico;

public class ErroSintatico extends Exception {
    public void printaErro(int linha) throws Exception {
        throw new Exception("Erro Sintatico linha " + linha);
    }
}
