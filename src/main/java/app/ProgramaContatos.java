package app;

import lista.IColecao;
import lista.ListaEncadeada;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;

// Programa que usa a biblioteca ListaEncadeada (pacote "lista") para
// gerenciar uma agenda de contatos via menu no console.
//
// Mantemos duas coleções (IColecao<Contato>) guardando os mesmos objetos
// Contato: uma organizada por nome e outra por telefone. Assim conseguimos
// pesquisar rapidamente dos dois jeitos sem precisar alterar a biblioteca.
public class ProgramaContatos {

    private final Scanner scanner = new Scanner(System.in);

    // Compara os contatos pelo nome, ignorando maiúsculas/minúsculas
    // (senão "Ana" e "ana" seriam tratados como nomes diferentes).
    private final Comparator<Contato> comparadorPorNome =
            (c1, c2) -> c1.getNome().compareToIgnoreCase(c2.getNome());

    // Compara pelo telefone. Aqui não precisa ignorar caixa, já que
    // telefone não tem letra.
    private final Comparator<Contato> comparadorPorTelefone =
            (c1, c2) -> c1.getTelefone().compareTo(c2.getTelefone());

    private IColecao<Contato> listaPorNome;
    private IColecao<Contato> listaPorTelefone;

    public static void main(String[] args) {
        new ProgramaContatos().executar();
    }

    // Pergunta o modo de operação, instancia as duas listas nesse mesmo
    // modo e mantém o menu rodando até o usuário escolher sair.
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
                    rodando = false; // encerra o laço depois de mostrar o resumo em sair()
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
            System.out.println(); // linha em branco só para separar visualmente cada rodada do menu
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

    // Lê "entrada.txt" (uma linha por contato, no formato "nome,telefone")
    // e insere cada contato válido nas duas listas. Mede o tempo total da
    // operação (leitura do arquivo + montagem das listas), como pede a
    // especificação do trabalho.
    private void carregarArquivo() {
        long inicio = System.nanoTime();
        int lidos = 0;
        int ignorados = 0;

        // try-with-resources garante que o arquivo é fechado no final,
        // mesmo se ocorrer um erro no meio da leitura.
        try (BufferedReader br = new BufferedReader(new FileReader("entrada.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) {
                    continue;
                }

                String[] partes = linha.split(",", 2);
                if (partes.length != 2) {
                    // linha fora do formato esperado: ignora e segue para a próxima
                    ignorados++;
                    continue;
                }

                String nome = partes[0].trim();
                String telefone = partes[1].trim();

                // Checa se o telefone já está em uso antes de adicionar. Essa
                // regra é do programa, não da lista — a lista aceitaria
                // duplicados sem problema. Usamos "" no lugar do nome porque
                // o comparadorPorTelefone não olha esse campo.
                if (listaPorTelefone.pesquisar(new Contato("", telefone)) != null) {
                    ignorados++;
                    continue;
                }

                // Contato válido: cria o objeto e adiciona nas duas listas
                // (é o mesmo objeto compartilhado entre elas).
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
        // Esses números vão direto para as tabelas/gráficos da seção 3 do relatório.
        System.out.println("Contatos carregados: " + lidos);
        System.out.println("Linhas ignoradas (formato inválido ou telefone duplicado): " + ignorados);
        System.out.printf("Tempo de leitura e montagem da(s) lista(s): %.3f ms%n", (fim - inicio) / 1_000_000.0);
    }

    // Pede nome e telefone e adiciona um novo contato, desde que o
    // telefone ainda não esteja cadastrado em outro contato.
    private void adicionarContato() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine().trim();

        if (listaPorTelefone.pesquisar(new Contato("", telefone)) != null) {
            System.out.println("Já existe um contato com esse telefone.");
            return;
        }

        Contato novo = new Contato(nome, telefone);
        listaPorNome.adicionar(novo);
        listaPorTelefone.adicionar(novo);
        System.out.println("Contato adicionado com sucesso.");
    }

    // Busca um contato pelo nome na listaPorNome. Se houver mais de um
    // contato com o mesmo nome, apenas um deles é retornado — comportamento
    // aceito pela especificação do trabalho.
    private void pesquisarPorNome() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();

        // Cronometra só a chamada de pesquisar, que é a operação que o
        // enunciado pede para medir.
        long inicio = System.nanoTime();
        Contato encontrado = listaPorNome.pesquisar(new Contato(nome, ""));
        long fim = System.nanoTime();

        if (encontrado != null) {
            System.out.println("Telefone: " + encontrado.getTelefone());
        } else {
            System.out.println("Contato não encontrado.");
        }
        System.out.printf("Tempo de busca: %.3f ms%n", (fim - inicio) / 1_000_000.0);
    }

    // Mesma lógica de pesquisarPorNome, usando a listaPorTelefone.
    private void pesquisarPorTelefone() {
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine().trim();

        long inicio = System.nanoTime();
        Contato encontrado = listaPorTelefone.pesquisar(new Contato("", telefone));
        long fim = System.nanoTime();

        if (encontrado != null) {
            System.out.println("Nome: " + encontrado.getNome());
        } else {
            System.out.println("Contato não encontrado.");
        }
        System.out.printf("Tempo de busca: %.3f ms%n", (fim - inicio) / 1_000_000.0);
    }

    // Remove um contato pelo telefone. Localizamos o objeto antes de
    // cronometrar, para depois conseguir removê-lo também da listaPorNome.
    private void removerPorTelefone() {
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine().trim();

        Contato alvo = listaPorTelefone.pesquisar(new Contato("", telefone));

        // Só a remoção na listaPorTelefone é cronometrada, já que é essa a
        // operação pedida pelo enunciado. A remoção na listaPorNome logo
        // abaixo é apenas para manter as duas listas consistentes.
        long inicio = System.nanoTime();
        boolean removidoDaListaPorTelefone = listaPorTelefone.remover(new Contato("", telefone));
        long fim = System.nanoTime();

        if (removidoDaListaPorTelefone && alvo != null) {
            listaPorNome.remover(alvo);
            System.out.println("Contato removido com sucesso.");
        } else {
            System.out.println("Contato não existia.");
        }
        System.out.printf("Tempo de remoção: %.3f ms%n", (fim - inicio) / 1_000_000.0);
    }

    // Altera nome e/ou telefone de um contato já cadastrado.
    private void alterarContato() {
        System.out.print("Nome atual do contato: ");
        String nomeAtual = scanner.nextLine().trim();

        Contato encontrado = listaPorNome.pesquisar(new Contato(nomeAtual, ""));
        if (encontrado == null) {
            System.out.println("Contato não encontrado.");
            return;
        }

        System.out.println("Telefone atual: " + encontrado.getTelefone());

        System.out.print("Novo nome: ");
        String novoNome = scanner.nextLine().trim();
        System.out.print("Novo telefone: ");
        String novoTelefone = scanner.nextLine().trim();

        // Só bloqueia se o novo telefone já pertencer a outro contato;
        // manter o mesmo telefone de antes é permitido.
        if (!novoTelefone.equals(encontrado.getTelefone())
                && listaPorTelefone.pesquisar(new Contato("", novoTelefone)) != null) {
            System.out.println("Já existe outro contato com esse telefone. Alteração cancelada.");
            return;
        }

        // Removemos o contato das duas listas, atualizamos os dados e
        // inserimos de novo, em vez de apenas sobrescrever os campos.
        // Isso evita que, numa lista ordenada, o contato fique "preso" na
        // posição antiga depois que seu nome ou telefone muda.
        listaPorNome.remover(encontrado);
        listaPorTelefone.remover(encontrado);

        encontrado.setNome(novoNome);
        encontrado.setTelefone(novoTelefone);

        listaPorNome.adicionar(encontrado);
        listaPorTelefone.adicionar(encontrado);

        System.out.println("Contato atualizado com sucesso.");
    }

    // Exibe a quantidade total de contatos antes de encerrar. Usamos a
    // listaPorTelefone como referência porque ela nunca tem telefones
    // duplicados, então sua contagem reflete o total real de contatos.
    private void sair() {
        System.out.println("Quantidade total de contatos: " + listaPorTelefone.quantidadeNos());
        System.out.println("Encerrando programa...");
    }
}
