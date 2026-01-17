<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Book List - Bibliotech</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="header">
        <div class="container">
            <h1>📚 Bibliotech</h1>
            <p class="subtitle">Library Management System</p>
        </div>
    </div>

    <div class="container">
        <nav class="nav">
            <ul class="nav-list">
                <li><a href="${pageContext.request.contextPath}/" class="nav-link">🏠 Home</a></li>
                <li><a href="new" class="nav-link">➕ Add New Book</a></li>
            </ul>
        </nav>

        <h2>Book Collection</h2>
        
        <!-- Search Form -->
        <form action="search" method="get" class="search-form">
            <input type="text" name="keyword" 
                   placeholder="Search by title, author, ISBN, or genre..." 
                   value="${searchKeyword}" 
                   class="search-input">
            <button type="submit" class="btn">🔍 Search</button>
            <a href="list" class="btn btn-secondary">Clear</a>
        </form>

        <c:if test="${not empty searchKeyword}">
            <p>Search results for: <strong>${searchKeyword}</strong></p>
        </c:if>

        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>ISBN</th>
                        <th>Genre</th>
                        <th>Available</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="book" items="${books}">
                        <tr>
                            <td>${book.id}</td>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <td>${book.isbn}</td>
                            <td>${book.genre}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${book.copiesAvailable > 3}">
                                        <span class="status-available">${book.copiesAvailable}/${book.totalCopies}</span>
                                    </c:when>
                                    <c:when test="${book.copiesAvailable > 0}">
                                        <span class="status-low">${book.copiesAvailable}/${book.totalCopies}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status-unavailable">0/${book.totalCopies}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="action-buttons">
                                <a href="edit?id=${book.id}" class="btn btn-secondary">Edit</a>
                                <a href="delete?id=${book.id}" 
                                   class="btn btn-danger" 
                                   onclick="return confirm('Are you sure you want to delete this book?')">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    
                    <c:if test="${empty books}">
                        <tr>
                            <td colspan="7" style="text-align: center; padding: 2rem;">
                                No books found. <a href="new">Add your first book!</a>
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
        
        <div class="footer">
            <p>Total Books: ${books.size()}</p>
        </div>
    </div>
</body>
</html>