package cadastrobd;

import cadastro.model.PessoaFisica;
import cadastro.model.PessoaFisicaDAO;
import cadastro.model.PessoaJuridica;
import cadastro.model.PessoaJuridicaDAO;

import java.util.List;
import java.util.Scanner;

public class SistemaOpcoes {
    private final PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
    private final PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();

    public void incluirPessoa(Scanner scanner) {
        System.out.print("Incluir Pessoa Física ou Jurídica (f/j)? ");
        String tipo = scanner.nextLine().toLowerCase();

        if (tipo.equals("f")) {
            PessoaFisica pessoaFisica = criarPessoaFisica(scanner);
            pessoaFisicaDAO.incluir(pessoaFisica);
            System.out.println("Pessoa Física incluída com sucesso.");
        } else if (tipo.equals("j")) {
            PessoaJuridica pessoaJuridica = criarPessoaJuridica(scanner);
            pessoaJuridicaDAO.incluir(pessoaJuridica);
            System.out.println("Pessoa Jurídica incluída com sucesso.");
        } else {
            System.out.println("Tipo inválido.");
        }
    }

    public void alterarPessoa(Scanner scanner) {
        System.out.print("Alterar Pessoa Física ou Jurídica (f/j)? ");
        String tipo = scanner.nextLine().toLowerCase();

        if (tipo.equals("f")) {
            System.out.print("Digite o ID da Pessoa Física a alterar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consumir a quebra de linha

            PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id);
            if (pessoaFisica == null) {
                System.out.println("Pessoa Física não encontrada.");
                return;
            }

            System.out.println("Dados atuais: ");
            pessoaFisica.exibir();

            PessoaFisica novaPessoaFisica = criarPessoaFisica(scanner);
            novaPessoaFisica.setId(id); // manter o mesmo ID
            pessoaFisicaDAO.alterar(novaPessoaFisica);
            System.out.println("Pessoa Física alterada com sucesso.");
        } else if (tipo.equals("j")) {
            System.out.print("Digite o ID da Pessoa Jurídica a alterar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consumir a quebra de linha

            PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);
            if (pessoaJuridica == null) {
                System.out.println("Pessoa Jurídica não encontrada.");
                return;
            }

            System.out.println("Dados atuais: ");
            pessoaJuridica.exibir();

            PessoaJuridica novaPessoaJuridica = criarPessoaJuridica(scanner);
            novaPessoaJuridica.setId(id); // manter o mesmo ID
            pessoaJuridicaDAO.alterar(novaPessoaJuridica);
            System.out.println("Pessoa Jurídica alterada com sucesso.");
        } else {
            System.out.println("Tipo inválido.");
        }
    }

    public void excluirPessoa(Scanner scanner) {
        System.out.print("Excluir Pessoa Física ou Jurídica (f/j)? ");
        String tipo = scanner.nextLine().toLowerCase();

        if (tipo.equals("f")) {
            System.out.print("Digite o ID da Pessoa Física a excluir: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consumir a quebra de linha

            pessoaFisicaDAO.excluir(id);
            System.out.println("Pessoa Física excluída com sucesso.");
        } else if (tipo.equals("j")) {
            System.out.print("Digite o ID da Pessoa Jurídica a excluir: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consumir a quebra de linha

            pessoaJuridicaDAO.excluir(id);
            System.out.println("Pessoa Jurídica excluída com sucesso.");
        } else {
            System.out.println("Tipo inválido.");
        }
    }

    public void exibirPessoaPorId(Scanner scanner) {
        System.out.print("Exibir Pessoa Física ou Jurídica (f/j)? ");
        String tipo = scanner.nextLine().toLowerCase();

        if (tipo.equals("f")) {
            System.out.print("Digite o ID da Pessoa Física: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consumir a quebra de linha

            PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id);
            if (pessoaFisica == null) {
                System.out.println("Pessoa Física não encontrada.");
            } else {
                pessoaFisica.exibir();
            }
        } else if (tipo.equals("j")) {
            System.out.print("Digite o ID da Pessoa Jurídica: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consumir a quebra de linha

            PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);
            if (pessoaJuridica == null) {
                System.out.println("Pessoa Jurídica não encontrada.");
            } else {
                pessoaJuridica.exibir();
            }
        } else {
            System.out.println("Tipo inválido.");
        }
    }

    public void exibirTodasPessoas(Scanner scanner) {
        System.out.print("Exibir todas as Pessoas Físicas ou Jurídicas (f/j)? ");
        String tipo = scanner.nextLine().toLowerCase();

        if (tipo.equals("f")) {
            List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
            if (pessoasFisicas.isEmpty()) {
                System.out.println("Nenhuma Pessoa Física encontrada.");
            } else {
                for (PessoaFisica pessoa : pessoasFisicas) {
                    pessoa.exibir();
                }
            }
        } else if (tipo.equals("j")) {
            List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoas();
            if (pessoasJuridicas.isEmpty()) {
                System.out.println("Nenhuma Pessoa Jurídica encontrada.");
            } else {
                for (PessoaJuridica pessoa : pessoasJuridicas) {
                    pessoa.exibir();
                }
            }
        } else {
            System.out.println("Tipo inválido.");
        }
    }

    private PessoaFisica criarPessoaFisica(Scanner scanner) {
        System.out.println("Digite os dados da Pessoa Física:");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Logradouro: ");
        String logradouro = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        return new PessoaFisica(0, nome, logradouro, cidade, estado, telefone, email, cpf);
    }

    private PessoaJuridica criarPessoaJuridica(Scanner scanner) {
        System.out.println("Digite os dados da Pessoa Jurídica:");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Logradouro: ");
        String logradouro = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();

        return new PessoaJuridica(0, nome, logradouro, cidade, estado, telefone, email, cnpj);
    }
}
