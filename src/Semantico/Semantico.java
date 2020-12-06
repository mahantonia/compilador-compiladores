package Semantico;

import ErroSemantico.ErroSematico;
import GeracaoCodigo.GeracaoCodigo;
import PosFixa.PosFixa;
import TabelaSimbolo.TabelaSimbolo;

public class Semantico {
    TabelaSimbolo tabelaSimbolo;
    PosFixa posFixa;
    GeracaoCodigo geracaoCodigo;

    public Semantico(GeracaoCodigo geracaoCodigo) {
        tabelaSimbolo = new TabelaSimbolo();
        posFixa = new PosFixa(tabelaSimbolo, geracaoCodigo);
        this.geracaoCodigo = geracaoCodigo;
    }

    public TabelaSimbolo getTabelaSimbolo() {
        return tabelaSimbolo;
    }

    public PosFixa getPosFixa() {
        return posFixa;
    }

    public void errorSemantico(String conteudo, int linha) throws Exception {
        new ErroSematico().printaErro(conteudo,  linha);
    }
}
