package testes;

import cadastro.model.PessoaFisica;
import cadastro.model.PessoaFisicaDAO;
import cadastro.model.PessoaJuridica;
import cadastro.model.PessoaJuridicaDAO;

import java.util.List;

public class CadastroBDTeste {
    public static void main(String[] args) {
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();

        // 1° instanciar uma pessoa física e persistir no banco de dados
        PessoaFisica pessoaFisica = new PessoaFisica(0, "João Silva", "Rua 1", "Cidade A", "Estado X", "123456789", "joao@email.com", "123.456.789-00");
        pessoaFisicaDAO.incluir(pessoaFisica);
        System.out.println("Pessoa Física criada: " + pessoaFisica.getNome());

        // 2° alterar os dados da pessoa física no banco
        pessoaFisica.setNome("João Silva Alterado");
        pessoaFisica.setCidade("Cidade B");
        pessoaFisicaDAO.alterar(pessoaFisica);
        System.out.println("Pessoa Física alterada: " + pessoaFisica.getNome());

        // 3° consultar todas as pessoas físicas do banco de dados e listar 
        List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
        System.out.println("\nLista de Pessoas Físicas:");
        for (PessoaFisica pf : pessoasFisicas) {
            pf.exibir();
        }

        // 4° exclusão da pessoa física criada anteriormente 
        pessoaFisicaDAO.excluir(pessoaFisica.getId());
        System.out.println("\nPessoa Física excluída: " + pessoaFisica.getNome());

        // 5° instanciar uma pessoa jurídica e persistir no banco de dados
        PessoaJuridica pessoaJuridica = new PessoaJuridica(0, "Empresa XYZ", "Avenida Central", "Cidade C", "Estado Y", "987654321", "contato@empresa.com", "00.123.456/0001-00");
        pessoaJuridicaDAO.incluir(pessoaJuridica);
        System.out.println("\nPessoa Jurídica criada: " + pessoaJuridica.getNome());

        // 6° alterar os dados da pessoa jurídica no banco
        pessoaJuridica.setNome("Empresa XYZ Alterada");
        pessoaJuridica.setCidade("Cidade D");
        pessoaJuridicaDAO.alterar(pessoaJuridica);
        System.out.println("Pessoa Jurídica alterada: " + pessoaJuridica.getNome());

        // 7° Consultar todas as pessoas jurídicas do banco de dados e listar 
        List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoas();
        System.out.println("\nLista de Pessoas Jurídicas:");
        for (PessoaJuridica pj : pessoasJuridicas) {
            pj.exibir();
        }

        // 8° exclusão da pessoa jurídica criada anteriormente 
        pessoaJuridicaDAO.excluir(pessoaJuridica.getId());
        System.out.println("\nPessoa Jurídica excluída: " + pessoaJuridica.getNome());
    }
}