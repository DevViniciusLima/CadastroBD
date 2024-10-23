package cadastrobd;

import java.util.Scanner;

public class CadastroBD {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SistemaOpcoes operacoes = new SistemaOpcoes();

    public static void main(String[] args) {
        try (scanner) {
            int opcao;
            
            do {
                exibirMenu();
                opcao = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcao) {
                    case 1 -> operacoes.incluirPessoa(scanner);
                    case 2 -> operacoes.alterarPessoa(scanner);
                    case 3 -> operacoes.excluirPessoa(scanner);
                    case 4 -> operacoes.exibirPessoaPorId(scanner);
                    case 5 -> operacoes.exibirTodasPessoas(scanner);
                    case 0 -> System.out.println("Finalizando o sistema...");
                    default -> System.out.println("Opção inválida, tente novamente.");
                }
                
            } while (opcao != 0);
        }
    }

    private static void exibirMenu() {
        System.out.println("\n----- Menu de Cadastro -----");
        System.out.println("1. Incluir Pessoa");
        System.out.println("2. Alterar Pessoa");
        System.out.println("3. Excluir Pessoa");
        System.out.println("4. Exibir Pessoa por ID");
        System.out.println("5. Exibir Todas as Pessoas");
        System.out.println("0. Sair");
        System.out.print("Selecione uma opção: ");
    }
}
