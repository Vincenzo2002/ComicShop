<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 09/07/24
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registrazione Effettuata</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/post-signup.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<div class="main">
    <h1>Ti sei registrato correttamente!</h1>
    <h3>Tra 3 secondi sarai reindirizzato al login...</h3>
    <i class="fas fa-5x fa-circle-notch fa-spin"></i>
</div>
<script>
    setTimeout(() => {
        window.location.replace("${pageContext.request.contextPath}/pages/register-login.jsp");
    }, 3000);
</script>
</body>
</html>
