package app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Gera arquivos de contatos com telefones em ORDEM CRESCENTE.
 * Ao serem inseridos em uma árvore binária indexada por telefone,
 * produzem uma árvore COMPLETAMENTE DEGENERADA (uma cadeia à direita).
 */
public class GeradorArquivosOrdenados {

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
        System.out.println("\nArquivos ordenados gerados com sucesso!");
    }

    private static void gerarArquivo(int quantidade) {
        String nomeArquivo = "entrada_" + quantidade + "_ordenado.txt";
        Random random = new Random(42); // reprodutível

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(nomeArquivo, StandardCharsets.UTF_8))) {

            for (int i = 0; i < quantidade; i++) {
                String nome = NOMES[random.nextInt(NOMES.length)] + " "
                            + SOBRENOMES[random.nextInt(SOBRENOMES.length)];
                // i cresce de 0 até quantidade-1, formatado com 9 dígitos.
                // Resultado: ordem lexicográfica == ordem numérica.
                String telefone = String.format("%09d", i);

                bw.write(nome + "," + telefone);
                bw.newLine();
            }

            System.out.println("Gerado: " + nomeArquivo + " (" + quantidade + " contatos)");

        } catch (IOException e) {
            System.err.println("Erro ao gerar " + nomeArquivo + ": " + e.getMessage());
        }
    }
}
