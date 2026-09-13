# Trabalhos-TPA


# Trabalho 1 - Análise de Complexidade em Estruturas de Listas

## Descrição

Implementação de uma biblioteca de lista encadeada genérica em Java e um 
programa de gerenciamento de contatos que usa essa biblioteca. O trabalho 
faz parte da disciplina de **Técnicas de Programação Avançada** e envolve análise matemática 
e empírica de complexidade dos algoritmos.

## Integrantes do Grupo

- Apoema
- Heloísa Hand
- Gabriela Demezio
  
## Estrutura do Projeto
- src/main/java/
├── lista/ # A biblioteca de lista encadeada
│ ├── IColecao.java # Interface da coleção
│ ├── No.java # Classe que representa um nó
│ └── ListaEncadeada.java # Implementação da lista
└── app/ # Programa de teste
├── Contato.java # Classe de domínio (nome e telefone)
└── progTeste.java # Programa principal com menu

entrada.txt # Arquivo de dados de exemplo
README.md # Este arquivo

## Arquivo de entrada
- extensão "txt".
- Nome,telefone.
- Exemplo: Ana Silva,12344555



## Como Compilar e Executar

### Pré-requisitos
- Java JDK 8 ou superior

### Compilação
No terminal, dentro da pasta raiz do projeto:

```bash
javac -d . src/main/java/lista/*.java src/main/java/app/*.java

**### Execução**

java app.progPrincipal

**Funcionalidades do Programa**
Ao iniciar, o programa pergunta se você deseja uma lista ordenada (S)
ou não ordenada (N). Em seguida, exibe um menu com as opções:

Carregar dados de arquivo: Lê o arquivo entrada.txt e insere os contatos

Adicionar contato: Solicita nome e telefone de um novo contato

Pesquisar contato por nome: Busca e exibe o telefone

Pesquisar contato por telefone: Busca e exibe o nome

Remover contato por telefone: Remove um contato

Alterar dados de contato: Altera nome e telefone

Sair: Exibe a quantidade total e encerra





