package app;

import lista.IColecao;
import lista.ListaEncadeada;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;

public class progTeste {

    private final Scanner scanner = new Scanner(System.in);
    private final Comparator<Contato> comparadorPorNome = Comparator.comparing(Contato::getNome);
    private final Comparator<Contato> comparadorPorTelefone = Comparator.comparing(Contato::getTelefone);

    private IColecao<Contato> listaPorNome;
    private IColecao<Contato> listaPorTelefone;

    public static void main(String[] args) {
        new progTeste().executar();
    }

    private void executar() {
        boolean ordenada = perguntarSeOrdenada();
        listaPorNome = new ListaEncadeada<>(comparadorPorNome, ordenada);
        listaPorTelefone = new ListaEncadeada<>(comparadorPorTelefone, ordenada);

        boolean rodando = true;
        while (rodando) {
            exibirMenu();
            String opcao = scanner.nextLine().trim();
            switch (opcao) {
                case "1":
                    carregarArquivo();
                    break;
                case "2":
                    adicionarContato();
                    break;
                case "3":
                    pesquisarPorNome();
                    break;
                case "4":
                    pesquisarPorTelefone();
                    break;
                case "5":
                    removerPorTelefone();
                    break;
                case "6":
                    alterarContato();
                    break;
                case "7":
                    sair();
                    rodando = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
            System.out.println();
        }
    }

    private boolean perguntarSeOrdenada() {
        System.out.print("Deseja usar lista ordenada? (S/N): ");
        String resposta = scanner.nextLine().trim();
        return resposta.equalsIgnoreCase("S");
    }

    private void exibirMenu() {
        System.out.println("===== MENU =====");
        System.out.println("1 - Carregar dados de arquivo");
        System.out.println("2 - Adicionar contato");
        System.out.println("3 - Pesquisar contato por nome");
        System.out.println("4 - Pesquisar contato por telefone");
        System.out.println("5 - Remover contato por telefone");
        System.out.println("6 - Alterar dados de contato");
        System.out.println("7 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void carregarArquivo() {
        long inicio = System.nanoTime();
        int lidos = 0;
        int ignorados = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("entrada.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) {
                    continue;
                }

                String[] partes = linha.split(",", 2);
                if (partes.length != 2) {
                    ignorados++;
                    continue;
                }

                String nome = partes[0].trim();
                String telefone = partes[1].trim();

                if (listaPorTelefone.pesquisar(new Contato(null, telefone)) != null) {
                    ignorados++;
                    continue;
                }

                Contato contato = new Contato(nome, telefone);
                listaPorNome.adicionar(contato);
                listaPorTelefone.adicionar(contato);
                lidos++;
            }
        } catch (IOException e) {
            System.out.println("Não foi possível ler o arquivo entrada.txt: " + e.getMessage());
            return;
        }

        long fim = System.nanoTime();
        System.out.println("Contatos carregados: " + lidos);
        System.out.println("Linhas ignoradas (formato inválido ou telefone duplicado): " + ignorados);
        System.out.printf("Tempo de leitura e montagem da(s) lista(s): %.3f ms%n", (fim - inicio) / 1_000_000.0);
    }

    private void adicionarContato() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine().trim();

        if (listaPorTelefone.pesquisar(new Contato(null, telefone)) != null) {
            System.out.println("Já existe um contato com esse telefone.");
            return;
        }

        Contato novo = new Contato(nome, telefone);
        listaPorNome.adicionar(novo);
        listaPorTelefone.adicionar(novo);
        System.out.println("Contato adicionado com sucesso.");
    }

    private void pesquisarPorNome() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();

        long inicio = System.nanoTime();
        Contato encontrado = listaPorNome.pesquisar(new Contato(nome, null));
        long fim = System.nanoTime();

        if (encontrado != null) {
            System.out.println("Telefone: " + encontrado.getTelefone());
        } else {
            System.out.println("Contato não encontrado.");
        }
        System.out.printf("Tempo de busca: %.3f ms%n", (fim - inicio) / 1_000_000.0);
    }

    private void pesquisarPorTelefone() {
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine().trim();

        long inicio = System.nanoTime();
        Contato encontrado = listaPorTelefone.pesquisar(new Contato(null, telefone));
        long fim = System.nanoTime();

        if (encontrado != null) {
            System.out.println("Nome: " + encontrado.getNome());
        } else {
            System.out.println("Contato não encontrado.");
        }
        System.out.printf("Tempo de busca: %.3f ms%n", (fim - inicio) / 1_000_000.0);
    }

    private void removerPorTelefone() {
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine().trim();

        Contato alvo = listaPorTelefone.pesquisar(new Contato(null, telefone));

        long inicio = System.nanoTime();
        boolean removidoDaListaPorTelefone = listaPorTelefone.remover(new Contato(null, telefone));
        long fim = System.nanoTime();

        if (removidoDaListaPorTelefone && alvo != null) {
            listaPorNome.remover(alvo);
            System.out.println("Contato removido com sucesso.");
        } else {
            System.out.println("Contato não existia.");
        }
        System.out.printf("Tempo de remoção: %.3f ms%n", (fim - inicio) / 1_000_000.0);
    }

    private void alterarContato() {
        System.out.print("Nome atual do contato: ");
        String nomeAtual = scanner.nextLine().trim();

        Contato encontrado = listaPorNome.pesquisar(new Contato(nomeAtual, null));
        if (encontrado == null) {
            System.out.println("Contato não encontrado.");
            return;
        }

        System.out.println("Telefone atual: " + encontrado.getTelefone());

        System.out.print("Novo nome: ");
        String novoNome = scanner.nextLine().trim();
        System.out.print("Novo telefone: ");
        String novoTelefone = scanner.nextLine().trim();

        if (!novoTelefone.equals(encontrado.getTelefone())
                && listaPorTelefone.pesquisar(new Contato(null, novoTelefone)) != null) {
            System.out.println("Já existe outro contato com esse telefone. Alteração cancelada.");
            return;
        }

        listaPorNome.remover(encontrado);
        listaPorTelefone.remover(encontrado);

        encontrado.setNome(novoNome);
        encontrado.setTelefone(novoTelefone);

        listaPorNome.adicionar(encontrado);
        listaPorTelefone.adicionar(encontrado);

        System.out.println("Contato atualizado com sucesso.");
    }

    private void sair() {
        System.out.println("Quantidade total de contatos: " + listaPorTelefone.quantidadeNos());
        System.out.println("Encerrando programa...");
    }
}
