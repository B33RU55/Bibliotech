package net.codejava.bibliotech.controller;

import net.codejava.bibliotech.dao.BookDAO;
import net.codejava.bibliotech.model.Book;
CREATE TABLE book (
    book_id int(11) NOT NULL AUTO_INCREMENT,
    title varchar(128) NOT NULL,
    author varchar(45) NOT NULL,
    price float NOT NULL,
    PRIMARY KEY (book_id)
);
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "BookServlet", urlPatterns = {"/", "/list", "/new", "/insert", "/edit", "/update", "/delete", "/search"})
public class BookServlet extends HttpServlet {
    private BookDAO bookDAO;
    
    @Override
    public void init() {
        bookDAO = new BookDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getServletPath();
        
        try {
            if (action == null || action.equals("/") || action.equals("/list")) {
                listBooks(request, response);
            } else if (action.equals("/new")) {
                showNewForm(request, response);
            } else if (action.equals("/insert")) {
                insertBook(request, response);
            } else if (action.equals("/delete")) {
                deleteBook(request, response);
            } else if (action.equals("/edit")) {
                showEditForm(request, response);
            } else if (action.equals("/update")) {
                updateBook(request, response);
            } else if (action.equals("/search")) {
                searchBooks(request, response);
            } else {
                listBooks(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    private void listBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Book> books = bookDAO.getAllBooks();
        request.setAttribute("books", books);
        RequestDispatcher dispatcher = request.getRequestDispatcher("book-list.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("book-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book existingBook = bookDAO.getBookById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("book-form.jsp");
        request.setAttribute("book", existingBook);
        dispatcher.forward(request, response);
    }
    
    private void insertBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        Book book = extractBookFromRequest(request);
        bookDAO.addBook(book);
        response.sendRedirect("list");
    }
    
    private void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book book = extractBookFromRequest(request);
        book.setId(id);
        bookDAO.updateBook(book);
        response.sendRedirect("list");
    }
    
    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        bookDAO.deleteBook(id);
        response.sendRedirect("list");
    }
    
    private void searchBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String keyword = request.getParameter("keyword");
        List<Book> books = bookDAO.searchBooks(keyword);
        request.setAttribute("books", books);
        request.setAttribute("searchKeyword", keyword);
        RequestDispatcher dispatcher = request.getRequestDispatcher("book-list.jsp");
        dispatcher.forward(request, response);
    }
    
    private Book extractBookFromRequest(HttpServletRequest request) {
        Book book = new Book();
        book.setTitle(request.getParameter("title"));
        book.setAuthor(request.getParameter("author"));
        book.setIsbn(request.getParameter("isbn"));
        book.setPublisher(request.getParameter("publisher"));
        
        try {
            book.setPublishYear(Integer.parseInt(request.getParameter("publishYear")));
        } catch (NumberFormatException e) {
            book.setPublishYear(0);
        }
        
        book.setGenre(request.getParameter("genre"));
        
        try {
            book.setCopiesAvailable(Integer.parseInt(request.getParameter("copiesAvailable")));
        } catch (NumberFormatException e) {
            book.setCopiesAvailable(1);
        }
        
        try {
            book.setTotalCopies(Integer.parseInt(request.getParameter("totalCopies")));
        } catch (NumberFormatException e) {
            book.setTotalCopies(1);
        }
        
        return book;
    }
}