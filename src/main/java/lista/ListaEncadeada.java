package lista;

import java.util.Comparator;
/**
 * @param <T> Tipo genérico dos elementos
*/
public class ListaEncadeada<T> implements IColecao<T> {
    private No<T> prim;
    private int tamanho;
    private final Comparator<T> comparador;
    private final boolean ehOrdenada;

    public ListaEncadeada() {
        this(null, false);
    }
/**
     * Construtor da lista encadeada
     * @param comparador Comparator para comparar elementos
     * @param ehOrdenada true para lista ordenada, false para não ordenada
*/ 
    public ListaEncadeada(Comparator<T> comparador, boolean ehOrdenada) {
        this.prim = null;
        this.tamanho = 0;
        this.comparador = comparador;
        this.ehOrdenada = ehOrdenada;
    }
/**
     * Adiciona um elemento à lista.
     * Complexidade: O(1) para lista não ordenada, O(n) para lista ordenada
*/
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
        // Caso 1: lista vazia ou novo valor menor que o primeiro
        if (prim == null || comparador.compare(novoValor, prim.getValor()) <= 0) {
            novoNo.setProx(prim);
            prim = novoNo;
            tamanho++;
            return true;
        }
        
        // Caso 2: procura a posição correta
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
        /**
         * Pesquisa um elemento na lista.
         * Complexidade: O(n) no pior caso
         */
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
         /**
         * Remove um elemento da lista.
         * Complexidade: O(n) no pior caso
         */
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
            // Otimização: se a lista é ordenada e já passou do valor, para
            if (ehOrdenada && cmp > 0) {
                return false;
            }
            anterior = atual;
            atual = atual.getProx();
        }

        return false;
    }
    
    /**
     * Retorna a quantidade de elementos na lista.
     * Complexidade: O(1)
     */
    @Override
    public int quantidadeNos() {
        return tamanho;
    }
    
    /**
     * Retorna a representação em String da lista.
     * Formato: [elemento1,elemento2,...,elementoN]
     * Complexidade: O(n)
     */
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
