package cadastro.model;

import cadastro.util.ConectorBD;
import cadastro.util.SequenceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDAO {
    private final ConectorBD conectorBD = new ConectorBD();
    private final SequenceManager sequenceManager = new SequenceManager();

    // obter uma pessoa juridica através do id (findById(int id))
    public PessoaJuridica getPessoa(int id) {
        String sql = "SELECT * FROM Pessoa p JOIN PessoaJuridica pj ON p.id = pj.id WHERE p.id = ?";
        PessoaJuridica pessoaJuridica = null;

        try (Connection connection = conectorBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                pessoaJuridica = new PessoaJuridica(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("logradouro"),
                        resultSet.getString("cidade"),
                        resultSet.getString("estado"),
                        resultSet.getString("telefone"),
                        resultSet.getString("email"),
                        resultSet.getString("cnpj")
                );
            }
            conectorBD.close(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pessoaJuridica;
    }

    // obter todas as pessoas juridicas (findAll())
    public List<PessoaJuridica> getPessoas() {
        String sql = "SELECT * FROM Pessoa p JOIN PessoaJuridica pj ON p.id = pj.id";
        List<PessoaJuridica> pessoasJuridicas = new ArrayList<>();

        try (Connection connection = conectorBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                PessoaJuridica pessoaJuridica = new PessoaJuridica(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("logradouro"),
                        resultSet.getString("cidade"),
                        resultSet.getString("estado"),
                        resultSet.getString("telefone"),
                        resultSet.getString("email"),
                        resultSet.getString("cnpj")
                );
                pessoasJuridicas.add(pessoaJuridica);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pessoasJuridicas;
    }

    // adicionar no banco
    public void incluir(PessoaJuridica pessoaJuridica) {
        String sqlPessoa = "INSERT INTO Pessoa (id, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlPessoaJuridica = "INSERT INTO PessoaJuridica (id, cnpj) VALUES (?, ?)";

        int id = sequenceManager.getValue("PessoaSequence");

        try (Connection connection = conectorBD.getConnection();
             PreparedStatement preparedStatementPessoa = connection.prepareStatement(sqlPessoa);
             PreparedStatement preparedStatementPessoaJuridica = connection.prepareStatement(sqlPessoaJuridica)) {

            // insere primeiro em pessoa
            preparedStatementPessoa.setInt(1, id);
            preparedStatementPessoa.setString(2, pessoaJuridica.getNome());
            preparedStatementPessoa.setString(3, pessoaJuridica.getLogradouro());
            preparedStatementPessoa.setString(4, pessoaJuridica.getCidade());
            preparedStatementPessoa.setString(5, pessoaJuridica.getEstado());
            preparedStatementPessoa.setString(6, pessoaJuridica.getTelefone());
            preparedStatementPessoa.setString(7, pessoaJuridica.getEmail());
            preparedStatementPessoa.executeUpdate();

            //insere em pessoa jurídica
            preparedStatementPessoaJuridica.setInt(1, id);
            preparedStatementPessoaJuridica.setString(2, pessoaJuridica.getCnpj());
            preparedStatementPessoaJuridica.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // alterar um registro
    public void alterar(PessoaJuridica pessoaJuridica) {
        String sqlPessoa = "UPDATE Pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE id = ?";
        String sqlPessoaJuridica = "UPDATE PessoaJuridica SET cnpj = ? WHERE id = ?";

        try (Connection connection = conectorBD.getConnection();
             PreparedStatement preparedStatementPessoa = connection.prepareStatement(sqlPessoa);
             PreparedStatement preparedStatementPessoaJuridica = connection.prepareStatement(sqlPessoaJuridica)) {

            // altera primeiro na tabela pessoa
            preparedStatementPessoa.setString(1, pessoaJuridica.getNome());
            preparedStatementPessoa.setString(2, pessoaJuridica.getLogradouro());
            preparedStatementPessoa.setString(3, pessoaJuridica.getCidade());
            preparedStatementPessoa.setString(4, pessoaJuridica.getEstado());
            preparedStatementPessoa.setString(5, pessoaJuridica.getTelefone());
            preparedStatementPessoa.setString(6, pessoaJuridica.getEmail());
            preparedStatementPessoa.setInt(7, pessoaJuridica.getId());
            preparedStatementPessoa.executeUpdate();

            // altera na tabela pessoa júridica
            preparedStatementPessoaJuridica.setString(1, pessoaJuridica.getCnpj());
            preparedStatementPessoaJuridica.setInt(2, pessoaJuridica.getId());
            preparedStatementPessoaJuridica.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // exclusão
    public void excluir(int id) {
        String sqlPessoaJuridica = "DELETE FROM PessoaJuridica WHERE id = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE id = ?";

        try (Connection connection = conectorBD.getConnection();
             PreparedStatement preparedStatementPessoaJuridica = connection.prepareStatement(sqlPessoaJuridica);
             PreparedStatement preparedStatementPessoa = connection.prepareStatement(sqlPessoa)) {

            // exclusão inicial na tabela filho (pessoa jurídica)
            preparedStatementPessoaJuridica.setInt(1, id);
            preparedStatementPessoaJuridica.executeUpdate();

           // exclusão na tabela pai (pessoa)
            preparedStatementPessoa.setInt(1, id);
            preparedStatementPessoa.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
