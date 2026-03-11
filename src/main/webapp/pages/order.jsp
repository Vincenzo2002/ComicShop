<%@ page import="model.bean.Order" %>
<%@ page import="model.dao.OrderDAO" %>
<%@ page import="model.dao.ProductOrderDAO" %>
<%@ page import="model.bean.ProductOrder" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Riepilogo Ordine</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/order.css">
</head>
<body>
<%@ include file="../components/navbar.jsp" %>
<%
    if(user == null){
        response.sendRedirect("register-login.jsp");
        return;
    }

    Order order = new OrderDAO().doRetrieveByKey(Integer.parseInt(request.getParameter("id")));
    if(order.getIdUser() != user.getIdUser() && !user.isAdmin()){
        //L'utente sta cercando di visualizzare un ordine non effettuato da lui
%>
<h3>Questo ordine non esiste</h3>
<%
        return;
    }

    ProductOrderDAO productOrderDAO = new ProductOrderDAO();
    ArrayList<ProductOrder> products = productOrderDAO.doRetrieveByOrder(order);
%>

<div class="parent-main">
    <div class="prodotti">
        <div class="section-prodotti-header">
            <h3>Prodotti</h3>
        </div>
        <table class="prodotti-table">
            <thead class="paragraph bold">
            <tr>
                <td>Nome</td>
                <td>Quantit&agrave;</td>
                <td>Prezzo totale</td>
            </tr>
            </thead>
            <tbody>
            <%
                float totale = 0;
                for(ProductOrder product: products){
                    totale += product.getPrice() * product.getQuantity();
            %>
            <tr>
                <td><a class="" href="${pageContext.request.contextPath}/product?id=<%= product.getProduct().getIdProduct() %>"><%= product.getProduct().getName() %></a></td>
                <td><p class="paragraph" style="text-align: center"><%= product.getQuantity()%></p></td>
                <td><p class="paragraph bold"><%= product.getPrice() * product.getQuantity() %>&euro;</p></td>
            </tr>

            <%
                }
            %>
            </tbody>
        </table>
    </div>
    <div class="sezione-destra">
        <div class="pagamento">
            <div class="section-prodotti-header">
                <h3>Pagamento</h3>
            </div>
            <table>
                <tr>
                    <td><p class="paragraph">Totale:</p></td>
                    <td><p class="paragraph bold"><%= String.format("%.2f", totale) %> &euro;</p></td>
                </tr>
            </table>
        </div>
        <div class="info">
            <div class="section-prodotti-header">
                <h3>Informazioni</h3>
            </div>
            <table>
                <tr>
                    <td><p class="paragraph">Id Ordine:</p></td>
                    <td><p class="paragraph bold"><%= order.getIdOrder() %></p></td>
                </tr>
                <tr>
                    <td><p class="paragraph">Data: </p></td>
                    <td><p class="paragraph bold"><%= new SimpleDateFormat("dd/MM/yyyy").format(order.getDateOrder()) %></p></td>
                </tr>
                <tr>
                    <td><p class="paragraph">Stato: </p></td>
                    <td><p class="paragraph bold"><%= order.getState() %></p></td>
                </tr>
            </table></div>
    </div>

</div>

<%@ include file="../components/footer.jsp" %>

</body>
</html>

