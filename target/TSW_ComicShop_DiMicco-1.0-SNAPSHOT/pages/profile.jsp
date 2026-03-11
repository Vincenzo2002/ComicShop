<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 09/07/24
  Time: 18:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profilo</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/profile.css">
</head>
<body>
<%@ include file="../components/navbar.jsp" %>

<div class="container">
    <!-- Sidebar Menu -->
    <div class="sidebar">
        <ul>
            <li><a href="profile.jsp">Profilo</a></li>
            <li><a href="list-orders.jsp">Ordini</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Esci</a></li>
        </ul>
    </div>

    <div class="main-container">
        <div class="container-header">
            <h1 class="container-title">Profilo</h1>
        </div>
        <div class="info-container">
            <div class="two-fields-row">
                <div class="field">
                    <h3 class="bold">Nome:</h3>
                    &nbsp;
                    <h3 class="field-value"><%= user.getFirstName() %></h3>
                </div>
                <div class="field">
                    <h3 class="bold">Cognome:</h3>
                    &nbsp;
                    <h3 class="field-value"><%= user.getLastName() %></h3>
                </div>
            </div>
            <div class="field">
                <h3 class="bold">Email:</h3>
                &nbsp;
                <h3 class="field-value"><%= user.getEmail() %></h3>
            </div>
            <div class="two-fields-row">
                <div class="field">
                    <h3 class="bold">Provincia:</h3>
                    &nbsp;
                    <h3 class="field-value"><%= user.getProvincia() %></h3>
                </div>
                <div class="field">
                    <h3 class="bold">Citt&agrave;:</h3>
                    &nbsp;
                    <h3 class="field-value"><%= user.getCitta() %></h3>
                </div>
            </div>
            <div class="field">
                <h3 class="bold">Indirizzo:</h3>
                &nbsp;
                <h3 class="field-value">
                    <%= user.getVia() + ", " + user.getCivico() %>
                </h3>
            </div>
            <div class="field">
                <h3 class="bold">CAP:</h3>
                &nbsp;
                <h3 class="field-value"><%= user.getCap() %></h3>
            </div>
        </div>
    </div>
</div>

<%@ include file="../components/footer.jsp" %>
</body>
</html>