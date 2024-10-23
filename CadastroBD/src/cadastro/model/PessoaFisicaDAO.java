package cadastro.model;

import cadastro.util.ConectorBD;
import cadastro.util.SequenceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {
    private final ConectorBD conectorBD = new ConectorBD();
    private final SequenceManager sequenceManager = new SequenceManager();

    // obter uma pessoa física através do id (findById(int id))
    public PessoaFisica getPessoa(int id) {
        String sql = "SELECT * FROM Pessoa p JOIN PessoaFisica pf ON p.id = pf.id WHERE p.id = ?";
        PessoaFisica pessoaFisica = null;

        try (Connection connection = conectorBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                pessoaFisica = new PessoaFisica(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("logradouro"),
                        resultSet.getString("cidade"),
                        resultSet.getString("estado"),
                        resultSet.getString("telefone"),
                        resultSet.getString("email"),
                        resultSet.getString("cpf")
                );
            }
            conectorBD.close(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pessoaFisica;
    }

    // obter todas as pessoas físicas (findAll())
    public List<PessoaFisica> getPessoas() {
        String sql = "SELECT * FROM Pessoa p JOIN PessoaFisica pf ON p.id = pf.id";
        List<PessoaFisica> pessoasFisicas = new ArrayList<>();

        try (Connection connection = conectorBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                PessoaFisica pessoaFisica = new PessoaFisica(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("logradouro"),
                        resultSet.getString("cidade"),
                        resultSet.getString("estado"),
                        resultSet.getString("telefone"),
                        resultSet.getString("email"),
                        resultSet.getString("cpf")
                );
                pessoasFisicas.add(pessoaFisica);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pessoasFisicas;
    }

    // adicionar no banco
    public void incluir(PessoaFisica pessoaFisica) {
        String sqlPessoa = "INSERT INTO Pessoa (id, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlPessoaFisica = "INSERT INTO PessoaFisica (id, cpf) VALUES (?, ?)";

        int id = sequenceManager.getValue("PessoaSequence");

        try (Connection connection = conectorBD.getConnection();
             PreparedStatement preparedStatementPessoa = connection.prepareStatement(sqlPessoa);
             PreparedStatement preparedStatementPessoaFisica = connection.prepareStatement(sqlPessoaFisica)) {

            // insere primeiro em pessoa
            preparedStatementPessoa.setInt(1, id);
            preparedStatementPessoa.setString(2, pessoaFisica.getNome());
            preparedStatementPessoa.setString(3, pessoaFisica.getLogradouro());
            preparedStatementPessoa.setString(4, pessoaFisica.getCidade());
            preparedStatementPessoa.setString(5, pessoaFisica.getEstado());
            preparedStatementPessoa.setString(6, pessoaFisica.getTelefone());
            preparedStatementPessoa.setString(7, pessoaFisica.getEmail());
            preparedStatementPessoa.executeUpdate();
            
            //insere para pessoa física
            preparedStatementPessoaFisica.setInt(1, id);
            preparedStatementPessoaFisica.setString(2, pessoaFisica.getCpf());
            preparedStatementPessoaFisica.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // alterar um registro
    public void alterar(PessoaFisica pessoaFisica) {
        String sqlPessoa = "UPDATE Pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE id = ?";
        String sqlPessoaFisica = "UPDATE PessoaFisica SET cpf = ? WHERE id = ?";

        try (Connection connection = conectorBD.getConnection();
             PreparedStatement preparedStatementPessoa = connection.prepareStatement(sqlPessoa);
             PreparedStatement preparedStatementPessoaFisica = connection.prepareStatement(sqlPessoaFisica)) {

            // altera primeiro na table pessoa
            preparedStatementPessoa.setString(1, pessoaFisica.getNome());
            preparedStatementPessoa.setString(2, pessoaFisica.getLogradouro());
            preparedStatementPessoa.setString(3, pessoaFisica.getCidade());
            preparedStatementPessoa.setString(4, pessoaFisica.getEstado());
            preparedStatementPessoa.setString(5, pessoaFisica.getTelefone());
            preparedStatementPessoa.setString(6, pessoaFisica.getEmail());
            preparedStatementPessoa.setInt(7, pessoaFisica.getId());
            preparedStatementPessoa.executeUpdate();

            // altera na table pessoa física
            preparedStatementPessoaFisica.setString(1, pessoaFisica.getCpf());
            preparedStatementPessoaFisica.setInt(2, pessoaFisica.getId());
            preparedStatementPessoaFisica.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // exclusão
    public void excluir(int id) {
        String sqlPessoaFisica = "DELETE FROM PessoaFisica WHERE id = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE id = ?";

        try (Connection connection = conectorBD.getConnection();
             PreparedStatement preparedStatementPessoaFisica = connection.prepareStatement(sqlPessoaFisica);
             PreparedStatement preparedStatementPessoa = connection.prepareStatement(sqlPessoa)) {

            // exclusão inicial na tabela filho (pessoa fisica)
            preparedStatementPessoaFisica.setInt(1, id);
            preparedStatementPessoaFisica.executeUpdate();

            // exclusão na tabela pai (pessoa)
            preparedStatementPessoa.setInt(1, id);
            preparedStatementPessoa.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
