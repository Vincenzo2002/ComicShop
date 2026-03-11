<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 08/07/24
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Login/Registrazione</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/register-login.css">
</head>
<body>
<%@ include file="../components/navbar.jsp" %>
<div class="container">

    <%-- Visualizza messaggio di errore se presente --%>
    <%
        String errorMsg = (String) request.getAttribute("errorMsg");
        if (errorMsg != null && !errorMsg.isEmpty()) {
    %>
    <div class="error-message">
        <h2>Error:</h2>
        <p><%= errorMsg %></p>
    </div>
    <%
        }
    %>

    <div class="form-container">
        <!--Login Form-->
        <h2>Login</h2>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="login-email">Email</label>
                <input type="email" id="login-email" name="loginEmail" placeholder="La tua email" required>
            </div>
            <div class="form-group">
                <label for="login-password">Password</label>
                <input type="password" id="login-password" name="loginPassword" placeholder="La tua password" required>
            </div>
            <div class="form-group">
                <button type="submit">Accedi</button>
            </div>
        </form>
    </div>

    <div class="form-container">
        <!--Registrazione form-->
        <h2>Registrazione</h2>
        <form action="${pageContext.request.contextPath}/signup" method="post" onsubmit="return validateForm()">
            <div class="form-group">
                <label for="fname">Nome</label>
                <input type="text" id="fname" name="fname" placeholder="Il tuo nome" required>
            </div>
            <div class="form-group">
                <label for="lname">Cognome</label>
                <input type="text" id="lname" name="lname" placeholder="Il tuo cognome" required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="La tua email" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="La tua password" required>
                <div id="password-error" style="color: red;"></div>
            </div>
            <div class="form-group">
                <label for="provincia">Provincia</label>
                <input type="text" id="provincia" name="provincia" placeholder="La tua provincia" >
            </div>
            <div class="form-group">
                <label for="via">Via</label>
                <input type="text" id="via" name="via" placeholder="La tua via" required>
            </div>
            <div class="form-group">
                <label for="civico">Numero Civico</label>
                <input type="text" id="civico" name="civico" placeholder="Il tuo numero civico" required pattern="\d+" inputmode="numeric">
            </div>
            <div class="form-group">
                <label for="cap">CAP</label>
                <input type="text" id="cap" name="cap" placeholder="Il tuo CAP" required pattern="\d{5}">
            </div>
            <div class="form-group">
                <label for="citta">Città</label>
                <input type="text" id="citta" name="citta" placeholder="La tua città" required>
            </div>
            <div class="form-group">
                <button type="submit">Registrati</button>
            </div>
        </form>
    </div>
</div>

<script>
    function showError(element, message) {
        element.textContent = message;
        element.style.color = 'red';
    }

    function validateForm() {
        const password = document.getElementById('password').value;
        const passwordError = document.getElementById('password-error');

        // Verifica che la password abbia almeno 8 caratteri
        if (password.length < 8) {
            showError(passwordError, 'La password deve contenere almeno 8 caratteri.');
            return false;
        }

        // Verifica che la password contenga almeno una lettera maiuscola
        if (!/[A-Z]/.test(password)) {
            showError(passwordError, 'La password deve contenere almeno una lettera maiuscola.');
            return false;
        }

        // Verifica che la password contenga almeno un numero
        if (!/\d/.test(password)) {
            showError(passwordError, 'La password deve contenere almeno un numero.');
            return false;
        }

        // Se tutti i controlli sono superati, resetta il messaggio di errore
        showError(passwordError, '');

        return true;
    }
</script>

<%@ include file="../components/footer.jsp" %>
</body>
</html>
