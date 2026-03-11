<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 14/07/24
  Time: 09:50
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/listfordate.css">
</head>
<body>
<%@include file="../../components/navbar.jsp"%>
<%
    ArrayList<Order> orders = (ArrayList<Order>) request.getAttribute("orders");
    Date dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateStart"));
    Date dateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateEnd"));
%>
<table>
    <tr>
        <th id="table-heading">
            <h3>
                Ordini<br />
                Dal <%= new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN).format(dateStart).toString() %><br />
                Al <%= new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN).format(dateEnd).toString() %>
            </h3>
        </th>
    </tr>
    <%
        for(Order order: orders){
            ArrayList<ProductOrder> products = new ProductOrderDAO().doRetrieveByOrder(order);
            float total = 0;
            for(ProductOrder product: products){
                total += product.getPrice() * product.getQuantity();
            }
    %>
    <tr>
        <td>
            <p class="paragraph"><span class="bold">Id Ordine:</span> <%= order.getIdOrder()%></p>
            <p class="paragraph"><span class="bold">Data: </span> <%= new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.ITALIAN).format(order.getDateOrder()).toString()%></p>
            <p class="paragraph"><span class="bold">Stato:</span> <%= order.getState()%></p>
            <p class="paragraph"><span class="bold">Email utente:</span> <%= new UserDAO().doRetrieveByKey(order.getIdUser()).getEmail() %></p>
            <p class="paragraph"><span class="bold">Prezzo Totale:</span> <%= total%> EUR</p>
            <a class="details paragraph" href="${pageContext.request.contextPath}/pages/order.jsp?id=<%= order.getIdOrder() %>">Dettagli</a>
        </td>
    </tr>
    <%
        }
    %>
</table>
<div class="pagination">
    <%
        int skip = Integer.parseInt(request.getParameter("skip"));
        int limit = Integer.parseInt(request.getParameter("limit"));
    %>
    <% if (skip > 0) { %>
    <form action="${pageContext.request.contextPath}/admin/listordersdate" method="get">
        <input type="hidden" name="dateStart" value="<%= request.getParameter("dateStart") %>">
        <input type="hidden" name="dateEnd" value="<%= request.getParameter("dateEnd") %>">
        <input type="hidden" name="skip" value="<%= (skip - 10) %>">
        <input type="hidden" name="limit" value="10">
        <button type="submit" class="btn-accent nav-button">
            <span class="paragraph bold">Indietro</span>
        </button>
    </form>
    <% } %>
    <form action="${pageContext.request.contextPath}/admin/listordersdate" method="get">
        <input type="hidden" name="dateStart" value="<%= request.getParameter("dateStart") %>">
        <input type="hidden" name="dateEnd" value="<%= request.getParameter("dateEnd") %>">
        <input type="hidden" name="skip" value="<%= (skip + 10) %>">
        <input type="hidden" name="limit" value="10">
        <button type="submit" class="btn-accent nav-button">
            <span class="paragraph bold">Avanti</span>
        </button>
    </form>
</div>

<%@include file="../../components/footer.jsp"%>
</body>
</html>

