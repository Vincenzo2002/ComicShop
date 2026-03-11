<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 12/07/24
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.Product" %>
<%@ page import="model.bean.Feedback" %>
<%@ page import="model.dao.FeedbackDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="model.dao.UserDAO" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/product.css">

    <% Product product = (Product) request.getAttribute("product"); %>
    <title>
        Prodotto<% if(product != null) { %> - <%= product.getName() %><% } %>
    </title>

    <script>
        function addToCart(productId, button) {

            // Aggiungi l'animazione al bottone cliccato
            button.classList.add('animate');

            // Rimuovi l'animazione dopo che è completata per poterla riutilizzare
            setTimeout(function() {
                button.classList.remove('animate');
            }, 500);

            var quantity = document.getElementById('quantity').value;
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "${pageContext.request.contextPath}/addtocart", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status !== 200) {
                        alert("Errore durante l'aggiunta al carrello: " + xhr.responseText + "\nStatus Code: " + xhr.status);
                    }
                }
            };
            xhr.send("pId=" + productId + "&q=" + quantity);
        }

        function showFeedbackForm() {
            document.getElementById('feedback-form').style.display = 'block';
        }
    </script>

</head>
<body>
<%@ include file="../components/navbar.jsp" %>
<% if (product != null) { %>
<div class="product-container">
    <img width="400" src="<%= product.getUrlImage().startsWith("http") ? product.getUrlImage() : request.getContextPath() + product.getUrlImage() %>" />
    <div class="product-info">
        <h3 class="product-title">
            <span class="product-name"><%= product.getName() %></span>
        </h3>
        <p class="paragraph bold price"><%= product.getPrice() %> &euro;</p>
        <p class="paragraph bold">
            <span class="brand-name">Descrizione</span>
        </p>
        <p class="paragraph"><%= product.getDescription() %></p>
        <p class="paragraph bold">
            <span class="brand-name">Aggiungi al carrello</span>
        </p>
        <div class="field">
            <input type="hidden" name="pId" value="<%= product.getIdProduct() %>"/>
            <input type="number" name="q" id="quantity" min="1" class="text-input" value="1" max="<%= product.getQuantity() %>"/>
            <button class="btn-accent nav-button addtocart" onclick="addToCart(<%= product.getIdProduct() %>, this)">
                <i class="fas fa-shopping-cart fa-lg"></i>
                <span class="paragraph bold">Aggiungi</span>
                <span class="animation-effect"></span>
            </button>
        </div>
    </div>
</div>

<div class="feedback-section">
    <h2>Feedback</h2>

    <%
        User currentUser = (User) session.getAttribute("user");
        if (currentUser != null) {
    %>
    <button onclick="showFeedbackForm()">Aggiungi un feedback</button>

    <div id="feedback-form" style="display: none;">
        <form action="${pageContext.request.contextPath}/addfeedback" method="post">
            <input type="hidden" name="productId" value="<%= product.getIdProduct() %>" />
            <label for="title">Titolo:</label>
            <input type="text" id="title" name="title" required>
            <label for="description">Descrizione:</label>
            <textarea id="description" name="description" required></textarea>
            <label for="score">Punteggio:</label>
            <input type="number" id="score" name="score" min="1" max="5" required>
            <input type="hidden" name="userId" value="<%= currentUser.getIdUser() %>">
            <button type="submit">Invia Feedback</button>
        </form>
    </div>
    <% } else { %>
    <p>Devi effetuare <a href="${pageContext.request.contextPath}/pages/register-login.jsp">login</a> per lasciare un feedback.</p>
    <% } %>

    <div class="feedback-list">
        <%
            FeedbackDAO feedbackDAO = new FeedbackDAO();
            List<Feedback> feedbacks = (List<Feedback>) feedbackDAO.doRetrieveByProduct(product.getIdProduct());
            if (feedbacks != null && !feedbacks.isEmpty()) {
                for (Feedback feedback : feedbacks) {
        %>
        <div class="feedback-item">
            <h3><%= feedback.getTitle() %></h3>
            <p><%= feedback.getDescription() %></p>
            <p>Punteggio: <%= feedback.getScore() %></p>
            <%
                User feedbackUser = new UserDAO().doRetrieveByKey(feedback.getUser());
            %>
            <p>Utente: <%= feedbackUser.getFirstName() + " " + feedbackUser.getLastName() %></p>
        </div>
        <%
            }
        } else {
        %>
        <p>Non ci sono feedback per questo prodotto.</p>
        <%
            }
        %>
    </div>
</div>
<% } %>
<%@ include file="../components/footer.jsp" %>
</body>
</html>
