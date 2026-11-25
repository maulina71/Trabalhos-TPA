package model;

public class Vertice<T> {
    private T valor;
    private Lista<Aresta<T>> arestas;
    private boolean visitado; 

    public Vertice(T valor) {
        this.valor = valor;
        this.arestas = new Lista<>();
        this.visitado = false;
    }

    public T getValor() { return valor; }
    public Lista<Aresta<T>> getArestas() { return arestas; }
    public boolean isVisitado() { return visitado; }
    public void setVisitado(boolean visitado) { this.visitado = visitado; }
}
