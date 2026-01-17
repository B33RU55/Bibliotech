package net.codejava.bibliotech.dao;

import net.codejava.bibliotech.db.DBConnection;
import net.codejava.bibliotech.model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    
    // Create (Insert)
    public boolean addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author, isbn, publisher, publish_year, genre, copies_available, total_copies) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getPublisher());
            stmt.setInt(5, book.getPublishYear());
            stmt.setString(6, book.getGenre());
            stmt.setInt(7, book.getCopiesAvailable());
            stmt.setInt(8, book.getTotalCopies());
            
            int rowsAffected = stmt.executeUpdate();
            
            // Get the generated ID
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        book.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        }
    }
    
    // Read (Get All)
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY title";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        }
        return books;
    }
    
    // Read (Get Single)
    public Book getBookById(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractBookFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    // Update
    public boolean updateBook(Book book) throws SQLException {
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, publisher = ?, publish_year = ?, genre = ?, copies_available = ?, total_copies = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getPublisher());
            stmt.setInt(5, book.getPublishYear());
            stmt.setString(6, book.getGenre());
            stmt.setInt(7, book.getCopiesAvailable());
            stmt.setInt(8, book.getTotalCopies());
            stmt.setInt(9, book.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    // Delete
    public boolean deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    // Search Books
    public List<Book> searchBooks(String keyword) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR isbn LIKE ? OR genre LIKE ? ORDER BY title";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(extractBookFromResultSet(rs));
                }
            }
        }
        return books;
    }
    
    // Helper method to extract book from ResultSet
    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setIsbn(rs.getString("isbn"));
        book.setPublisher(rs.getString("publisher"));
        book.setPublishYear(rs.getInt("publish_year"));
        book.setGenre(rs.getString("genre"));
        book.setCopiesAvailable(rs.getInt("copies_available"));
        book.setTotalCopies(rs.getInt("total_copies"));
        book.setCreatedAt(rs.getTimestamp("created_at"));
        return book;
    }
}