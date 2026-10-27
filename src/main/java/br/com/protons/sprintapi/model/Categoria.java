package br.com.protons.sprintapi.model;

// Define as categorias e seus pontos por acerto
public enum Categoria {
    CRIANCA(10),
    JOVEM(20),
    ADULTO(30);

    private final int pontos;

    Categoria(int pontos) {
        this.pontos = pontos;
    }

    public int getPontos() {
        return pontos;
    }
}
