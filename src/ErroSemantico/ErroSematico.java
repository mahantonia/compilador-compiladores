package ErroSemantico;

public class ErroSematico extends Exception {
    public void printaErro(String conteudo) throws Exception {
        throw new Exception(conteudo);
    }
}
