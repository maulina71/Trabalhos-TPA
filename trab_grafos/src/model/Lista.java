package model;

public class Lista<T> {
    private class No {
        T valor;
        No proximo;
        No(T valor) { this.valor = valor; this.proximo = null; }
    }

    private No inicio;
    private No fim;
    private int tamanho;

    public Lista() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }

    public void adicionar(T valor) {
        No novo = new No(valor);
        if (inicio == null) {
            inicio = novo;
            fim = novo;
        } else {
            fim.proximo = novo;
            fim = novo;
        }
        tamanho++;
    }

    public T obter(int i) {
        if (i < 0 || i >= tamanho) return null;
        No atual = inicio;
        for (int k = 0; k < i; k++) {
            atual = atual.proximo;
        }
        return atual.valor;
    }

    public int tamanho() { return tamanho; }
    public boolean vazia() { return tamanho == 0; }
}
