<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 06/07/24
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="model.bean.User" %>

<%
    User user = (User) session.getAttribute("user");//Recupero user dalla sessione
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<div class="header">
    <div class="logo">
        <a href="${pageContext.request.contextPath}/pages/index.jsp">
            <img src="${pageContext.request.contextPath}/assets/img/logo/logo.png" alt="Logo" style="width: 100px; height: auto;">
        </a>
    </div>

    <div class="navBar-button">
        <% if (user != null) { %>
        <a href="${pageContext.request.contextPath}/pages/profile.jsp">
                <% } else { %>
            <a href="${pageContext.request.contextPath}/pages/register-login.jsp">
                <% } %>
                <i class="fas fa-user icon"></i>
                Account
            </a>
            <a href="${pageContext.request.contextPath}/pages/carrello.jsp">
                <i class="fas fa-shopping-cart icon"></i>
                Carrello
            </a>
                <% if (user != null && user.isAdmin()) { %>
            <a href="${pageContext.request.contextPath}/pages/admin/index.jsp">
                <i class="fas fa-user-shield icon"></i>
                Amministratore
            </a>
                <% } %>
    </div>
</div>

</body>
</html>





