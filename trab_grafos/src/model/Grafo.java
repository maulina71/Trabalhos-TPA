package model;

public class Grafo<T> {
    private Lista<Vertice<T>> vertices;

    public Grafo() {
        this.vertices = new Lista<>();
    }

    // (i) Adicionar vértice (não duplica)
    public boolean adicionarVertice(T valor) {
        if (buscarVertice(valor) != null) return false;
        vertices.adicionar(new Vertice<>(valor));
        return true;
    }

    public Vertice<T> buscarVertice(T valor) {
        for (int i = 0; i < vertices.tamanho(); i++) {
            Vertice<T> v = vertices.obter(i);
            if (v.getValor().equals(valor)) return v;
        }
        return null;
    }

    // (ii) Adicionar aresta
    public boolean adicionarAresta(T origem, T destino, float peso) {
        Vertice<T> vo = buscarVertice(origem);
        Vertice<T> vd = buscarVertice(destino);
        if (vo == null || vd == null) return false;
        
        vo.getArestas().adicionar(new Aresta<>(origem, destino, peso));
        return true;
    }

    // (iii) Busca em Largura (BFS)
    public void buscaEmLargura(T origem) {
        Vertice<T> inicio = buscarVertice(origem);
        if (inicio == null) {
            System.out.println("Vértice não encontrado: " + origem);
            return;
        }

        // Zera os visitados antes de começar
        for (int i = 0; i < vertices.tamanho(); i++) {
            vertices.obter(i).setVisitado(false);
        }

        Fila<Vertice<T>> fila = new Fila<>();
        fila.enfileirar(inicio);
        inicio.setVisitado(true);

        System.out.println("BFS a partir de " + origem + ":");
        while (!fila.vazia()) {
            Vertice<T> atual = fila.desenfileirar();
            System.out.print(atual.getValor() + " -> ");

            Lista<Aresta<T>> adj = atual.getArestas();
            for (int i = 0; i < adj.tamanho(); i++) {
                Aresta<T> aresta = adj.obter(i);
                Vertice<T> vizinho = buscarVertice(aresta.getDestino());
                if (vizinho != null && !vizinho.isVisitado()) {
                    fila.enfileirar(vizinho);
                    vizinho.setVisitado(true);
                }
            }
        }
        System.out.println("FIM");
    }
}
