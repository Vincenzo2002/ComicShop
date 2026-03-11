<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 11/07/24
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.dao.Cart" %>
<%@ page import="model.dao.CartEntry" %>
<%@ page import="java.util.Iterator" %>
<!DOCTYPE html>
<html>
<head>
    <title>Carrello</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/carrello.css">
</head>
<body>

<%@ include file="../components/navbar.jsp" %>
<div class="cart">
    <div class="cart-header">
        <h2 id="#cart-title">Carrello</h2>
        <form action="${pageContext.request.contextPath}/pages/index.jsp">
            <button type="submit" class="btn-accent nav-button">
                <span class="paragraph bold">Continua lo shopping</span>
            </button>
        </form>
    </div>
    <div class="product-row t-header">
        <div class="product-info"><h3>Prodotto</h3></div>
        <div class="product-price"><h3>Prezzo</h3></div>
    </div>
    <%
        request.getSession().setAttribute("redirect", "/pages/carrello.jsp");
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            Iterator<CartEntry> cartIterator = cart.getProduct();
            float total = 0;
            if (cartIterator.hasNext()) {
                while (cartIterator.hasNext()) {
                    CartEntry entry = cartIterator.next();
                    total += entry.getQuantity() * entry.getProduct().getPrice();
    %>
    <div class="product-row" data-product-id="<%= entry.getProduct().getIdProduct() %>">
        <div class="product-info">
            <h4><%= entry.getProduct().getName() %></h4>
            <div class="qprice">
                <input class="text-input small-text" id="i-<%= entry.getProduct().getIdProduct() %>" onchange="editCart(<%= entry.getProduct().getIdProduct() %>, 'p-<%= entry.getProduct().getIdProduct() %>')" min="1" max="<%= entry.getProduct().getQuantity() %>" type="number" value="<%= entry.getQuantity() %>">
                <p class="paragraph bold sing-price">x <%= entry.getProduct().getPrice() %> &euro;</p>
            </div>
        </div>
        <div class="product-price" id="p-<%= entry.getProduct().getIdProduct() %>">
            <h4>&euro; <%= String.format("%.2f", entry.getProduct().getPrice() * entry.getQuantity()) %></h4>
            <button type="button" class="btn-accent nav-button del-button" onclick="removeFromCart(<%= entry.getProduct().getIdProduct() %>)">
                <span class="paragraph bold">x</span>
            </button>
        </div>
    </div>
    <%
        }
    %>
    <div class="product-row totale-row">
        <div class="total">
            <p class="paragraph bold">Totale: <%= String.format("%.2f", total) %> &euro;</p><br>
            <form action="${pageContext.request.contextPath}/pages/checkout.jsp">
                <button type="submit" class="btn-accent nav-button checkout-button">
                    <span class="paragraph bold">Checkout</span>
                </button>
            </form>
        </div>
    </div>
    <%
    } else {
    %>
    <h3>Non hai prodotti nel carrello</h3>
    <%
        }
    } else {
    %>
    <h2>Non hai inserito nessun prodotto nel carrello</h2>
    <%
        }
    %>
</div>

<%@ include file="../components/footer.jsp" %>
<script>
    // Funzione per modificare la quantità di un prodotto nel carrello
    function editCart(productId, priceElementId) {
        // Ottieni la nuova quantità dal campo di input
        var quantity = document.getElementById('i-' + productId).value;

        // Crea un oggetto XMLHttpRequest per effettuare la richiesta POST
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "${pageContext.request.contextPath}/editcart", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        // Gestisci la risposta della richiesta
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) { // Verifica che la richiesta sia completata
                if (xhr.status === 200) { // Verifica che la risposta sia positiva
                    // Analizza la risposta JSON
                    var response = JSON.parse(xhr.responseText);
                    // Aggiorna il contenuto dell'elemento che mostra il prezzo
                    document.getElementById(priceElementId).innerHTML = '<h4>&euro; ' + response.totalPrice.toFixed(2) + '</h4>';
                    // Aggiorna i totali del carrello
                    updateCartTotals(response.cartTotal);
                    // Ricarica la pagina per aggiornare completamente il carrello
                    setTimeout(function() {
                        window.location.reload();
                    }, 1000);
                } else {
                    // Log dell'errore in caso di status diverso da 200
                    console.error("Errore durante la modifica della quantità del prodotto: " + xhr.responseText + "\nStatus Code: " + xhr.status);
                }
            }
        };
        // Invia i dati al server
        xhr.send("pId=" + productId + "&q=" + quantity);
    }

    // Funzione per rimuovere un prodotto dal carrello
    function removeFromCart(productId) {
        // Crea un oggetto XMLHttpRequest per effettuare la richiesta POST
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "${pageContext.request.contextPath}/removecart", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        // Gestisci la risposta della richiesta
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) { // Verifica che la richiesta sia completata
                if (xhr.status === 200) { // Verifica che la risposta sia positiva
                    // Trova la riga del prodotto da rimuovere
                    var productRow = document.querySelector('.product-row[data-product-id="' + productId + '"]');
                    if (productRow) {
                        // Rimuove la riga del prodotto dalla pagina
                        productRow.parentNode.removeChild(productRow);
                    }
                    // Analizza la risposta JSON
                    var response = JSON.parse(xhr.responseText);
                    // Aggiorna i totali del carrello
                    updateCartTotals(response.cartTotal);
                } else {
                    // Log dell'errore in caso di status diverso da 200
                    console.error("Errore durante la rimozione del prodotto dal carrello: " + xhr.responseText + "\nStatus Code: " + xhr.status);
                }
            }
        };
        // Invia i dati al server
        xhr.send("pId=" + productId);
    }

    // Funzione per aggiornare i totali del carrello
    function updateCartTotals(total) {
        // Trova l'elemento che mostra il totale del carrello
        var totalElement = document.querySelector('.totale-row .total p');
        if (totalElement) {
            // Aggiorna il testo dell'elemento con il totale formattato
            totalElement.innerText = 'Totale: ' + total.toFixed(2) + ' €';
        } else {
            // Log dell'errore se l'elemento per il totale non viene trovato
            console.error("Elemento per il totale non trovato.");
        }
    }
</script>

</body>
</html>
