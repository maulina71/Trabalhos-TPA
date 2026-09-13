package app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GeradorArquivos {
    
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
        int[] tamanhos = {100000, 200000, 400000, 800000};
        
        for (int tamanho : tamanhos) {
            gerarArquivo(tamanho);
        }
        
        System.out.println("Arquivos gerados com sucesso!");
    }
    
    private static void gerarArquivo(int quantidade) {
        String nomeArquivo = "entrada_" + quantidade + ".txt";
        Random random = new Random(42); // seed fixo para reprodutibilidade
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (int i = 0; i < quantidade; i++) {
                String nome = NOMES[random.nextInt(NOMES.length)] + " " 
                            + SOBRENOMES[random.nextInt(SOBRENOMES.length)];
                String telefone = String.format("%09d", i); // 000000000, 000000001, ...
                
                bw.write(nome + "," + telefone);
                bw.newLine();
            }
            
            System.out.println("Gerado: " + nomeArquivo + " (" + quantidade + " contatos)");
            
        } catch (IOException e) {
            System.err.println("Erro ao gerar " + nomeArquivo + ": " + e.getMessage());
        }
    }
}
