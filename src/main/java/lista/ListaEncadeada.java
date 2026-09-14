package lista;

import java.util.Comparator;

public class ListaEncadeada<T> implements IColecao<T> {

    private No<T> prim;
    private int tamanho;
    private final Comparator<T> comparador;
    private final boolean ehOrdenada;

    public ListaEncadeada() {
        this(null, false);
    }

    public ListaEncadeada(Comparator<T> comparador, boolean ehOrdenada) {
        this.prim = null;
        this.tamanho = 0;
        this.comparador = comparador;
        this.ehOrdenada = ehOrdenada;
    }

    @Override
    public boolean adicionar(T novoValor) {
        No<T> novoNo = new No<>(novoValor);

        // Lista não ordenada: insere no início
        if (!ehOrdenada) {
            novoNo.setProx(prim);
            prim = novoNo;
            tamanho++;
            return true;
        }

        // Lista ordenada: insere na posição correta
        if (prim == null || comparador.compare(novoValor, prim.getValor()) <= 0) {
            novoNo.setProx(prim);
            prim = novoNo;
            tamanho++;
            return true;
        }

        No<T> atual = prim;
        while (atual.getProx() != null
                && comparador.compare(novoValor, atual.getProx().getValor()) > 0) {
            atual = atual.getProx();
        }

        novoNo.setProx(atual.getProx());
        atual.setProx(novoNo);
        tamanho++;
        return true;
    }

    @Override
    public T pesquisar(T valor) {
        No<T> atual = prim;

        while (atual != null) {
            int cmp = comparador.compare(atual.getValor(), valor);
            if (cmp == 0) {
                return atual.getValor();
            }
            // Otimização: se a lista é ordenada e já passou do valor, para
            if (ehOrdenada && cmp > 0) {
                return null;
            }
            atual = atual.getProx();
        }

        return null;
    }

    @Override
    public boolean remover(T valor) {
        No<T> atual = prim;
        No<T> anterior = null;

        while (atual != null) {
            int cmp = comparador.compare(atual.getValor(), valor);
            if (cmp == 0) {
                if (anterior == null) {
                    prim = atual.getProx();
                } else {
                    anterior.setProx(atual.getProx());
                }
                tamanho--;
                return true;
            }
            if (ehOrdenada && cmp > 0) {
                return false;
            }
            anterior = atual;
            atual = atual.getProx();
        }

        return false;
    }

    @Override
    public int quantidadeNos() {
        return tamanho;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        No<T> atual = prim;
        while (atual != null) {
            sb.append(atual.getValor());
            if (atual.getProx() != null) {
                sb.append(",");
            }
            atual = atual.getProx();
        }
        sb.append("]");
        return sb.toString();
    }
}
