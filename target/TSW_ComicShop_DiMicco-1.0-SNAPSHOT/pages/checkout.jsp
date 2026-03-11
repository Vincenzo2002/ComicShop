<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 12/07/24
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;"%>
<%@ page import="model.dao.Cart"%>
<%@ page import="model.dao.CartEntry"%>
<%@ page import="java.util.Iterator"%>
<%
    if(request.getSession().getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/pages/register-login.jsp");
    } else if (request.getSession().getAttribute("cart") == null || ((Cart) request.getSession().getAttribute("cart")).getNumProduct() == 0) {
        response.sendRedirect(request.getContextPath() + "/pages/index.jsp");
    } else { %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/checkout.css">
</head>
<body>
<%@ include file="../components/navbar.jsp" %>
<% Cart cart = (Cart) request.getSession().getAttribute("cart"); %>
<div class="checkout">
    <div class="payment-card">
        <div class="payment-card-header">
            <h3>Checkout</h3>
            <i id="card-type" class="fas fa-lg fa-credit-card"></i>
        </div>
        <div class="payment-card-body">
            <div class="field">
                <label class="paragraph bold">Nome titolare carta</label>
                <input class="payment-long-input text-input" type="text" required/>
            </div>
            <div class="field">
                <label class="paragraph bold">Numero carta</label>
                <input class="payment-long-input text-input cc-number" type="text" maxlength="19" required oninput="formatCardNumber(this)" />
            </div>
            <div class="row-2-input">
                <div class="field">
                    <label class="paragraph bold">Scadenza</label>
                    <input class="text-input cc-scadenza" type="text" maxlength="5" required oninput="formatExpirationDate(this)" />
                </div>
                <div class="field">
                    <label class="paragraph bold">CCV</label>
                    <input class="text-input cc-ccv" type="text" minlength="3" maxlength="3" required />
                </div>
            </div>
        </div>
    </div>
    <div class="total-amount">
        <div class="payment-card-header">
            <h3>Pagamento</h3>
        </div>
        <div class="info-body">
            <%
                float totale = 0;
                Iterator<CartEntry> i = cart.getProduct();
                while (i.hasNext()) {
                    CartEntry entry = i.next();
                    totale += (float) (entry.getQuantity() * entry.getProduct().getPrice());
                }
            %>
            <table class="paragraph">
                <tr>
                    <td class="bold">Totale</td>
                    <td><%= String.format("%.2f", totale) %> &euro;</td>
                </tr>
            </table>
            <form class="confirm-button" action="${pageContext.request.contextPath}/checkout">
                <button type="submit" class="btn-accent nav-button confirm-button">
                    <span class="paragraph bold">Conferma</span>
                </button>
            </form>
        </div>
    </div>
</div>
<script>
    function formatCardNumber(input) {
        // Rimuove tutti i caratteri non numerici
        let value = input.value.replace(/\D/g, '');
        // Aggiunge uno spazio ogni 4 cifre
        input.value = value.replace(/(.{4})/g, '$1 ').trim();
    }

    function formatExpirationDate(input) {
        // Rimuove tutti i caratteri non numerici
        let value = input.value.replace(/\D/g, '');
        // Aggiunge una barra dopo 2 cifre (MM/YY)
        input.value = value.replace(/(.{2})/, '$1/').trim();
    }
</script>
<%@ include file="../components/footer.jsp" %>
</body>
</html>
<% } %>
