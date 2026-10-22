package app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

/**
 * Gera arquivos de contatos com telefones em ORDEM BALANCEADA.
 * A ordem de inserção é obtida recursivamente pegando o elemento do meio
 * do intervalo. Ao serem inseridos em uma árvore binária indexada por
 * telefone, produzem uma árvore PERFEITAMENTE BALANCEADA.
 */
public class GeradorArquivosBalanceados {

    private static final int[] TAMANHOS = {25_000, 50_000, 100_000, 200_000};

    private static final String[] NOMES = {
        "Ana", "Bruno", "Carla", "Daniel", "Eduardo", "Fernanda", "Gabriel",
        "Helena", "Igor", "Julia", "Karol", "Lucas", "Mariana", "Nathan",
        "Olivia", "Pedro", "Rafaela", "Samuel", "Tatiana", "Victor"
    };

    private static final String[] SOBRENOMES = {
        "Silva", "Santos", "Oliveira", "Souza", "Lima", "Costa",
        "Pereira", "Almeida", "Rodrigues", "Carvalho"
    };

    public static void main(String[] args) {
        for (int tamanho : TAMANHOS) {
            gerarArquivo(tamanho);
        }
        System.out.println("\nArquivos balanceados gerados com sucesso!");
    }

    private static void gerarArquivo(int quantidade) {
        String nomeArquivo = "entrada_" + quantidade + "_balanceado.txt";
        Random random = new Random(42); // mesma seed do ordenado, para comparar com o mesmo conteúdo

        // 1) Monta a lista de índices na ordem "do meio": 0..n-1 embaralhado
        //    pela recursão que sempre pega o elemento central.
        ArrayList<Integer> ordem = new ArrayList<>(quantidade);
        gerarOrdemBalanceada(ordem, 0, quantidade - 1);

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(nomeArquivo, StandardCharsets.UTF_8))) {

            for (int i = 0; i < quantidade; i++) {
                int id = ordem.get(i); // telefone do i-ésimo a ser escrito
                String nome = NOMES[random.nextInt(NOMES.length)] + " "
                            + SOBRENOMES[random.nextInt(SOBRENOMES.length)];
                String telefone = String.format("%09d", id);

                bw.write(nome + "," + telefone);
                bw.newLine();
            }

            System.out.println("Gerado: " + nomeArquivo + " (" + quantidade + " contatos)");

        } catch (IOException e) {
            System.err.println("Erro ao gerar " + nomeArquivo + ": " + e.getMessage());
        }
    }

    /**
     * Preenche `ids` com a sequência de inserção que produz árvore balanceada:
     * primeiro o meio do intervalo, depois recursivamente os subintervalos
     * esquerdo e direito.
     */
    private static void gerarOrdemBalanceada(ArrayList<Integer> ids, int inicio, int fim) {
        if (inicio > fim) return;
        int meio = (inicio + fim) / 2;
        ids.add(meio);
        gerarOrdemBalanceada(ids, inicio, meio - 1);
        gerarOrdemBalanceada(ids, meio + 1, fim);
    }
}
