package com.example.fbtransactions;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionDao {
    private final Connection connection;


    public TransactionDao() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/family-budget?serverTimezone=UTC", "root", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void save(Transaction transaction) {
        final String sql = String.format("INSERT INTO transaction (type, description, amount, date) VALUES ('%s', '%s', '%.2f', '%tF')",
                transaction.getType().getDescription(), transaction.getDescription(), transaction.getAmount(), transaction.getDate());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                transaction.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean update(Transaction transaction) {
        final String sql = String.format("""
            UPDATE
                transaction
            SET
                type = '%s',
                description = '%s',
                amount = '%.2f',
                date = '%tF'
            WHERE
                id = %d
            """,
                transaction.getType().getDescription(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getId());
        try(Statement statement =connection.createStatement()) {
            int updateRows = statement.executeUpdate(sql);
            return updateRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean delete(int id) {
        final String sql = "DELETE FROM transaction WHERE id = " + id;
        try(Statement statement = connection.createStatement()){
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<Transaction> findByType(TransactionType searchType) {
        final String sql = "SELECT id, type, description, amount, date FROM transaction WHERE type = '" + searchType.getDescription() + "'";
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            List<Transaction> transactionList = new ArrayList<>();
            while (resultSet.next()) {
                String type = resultSet.getString("type");
                if (type.equals(searchType.getDescription()) ) {
                    int id = resultSet.getInt("id");
                    String description = resultSet.getString("description");
                    double amount = resultSet.getDouble("amount");
                    Date date = resultSet.getDate("date");
                    transactionList.add(new Transaction(id, searchType, description, amount, date));
                }
            }
            return transactionList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Transaction> findById(int id) {
        final String sql = "SELECT id, type, description, amount, date FROM transaction WHERE id = '" + id + "'";
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            List<Transaction> transactionList = new ArrayList<>();
            if (resultSet.next()) {
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                Date date = resultSet.getDate("date");
                TransactionType type = TransactionType.CHARGE;
                if (!type.getDescription().equals(resultSet.getString("type"))) {
                    type = TransactionType.CREDIT;
                }
                return Optional.of(new Transaction(id, type, description, amount, date));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
