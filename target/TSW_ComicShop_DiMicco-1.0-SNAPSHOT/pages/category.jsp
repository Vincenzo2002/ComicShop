<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 10/07/24
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html;"%>
<%@page import="model.bean.Product" %>
<%@page import="model.dao.ProductDAO" %>
<%@page import="java.util.ArrayList" %>
<%@ page import="jakarta.servlet.ServletContext" %>

<!DOCTYPE html>
<html>
<head>
    <!-- Imposta il titolo della pagina dinamicamente in base alla categoria -->
    <title>ComicShop - <%= request.getParameter("category") %></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/category.css">

    <script>
        function addToCart(productId, quantity, button) {
            // Aggiunge l'animazione al bottone cliccato
            button.classList.add('animate');

            // Rimuove l'animazione dopo che è completata per poterla riutilizzare
            setTimeout(function() {
                button.classList.remove('animate');
            }, 500);

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
    </script>

</head>
<body>
<!-- Include il menu di navigazione -->
<%@ include file="../components/navbar.jsp" %>

<div class="container">
    <div class="product-grid">
        <%
            // Ottiene i parametri della richiesta e gli attributi impostati dal servlet
            String category = request.getParameter("category");
            int numProducts = (int) request.getAttribute("numProducts");
            ArrayList<Product> products = (ArrayList<Product>) request.getAttribute("products");

            // Itera sui prodotti e li visualizza se la quantità è maggiore di 0
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                if (product.getQuantity() > 0) {
        %>

        <div class="card">
            <!-- Immagine del prodotto -->
            <div class="card-img-container">
                <a href="${pageContext.request.contextPath}/product?id=<%=product.getIdProduct()%>">
                    <img src="<%= product.getUrlImage().startsWith("http") ? product.getUrlImage() : request.getContextPath() + product.getUrlImage() %>" class="table-image" alt="<%= product.getName() %>" title="<%= product.getName() %>"/>
                </a>
            </div>
            <div class="card-body">
                <!-- Titolo e descrizione del prodotto -->
                <h3 class="product-title"><%= product.getName() %></h3>
                <p class="paragraph product-description"><%= product.getDescription() %></p>
            </div>
            <div class="card-footer">
                <!-- Bottone per aggiungere il prodotto al carrello -->
                <button class="btn-accent bottom-left" onclick="addToCart(<%= product.getIdProduct() %>, 1, this)">
                    <i class="fas fa-shopping-cart fa-lg"></i>
                    <span class="paragraph bold">Aggiungi</span>
                    <span class="animation-effect"></span>
                </button>
                <!-- Prezzo del prodotto -->
                <h4 class="price-text"><%= String.format("%.2f", product.getPrice()) %> &euro;</h4>
            </div>
        </div>
        <%
                } // Fine if quantity > 0
            } // Fine for
        %>
    </div>

    <div class="nav-containers">
        <%
            // Gestisce la paginazione
            int skip = Integer.parseInt(request.getParameter("skip"));
            int limit = Integer.parseInt(request.getParameter("limit"));
        %>
        <% if (skip > 0) { %>
        <!-- Bottone per la pagina precedente -->
        <form action="${pageContext.request.contextPath}/category">
            <input type="hidden" name="category" value="<%= request.getParameter("category") %>">
            <input type="hidden" name="skip" value="<%= ((int)skip - 9) %>">
            <input type="hidden" name="limit" value="9">
            <button type="submit" class="btn-accent nav-button">
                <span class="paragraph bold">Indietro</span>
            </button>
        </form>
        <% } %>
        <% if (skip + limit < numProducts && numProducts != 0) { %>
        <!-- Bottone per la pagina successiva -->
        <form action="${pageContext.request.contextPath}/category">
            <input type="hidden" name="category" value="<%= request.getParameter("category") %>">
            <input type="hidden" name="skip" value="<%= ((int)skip + 9) %>">
            <input type="hidden" name="limit" value="9">
            <button type="submit" class="btn-accent nav-button">
                <span class="paragraph bold">Avanti</span>
            </button>
        </form>
        <% } %>
    </div>
</div>
<!-- Include il footer -->
<%@ include file="../components/footer.jsp" %>
</body>
</html>
