<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 13/07/24
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.Product"%>
<%@ page import="model.dao.ProductDAO"%>
<!DOCTYPE html>
<html>
<head>
    <title>Aggiorna Prodotto</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/update-product.css">
</head>
<body>
<%@include file="../../components/navbar.jsp"%>
<%
    Product product = null;
    if(request.getParameter("id") != null){
        int productId = Integer.parseInt(request.getParameter("id"));
        product = new ProductDAO().doRetrieveByKey(productId);
    }
    String category = new ProductDAO().getCategoryProduct(product);
%>
<div class="container">

    <form action="${pageContext.request.contextPath}/admin/updateproduct" enctype="multipart/form-data" method="post" >
        <div class="two-row-input">
            <div class="input">
                <label>Nome</label>
                <input required class="text-input" value="<%= product != null ? product.getName() : "" %>" type="text" name="nameProduct"
                />
            </div>
            <div class="input">
                <label>Prezzo</label>
                <input required type="number" name="price" step="any" value="<%= product != null ? product.getPrice() : "" %>"/>
            </div>
        </div>
        <div class="two-row-input">
            <div class="input">
                <label>Quantit&agrave;</label>
                <input required type="number" min="0" name="quantity" value="<%= product != null ? product.getQuantity() : "" %>"
                />
            </div>
        </div>
        <textarea name="description" rows="5" cols="45" placeholder="Descrizione prodotto"><%= product != null ? product.getDescription() : "" %></textarea>
        <div class="two-row-input">
            <div class="input">
                <label>Categoria</label>
                <select name="category" id="category">
                    <option value="Fumetti">Fumetti</option>
                    <option value="Manga">Manga</option>
                    <option value="Gadget">Gadget</option>
                </select>
            </div>
        </div>
        <div class="img-choice-container">
            <img id="product-img" src="<%= product != null ? request.getContextPath() + product.getUrlImage() : "https://via.placeholder.com/300x225" %>"
                 alt="prodotto">
            <input type="file" name="prod_img" />
        </div>
        <%if(request.getParameter("id") != null){ %>
        <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
        <% } %>
        <input type="submit" value="Aggiorna" />
    </form>

</div>
<%@include file="../../components/footer.jsp"%>


<script>
    // Recupera la categoria
    var productCategory = "<%= category %>";

    // Imposta il valore del <select> in base alla categoria del prodotto
    document.getElementById('category').value = productCategory;
</script>
</body>
</html>
