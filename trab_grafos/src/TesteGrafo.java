import model.Grafo;

public class TesteGrafo {
    public static void main(String[] args) {
        System.out.println("--- TESTANDO A BIBLIOTECA DE GRAFOS ---");

        // 1. Criando o grafo
        Grafo<String> grade = new Grafo<>();

        // 2. Adicionando as matérias (vértices)
        grade.adicionarVertice("Calculo1");
        grade.adicionarVertice("Calculo2");
        grade.adicionarVertice("Fisica1");
        grade.adicionarVertice("Algoritmos");

        // 3. Adicionando os pré-requisitos (arestas: Origem, Destino, Peso)
        // Ex: Para fazer Calculo2, precisa de Calculo1
        grade.adicionarAresta("Calculo1", "Calculo2", 1.0f);
        grade.adicionarAresta("Calculo2", "Fisica1", 1.0f);
        grade.adicionarAresta("Calculo1", "Algoritmos", 1.0f);

        // 4. Testando o caminhamento (BFS) a partir de Calculo1
        grade.buscaEmLargura("Calculo1");
        
        System.out.println("---------------------------------------");
    }
}
