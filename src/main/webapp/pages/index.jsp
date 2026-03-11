<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Store Homepage</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css">
</head>
<body>
<%@ include file="../components/navbar.jsp" %>

<div class="banner">
    <img src="${pageContext.request.contextPath}/assets/img/banner.jpg" alt="Banner Image">
</div>

<div class="main-content">
    <a href="${pageContext.request.contextPath}/category?category=Fumetti&skip=0&limit=9">
        <div class="section">
            <img src="${pageContext.request.contextPath}/assets/img/fumetti.jpg" alt="Fumetti">
            <div class="overlay-text">FUMETTI</div>
        </div>
    </a>
    <a href="${pageContext.request.contextPath}/category?category=Manga&skip=0&limit=9">
        <div class="section">
            <img src="${pageContext.request.contextPath}/assets/img/manga.jpg" alt="Manga">
            <div class="overlay-text">MANGA</div>
        </div>
    </a>
    <a href="${pageContext.request.contextPath}/category?category=Gadget&skip=0&limit=9">
        <div class="section">
            <img src="${pageContext.request.contextPath}/assets/img/gadget.jpg" alt="Gadget">
            <div class="overlay-text">GADGET</div>
        </div>
    </a>
</div>

<%@ include file="../components/footer.jsp" %>
</body>
</html>



