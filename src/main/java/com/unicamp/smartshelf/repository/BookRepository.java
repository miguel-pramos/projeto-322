package com.unicamp.smartshelf.repository;

import com.unicamp.smartshelf.database.DatabaseManager;
import com.unicamp.smartshelf.model.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepository {

    private final DatabaseManager databaseManager;

    public BookRepository() {
        this.databaseManager = DatabaseManager.getInstance();
    }

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book ORDER BY id";

        try (Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                books.add(mapResultSetToBook(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os livros", e);
        }

        return books;
    }

    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";

        try (Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToBook(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livro por ID", e);
        }

        return Optional.empty();
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            return insert(book);
        } else {
            return update(book);
        }
    }

    private Book insert(Book book) {
        String sql = "INSERT INTO book (description, read) VALUES (?, ?)";

        try (Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getDescription());
            statement.setBoolean(2, book.isRead());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao inserir livro, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Falha ao inserir livro, ID não foi gerado.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir livro", e);
        }

        return book;
    }

    private Book update(Book book) {
        String sql = "UPDATE book SET description = ?, read = ? WHERE id = ?";

        try (Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getDescription());
            statement.setBoolean(2, book.isRead());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao atualizar livro, nenhuma linha afetada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar livro", e);
        }

        return book;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";

        try (Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao deletar livro, nenhuma linha afetada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar livro", e);
        }
    }

    public List<Book> findByRead(boolean read) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE read = ? ORDER BY id";

        try (Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, read);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    books.add(mapResultSetToBook(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livros por status de leitura", e);
        }

        return books;
    }

    public List<Book> findByDescriptionContainingIgnoreCase(String description) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE LOWER(description) LIKE LOWER(?) ORDER BY id";

        try (Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + description + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    books.add(mapResultSetToBook(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livros por descrição", e);
        }

        return books;
    }

    public List<Book> findByReadAndDescriptionContaining(boolean read, String description) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE read = ? AND LOWER(description) LIKE LOWER(?) ORDER BY id";

        try (Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, read);
            statement.setString(2, "%" + description + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    books.add(mapResultSetToBook(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livros por status e descrição", e);
        }

        return books;
    }

    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setDescription(resultSet.getString("description"));
        book.setRead(resultSet.getBoolean("read"));
        return book;
    }
}
