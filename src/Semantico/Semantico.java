package Semantico;

import PosFixa.PosFixa;
import TabelaSimbolo.TabelaSimbolo;

public class Semantico {
    TabelaSimbolo tabelaSimbolo;
    PosFixa posFixa;

    public Semantico() {
        tabelaSimbolo = new TabelaSimbolo();
        posFixa = new PosFixa(tabelaSimbolo);
    }

    public TabelaSimbolo getTabelaSimbolo() {
        return tabelaSimbolo;
    }

    public PosFixa getPosFixa() {
        return posFixa;
    }
}
