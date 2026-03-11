<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 14/07/24
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ComicShop - Amministratore</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/indexAdmin.css">
</head>
<body>
<%@include file="../../components/navbar.jsp"%>
<div class="container">
    <div class="top">
        <h1>ComicShop - Amministratore</h1>
        <a href="${pageContext.request.contextPath}/admin/products" class="action-button">
            <i class="fas fa-box-open icon"></i>
            <p>Gestione catalogo</p>
        </a>
    </div>
    <div class="bottom">
        <p>Ricerca ordini:</p>
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/admin/listordersdate">
                <p>Per data:</p>
                <p class="paragraph">Da:</p> <input type="date" class="text-input" name="dateStart"><br />
                <p class="paragraph">A:</p> <input type="date" class="text-input" name="dateEnd"><br />
                <input type="hidden" name="skip" value="0">
                <input type="hidden" name="limit" value="10">
                <input class="submit" type="submit">
            </form>

            <form action="${pageContext.request.contextPath}/admin/listordersemail">
                <p>Per email:</p>
                <input type="text" class="text-input" name="email" placeholder="Email Cliente">
                <input type="hidden" name="skip" value="0">
                <input type="hidden" name="limit" value="10">
                <input class="submit" type="submit">
            </form>
        </div>


        <form action="${pageContext.request.contextPath}/admin/listallorders">
            <p>Tutti gli ordini</p>
            <input type="hidden" name="skip" value="0">
            <input type="hidden" name="limit" value="10">
            <input class="action-button" type="submit">
        </form>
    </div>
</div>
<%@include file="../../components/footer.jsp"%>
</body>
</html>

