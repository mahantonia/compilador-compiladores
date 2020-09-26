package ErroLexico;

public class ErroLexico extends Exception {
    public void printaErro(int linha) throws Exception {
        throw new Exception("Erro Lexico linha " + linha);
    }
}
