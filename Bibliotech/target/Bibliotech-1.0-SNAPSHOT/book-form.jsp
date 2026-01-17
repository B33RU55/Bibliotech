<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        <c:if test="${book != null}">Edit Book</c:if>
        <c:if test="${book == null}">Add New Book</c:if>
        - Bibliotech
    </title>
    <link rel="stylesheet" href="css/style.css">
    <script>
        function validateForm() {
            const title = document.getElementById('title').value;
            const author = document.getElementById('author').value;
            const isbn = document.getElementById('isbn').value;
            const copiesAvailable = parseInt(document.getElementById('copiesAvailable').value);
            const totalCopies = parseInt(document.getElementById('totalCopies').value);
            
            if (!title || !author || !isbn) {
                alert('Please fill in all required fields');
                return false;
            }
            
            if (copiesAvailable > totalCopies) {
                alert('Available copies cannot be more than total copies');
                return false;
            }
            
            return true;
        }
    </script>
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
                <li><a href="list" class="nav-link">📖 View All Books</a></li>
            </ul>
        </nav>

        <h2>
            <c:if test="${book != null}">Edit Book</c:if>
            <c:if test="${book == null}">Add New Book</c:if>
        </h2>

        <div class="form-container">
            <c:if test="${book != null}">
                <form action="update" method="post" onsubmit="return validateForm()">
                    <input type="hidden" name="id" value="${book.id}">
            </c:if>
            <c:if test="${book == null}">
                <form action="insert" method="post" onsubmit="return validateForm()">
            </c:if>
            
                <div class="form-group">
                    <label for="title">Title *</label>
                    <input type="text" id="title" name="title" 
                           value="${book.title}" 
                           class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="author">Author *</label>
                    <input type="text" id="author" name="author" 
                           value="${book.author}" 
                           class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="isbn">ISBN *</label>
                    <input type="text" id="isbn" name="isbn" 
                           value="${book.isbn}" 
                          