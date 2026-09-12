package lista;

import java.util.Comparator;

/**
 * @param <T> Tipo genérico dos elementos
 */
public class ListaEncadeada<T> implements IColecao<T> {
    
    private No<T> prim;
    private int tamanho;
    private Comparator<T> comparador;
    private boolean ordenada;
    
    /**
     * Construtor da lista encadeada
     * @param comparador Comparator para comparar elementos
     * @param ordenada true para lista ordenada, false para não ordenada
     */
    public ListaEncadeada(Comparator<T> comparador, boolean ordenada) {
        this.prim = null;
        this.tamanho = 0;
        this.comparador = comparador;
        this.ordenada = ordenada;
    }
    
    /**
     * Adiciona um elemento à lista.
     * Complexidade: O(1) para lista não ordenada, O(n) para lista ordenada
     */
    @Override
    public void adicionar(T novoValor) {
        No<T> novoNo = new No<>(novoValor);
        
        // Lista não ordenada: insere no início
        if (!ordenada) {
            novoNo.setProximo(prim);
            prim = novoNo;
            tamanho++;
            return;
        }
        
        // Lista ordenada: insere na posição correta
        
        // Caso 1: lista vazia ou novo valor menor que o primeiro
        if (prim == null || comparador.compare(novoValor, prim.getValor()) <= 0) {
            novoNo.setProximo(prim);
            prim = novoNo;
            tamanho++;
            return;
        }
        
        // Caso 2: procura a posição correta
        No<T> atual = prim;
        while (atual.getProximo() != null 
                && comparador.compare(novoValor, atual.getProximo().getValor()) > 0) {
            atual = atual.getProximo();
        }
        
        novoNo.setProximo(atual.getProximo());
        atual.setProximo(novoNo);
        tamanho++;
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
            if (ordenada && cmp > 0) {
                return null;
            }
            
            atual = atual.getProximo();
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
                // Encontrou: remove o nó
                if (anterior == null) {
                    prim = atual.getProximo();
                } else {
                    anterior.setProximo(atual.getProximo());
                }
                tamanho--;
                return true;
            }
            
            // Otimização: se a lista é ordenada e já passou do valor, para
            if (ordenada && cmp > 0) {
                return false;
            }
            
            anterior = atual;
            atual = atual.getProximo();
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
            if (atual.getProximo() != null) {
                sb.append(",");
            }
            atual = atual.getProximo();
        }
        
        sb.append("]");
        return sb.toString();
    }
}
