<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 14/07/24
  Time: 18:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.bean.Product" %>
<!DOCTYPE html>
<html>
<head>
    <title>Catalogo Prodotti</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/products.css">
</head>
<body>
<%@ include file="../components/navbar.jsp" %>

<%
    ArrayList<Product> products = (ArrayList<Product>) request.getAttribute("products");
    if (products != null) {
%>
<h2>Catalogo Prodotti</h2>
<table>
    <tr id="table-heading">
        <th><h4>Nome</h4></th>
        <th><h4>Prezzo</h4></th>
        <th><h4>Informazioni</h4></th>
        <th><h4>Foto</h4></th>
        <th><h4>Azioni</h4></th>
    </tr>
    <tr>
        <td colspan="5" class="paragraph"><a href="${pageContext.request.contextPath}/pages/admin/update-product.jsp">Aggiungi nuovo prodotto</a></td>
    </tr>
    <%
        for (Product p : products) {
    %>
    <tr>
        <td class="paragraph"><%= p.getName() %></td>
        <%
            String price = String.format("%.2f", p.getPrice()).replace('.', ',');
        %>
        <td class="paragraph"><%= price %> &euro;</td>
        <td class="paragraph"><%= p.getDescription() %></td>
        <td><img src="<%= p.getUrlImage().startsWith("http") ? p.getUrlImage() : request.getContextPath() + p.getUrlImage() %>" class="table-image" alt="<%= p.getName() %>" title="<%= p.getName() %>"/></td>
        <td class="paragraph">
            <a href="${pageContext.request.contextPath}/pages/admin/update-product.jsp?id=<%= p.getIdProduct() %>" class="details">Modifica »</a>
        </td>
    </tr>
    <%
        }
    %>
</table>
<%
} else {
%>
<h2>Impossibile accedere ai prodotti</h2>
<br>
<p class="paragraph">
    Ci dispiace, potrebbero non esserci prodotti nel database,<br>
    oppure stai cercando di effettuare un accesso a questa pagina non autorizzato
</p>
<%
    }
%>

<%@ include file="../components/footer.jsp" %>
</body>
</html>


