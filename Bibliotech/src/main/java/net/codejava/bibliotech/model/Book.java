package net.codejava.bibliotech.model;  // IMPORTANT: This package declaration

import java.util.Date;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private int publishYear;
    private String genre;
    private int copiesAvailable;
    private int totalCopies;
    private Date createdAt;
    
    // Constructors
    public Book() {}
    
    public Book(String title, String author, String isbn, String publisher, 
                int publishYear, String genre, int copiesAvailable, int totalCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.genre = genre;
        this.copiesAvailable = copiesAvailable;
        this.totalCopies = totalCopies;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    
    public int getPublishYear() { return publishYear; }
    public void setPublishYear(int publishYear) { this.publishYear = publishYear; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    public int getCopiesAvailable() { return copiesAvailable; }
    public void setCopiesAvailable(int copiesAvailable) { this.copiesAvailable = copiesAvailable; }
    
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}