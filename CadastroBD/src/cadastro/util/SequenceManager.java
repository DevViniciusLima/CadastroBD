package cadastro.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceManager {
    ConectorBD conectorBD = new ConectorBD();

    //obter o próximo valor de uma sequência
    public int getValue(String sequenceName) {
        int nextValue = 0;
        String sql = "SELECT NEXT VALUE FOR " + sequenceName;

        try (Connection connection = conectorBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            if (resultSet.next()) {
                nextValue = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextValue;
    }
}

