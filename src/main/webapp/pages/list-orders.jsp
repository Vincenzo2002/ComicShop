<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 10/07/24
  Time: 09:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="model.bean.Order" %>
<%@ page import="model.dao.OrderDAO"%>
<%@ page import="model.dao.ProductOrderDAO"%>
<%@ page import="model.bean.ProductOrder"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Locale" %>
<html>
<head>
    <title>I Tuoi Ordini</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/list-orders.css">
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
        <%
            //Recupera tutti gli ordini di un user
            OrderDAO orderDAO = new OrderDAO();
            ProductOrderDAO productOrderDAO = new ProductOrderDAO();
            ArrayList<Order> orders = null;
            try {
                orders = (ArrayList<Order>) orderDAO.doRetrieveByUser(user, "ID_Order DESC");
            } catch (SQLException e) {
                response.getWriter().println("Error");
                e.printStackTrace();
                return;
            }
        %>

        <div class="table-heading">
            <h3>I Tuoi Ordini</h3>
        </div>

        <%
            if (orders == null || orders.isEmpty()) {
        %>
        <p class="no-orders">Non hai effettuato nessun ordine</p>
        <%
        } else {
        %>
        <table>
            <%
                for (Order order : orders) {
                    ArrayList<ProductOrder> products = productOrderDAO.doRetrieveByOrder(order);
                    float total = 0;
                    for (ProductOrder product : products) {
                        total += product.getPrice() * product.getQuantity();
                    }
            %>
            <tr>
                <td>
                    <p class="paragraph"><span class="bold">Id Ordine:</span> <%= order.getIdOrder() %></p>
                    <p class="paragraph"><span class="bold">Data:</span> <%= new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN).format(order.getDateOrder()).toString() %></p>
                    <p class="paragraph"><span class="bold">Stato:</span> <%= order.getState() %></p>
                    <p class="paragraph"><span class="bold">Prezzo Totale:</span> <%= total %></p>
                    <a class="details paragraph" href="${pageContext.request.contextPath}/pages/order.jsp?id=<%= order.getIdOrder() %>">Dettagli</a>
                </td>
            </tr>
            <%
                }
            %>
        </table>
        <%
            }
        %>
    </div>
</div>
<%@ include file="../components/footer.jsp" %>
</body>
</html>

