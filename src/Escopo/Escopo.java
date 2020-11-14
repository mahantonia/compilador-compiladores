package Escopo;

import java.util.Objects;

public class Escopo {
    public int nivel;

    public Escopo(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() { return nivel; }

    public void adicionarNivel() {
        nivel++;
    }

    public void removerNivel() {
        nivel--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Escopo escopo = (Escopo) o;
        return nivel == escopo.nivel;
    }
}
