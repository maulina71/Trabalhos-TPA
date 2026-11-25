package model;

public class Fila<T> {
    private class No {
        T valor;
        No proximo;
        No(T valor) { this.valor = valor; }
    }
    
    private No inicio;
    private No fim;

    public void enfileirar(T valor) {
        No novo = new No(valor);
        if (fim == null) { 
            inicio = fim = novo; 
        } else { 
            fim.proximo = novo; 
            fim = novo; 
        }
    }

    public T desenfileirar() {
        if (inicio == null) return null;
        T valor = inicio.valor;
        inicio = inicio.proximo;
        if (inicio == null) fim = null;
        return valor;
    }

    public boolean vazia() { return inicio == null; }
}
