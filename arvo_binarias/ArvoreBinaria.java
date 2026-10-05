package arvorebinaria;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Árvore Binária de Busca genérica.
 * A ordem é definida pelo Comparator recebido no construtor,
 * o que permite indexar os mesmos objetos por critérios diferentes
 * (por exemplo, nome ou telefone).
 *
 * Chaves duplicadas (comparador retornando 0) SÃO PERMITIDAS e são
 * inseridas na subárvore direita do nó com a mesma chave.
 *
 * @param <T> Tipo genérico dos elementos armazenados
 */
public class ArvoreBinaria<T> extends ArvoreBinariaBase<T> {

    /** Nó raiz da árvore. Null quando a árvore está vazia. */
    protected NoArvore<T> raiz;

    /** Quantidade de nós armazenados. Mantida em O(1). */
    protected int tamanho;

    public ArvoreBinaria(Comparator<T> comparador) {
        super(comparador);
        this.raiz = null;
        this.tamanho = 0;
    }

    /**
     * Adiciona um valor na árvore respeitando a ordem do comparador.
     * Chaves duplicadas (comparador retornando 0) são permitidas e vão
     * para a subárvore direita.
     * Complexidade: O(h), onde h é a altura da árvore.
     */
    @Override
    public boolean adicionar(T novoValor) {
        if (novoValor == null) return false;

        if (raiz == null) {
            raiz = new NoArvore<>(novoValor);
            tamanho++;
            return true;
        }

        NoArvore<T> atual = raiz;
        while (true) {
            int cmp = comparador.compare(novoValor, atual.getValor());
            if (cmp < 0) {
                if (atual.getEsq() == null) {
                    atual.setEsq(new NoArvore<>(novoValor));
                    tamanho++;
                    return true;
                }
                atual = atual.getEsq();
            } else {
                // cmp >= 0: vai para a direita.
                // Duplicatas (cmp == 0) ficam na subárvore direita.
                if (atual.getDir() == null) {
                    atual.setDir(new NoArvore<>(novoValor));
                    tamanho++;
                    return true;
                }
                atual = atual.getDir();
            }
        }
    }

    /**
     * Pesquisa um valor pela chave definida no comparador.
     * Retorna o próprio objeto armazenado (não a cópia usada na busca).
     * Se houver duplicatas, retorna a primeira encontrada descendo pela árvore.
     * Complexidade: O(h).
     */
    @Override
    public T pesquisar(T valor) {
        NoArvore<T> atual = raiz;
        while (atual != null) {
            int cmp = comparador.compare(valor, atual.getValor());
            if (cmp == 0) return atual.getValor();
            atual = (cmp < 0) ? atual.getEsq() : atual.getDir();
        }
        return null;
    }

    /**
     * Remove um valor pela chave.
     * Implementação ITERATIVA, para não estourar a pilha em árvores
     * degeneradas (profundidade ~ n).
     * Complexidade: O(h).
     */
    @Override
    public boolean remover(T valor) {
        if (raiz == null) return false;

        // 1) Localiza o nó com a chave e o pai dele.
        NoArvore<T> pai = null;
        NoArvore<T> atual = raiz;
        boolean achou = false;

        while (atual != null) {
            int cmp = comparador.compare(valor, atual.getValor());
            if (cmp == 0) {
                achou = true;
                break;
            }
            pai = atual;
            atual = (cmp < 0) ? atual.getEsq() : atual.getDir();
        }

        if (!achou) return false;

        // 2) Caso A: no máximo um filho -> basta religar o pai ao filho.
        if (atual.getEsq() == null || atual.getDir() == null) {
            NoArvore<T> filho = (atual.getEsq() != null) ? atual.getEsq() : atual.getDir();

            if (pai == null) {
                raiz = filho;
            } else if (pai.getEsq() == atual) {
                pai.setEsq(filho);
            } else {
                pai.setDir(filho);
            }
        }
        // 3) Caso B: dois filhos -> substitui pelo sucessor em ordem.
        else {
            NoArvore<T> paiSucessor = atual;
            NoArvore<T> sucessor = atual.getDir();
            while (sucessor.getEsq() != null) {
                paiSucessor = sucessor;
                sucessor = sucessor.getEsq();
            }

            // Copia o valor do sucessor para o nó atual.
            atual.setValor(sucessor.getValor());

            // Remove o sucessor da sua posição original.
            // O sucessor não tem filho esquerdo, então ligamos o pai
            // ao filho direito (que pode ser null).
            if (paiSucessor.getEsq() == sucessor) {
                paiSucessor.setEsq(sucessor.getDir());
            } else {
                paiSucessor.setDir(sucessor.getDir());
            }
        }

        tamanho--;
        return true;
    }

    @Override
    public int quantidadeNos() {
        return tamanho;
    }

    /**
     * Altura da árvore. Árvore vazia -> -1. Só raiz -> 0.
     * Implementação ITERATIVA (BFS contando níveis), para não estourar
     * a pilha em árvores degeneradas.
     * Complexidade: O(n).
     */
    @Override
    public int altura() {
        if (raiz == null) return -1;

        Queue<NoArvore<T>> fila = new LinkedList<>();
        fila.add(raiz);
        int altura = -1;

        while (!fila.isEmpty()) {
            int n = fila.size();
            altura++;
            for (int i = 0; i < n; i++) {
                NoArvore<T> atual = fila.poll();
                if (atual.getEsq() != null) fila.add(atual.getEsq());
                if (atual.getDir() != null) fila.add(atual.getDir());
            }
        }
        return altura;
    }

    /**
     * Caminhamento em nível (BFS). Cada nível em uma linha.
     * Formato: [raiz\nnível1\nnível2...]
     */
    @Override
    public String caminharEmNivel() {
        StringBuilder sb = new StringBuilder("[");
        if (raiz == null) {
            sb.append("]");
            return sb.toString();
        }

        Queue<NoArvore<T>> fila = new LinkedList<>();
        fila.add(raiz);
        boolean primeiroNivel = true;

        while (!fila.isEmpty()) {
            if (!primeiroNivel) sb.append("\n");
            primeiroNivel = false;

            int n = fila.size();
            for (int i = 0; i < n; i++) {
                NoArvore<T> atual = fila.poll();
                if (i > 0) sb.append(",");
                sb.append(atual.getValor());
                if (atual.getEsq() != null) fila.add(atual.getEsq());
                if (atual.getDir() != null) fila.add(atual.getDir());
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Caminhamento em ordem (in-order) ITERATIVO.
     * Formato: [e1,e2,...,eN]
     */
    @Override
    public String caminharEmOrdem() {
        StringBuilder sb = new StringBuilder("[");
        java.util.Deque<NoArvore<T>> pilha = new java.util.ArrayDeque<>();
        NoArvore<T> atual = raiz;
        boolean primeiro = true;

        while (atual != null || !pilha.isEmpty()) {
            while (atual != null) {
                pilha.push(atual);
                atual = atual.getEsq();
            }
            atual = pilha.pop();
            if (!primeiro) sb.append(",");
            primeiro = false;
            sb.append(atual.getValor());
            atual = atual.getDir();
        }

        sb.append("]");
        return sb.toString();
    }
}
