<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 18/07/24
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="model.dao.UserDAO"%>
<%@page import="model.dao.ProductOrderDAO"%>
<%@page import="java.util.ArrayList" %>
<%@page import="model.bean.*" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Locale" %>
<%@page import="java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
    <title>ComicShop - Amministratore</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/listallorders.css">
</head>
<body>
<%@include file="../../components/navbar.jsp"%>
<div class="container">
    <table>
        <thead>
        <tr>
            <th>Id Ordine</th>
            <th>Data</th>
            <th>Stato</th>
            <th>Email utente</th>
            <th>Prezzo Totale</th>
            <th>Dettagli</th>
        </tr>
        </thead>
        <tbody>
        <%
            //Recupera l'attributo order dalla request
            ArrayList<Order> orders = (ArrayList<Order>) request.getAttribute("orders");
            if (orders != null) {
                for(Order order: orders){
                    //Per ogni ordine recupera i prodotti che ne fanno parte e calcola il totale
                    ArrayList<ProductOrder> products = new ProductOrderDAO().doRetrieveByOrder(order);
                    float total = 0;
                    for(ProductOrder product: products){
                        total += product.getPrice() * product.getQuantity();
                    }
        %>
        <tr>
            <td><%= order.getIdOrder() %></td>
            <td><%= new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN).format(order.getDateOrder()) %></td>
            <td><%= order.getState() %></td>
            <td><%= new UserDAO().doRetrieveByKey(order.getIdUser()).getEmail() %></td>
            <td><%= total %> EUR</td>
            <td><a class="details" href="${pageContext.request.contextPath}/pages/order.jsp?id=<%= order.getIdOrder() %>">Dettagli</a></td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="6">Nessun ordine trovato</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <div class="pagination">
        <%
            int skip = Integer.parseInt(request.getParameter("skip"));
            int limit = Integer.parseInt(request.getParameter("limit"));
        %>
        <% if (skip > 0) { %>
        <form action="${pageContext.request.contextPath}/admin/listallorders" method="get">
            <input type="hidden" name="skip" value="<%= (skip - 10) %>">
            <input type="hidden" name="limit" value="10">
            <button type="submit" class="btn-accent nav-button">
                <span class="paragraph bold">Indietro</span>
            </button>
        </form>
        <% } %>
        <form action="${pageContext.request.contextPath}/admin/listallorders" method="get">
            <input type="hidden" name="skip" value="<%= (skip + 10) %>">
            <input type="hidden" name="limit" value="10">
            <button type="submit" class="btn-accent nav-button">
                <span class="paragraph bold">Avanti</span>
            </button>
        </form>
    </div>
</div>
<%@include file="../../components/footer.jsp"%>
</body>
</html>
