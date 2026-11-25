package model;

public class Aresta<T> {
    private T origem;
    private T destino;
    private float peso;

    public Aresta(T origem, T destino, float peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public T getOrigem() { return origem; }
    public T getDestino() { return destino; }
    public float getPeso() { return peso; }
}
