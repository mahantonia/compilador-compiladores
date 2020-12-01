package ErroSemantico;

public class ErroSematico extends Exception {
    public void printaErro(String conteudo, int linha) throws Exception {
        throw new Exception(conteudo + " linha " + linha);
    }

    public void erroPosFixa(String conteudo) throws Exception {
        throw new Exception(conteudo);
    }
}
