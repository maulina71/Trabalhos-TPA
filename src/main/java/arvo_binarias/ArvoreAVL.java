package arvo_binarias;

import java.util.Comparator;

/**
 * Árvore AVL genérica.
 * Herda de ArvoreBinaria e sobrescreve adicionar/remover para aplicar
 * rotações e manter a árvore balanceada.
 *
 * A altura é armazenada em cada nó (NoArvore.altura), garantindo que
 * fatorBalanceamento e as rotações sejam O(1).
 *
 * Complexidade: O(log n) para adicionar, remover e pesquisar.
 *
 * @param <T> Tipo genérico dos elementos armazenados
 */
public class ArvoreAVL<T> extends ArvoreBinaria<T> {

    public ArvoreAVL(Comparator<T> comparador) {
        super(comparador);
    }

    // ======================== AUXILIARES O(1) ========================

    /** Altura de um nó (nulo = -1). O(1). */
    private int altura(NoArvore<T> no) {
        return (no == null) ? -1 : no.getAltura();
    }

    /** Atualiza a altura do nó com base nos filhos. O(1). */
    private void atualizarAltura(NoArvore<T> no) {
        if (no != null) {
            no.setAltura(Math.max(altura(no.getEsq()), altura(no.getDir())) + 1);
        }
    }

    /** Fator de balanceamento = altura(esq) - altura(dir). O(1). */
    private int fatorBalanceamento(NoArvore<T> no) {
        if (no == null) return 0;
        return altura(no.getEsq()) - altura(no.getDir());
    }

    /** Rotação simples à direita. O(1). */
    private NoArvore<T> rotacaoDireita(NoArvore<T> y) {
        NoArvore<T> x = y.getEsq();
        NoArvore<T> T2 = x.getDir();

        x.setDir(y);
        y.setEsq(T2);

        atualizarAltura(y);
        atualizarAltura(x);

        return x;
    }

    /** Rotação simples à esquerda. O(1). */
    private NoArvore<T> rotacaoEsquerda(NoArvore<T> x) {
        NoArvore<T> y = x.getDir();
        NoArvore<T> T2 = y.getEsq();

        y.setEsq(x);
        x.setDir(T2);

        atualizarAltura(x);
        atualizarAltura(y);

        return y;
    }

    /** Rebalanceia o nó e retorna a nova raiz da subárvore. O(1). */
    private NoArvore<T> rebalancear(NoArvore<T> no) {
        atualizarAltura(no);
        int fb = fatorBalanceamento(no);

        // Caso Esquerda-Esquerda
        if (fb > 1 && fatorBalanceamento(no.getEsq()) >= 0) {
            return rotacaoDireita(no);
        }
        // Caso Esquerda-Direita
        if (fb > 1 && fatorBalanceamento(no.getEsq()) < 0) {
            no.setEsq(rotacaoEsquerda(no.getEsq()));
            return rotacaoDireita(no);
        }
        // Caso Direita-Direita
        if (fb < -1 && fatorBalanceamento(no.getDir()) <= 0) {
            return rotacaoEsquerda(no);
        }
        // Caso Direita-Esquerda
        if (fb < -1 && fatorBalanceamento(no.getDir()) > 0) {
            no.setDir(rotacaoDireita(no.getDir()));
            return rotacaoEsquerda(no);
        }
        return no;
    }

    // ======================== ADICIONAR ========================

    /**
     * Adiciona um valor e rebalanceia a árvore.
     * Complexidade: O(log n).
     */
    @Override
    public boolean adicionar(T novoValor) {
        if (novoValor == null) return false;
        int antes = tamanho;
        raiz = adicionarRec(raiz, novoValor);
        return tamanho > antes;
    }

    private NoArvore<T> adicionarRec(NoArvore<T> no, T valor) {
        if (no == null) {
            tamanho++;
            return new NoArvore<>(valor);
        }

        int cmp = comparador.compare(valor, no.getValor());
        if (cmp < 0) {
            no.setEsq(adicionarRec(no.getEsq(), valor));
        } else {
            no.setDir(adicionarRec(no.getDir(), valor));
        }

        return rebalancear(no);
    }

    // ======================== REMOVER ========================

    /**
     * Remove um valor e rebalanceia a árvore.
     * Complexidade: O(log n).
     */
    @Override
    public boolean remover(T valor) {
        int antes = tamanho;
        raiz = removerRec(raiz, valor);
        return tamanho < antes;
    }

    private NoArvore<T> removerRec(NoArvore<T> no, T valor) {
        if (no == null) return null;

        int cmp = comparador.compare(valor, no.getValor());

        if (cmp < 0) {
            no.setEsq(removerRec(no.getEsq(), valor));
        } else if (cmp > 0) {
            no.setDir(removerRec(no.getDir(), valor));
        } else {
            // Encontrou
            if (no.getEsq() == null || no.getDir() == null) {
                NoArvore<T> filho = (no.getEsq() != null) ? no.getEsq() : no.getDir();
                tamanho--;
                return filho;
            } else {
                NoArvore<T> sucessor = menorNo(no.getDir());
                no.setValor(sucessor.getValor());
                no.setDir(removerRec(no.getDir(), sucessor.getValor()));
            }
        }

        return rebalancear(no);
    }

    private NoArvore<T> menorNo(NoArvore<T> no) {
        while (no.getEsq() != null) no = no.getEsq();
        return no;
    }
}
