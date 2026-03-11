<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 13/07/24
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pagamento Completato</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/post-checkout.css">
</head>
<body>
<div class="main">
    <h1 class="feedback-title">Stiamo processando il pagamento</h1>
    <span id="icon">
        <svg
                id="check"
                version="1.1"
                xmlns="http://www.w3.org/2000/svg"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                viewBox="0 0 100 100"
                xml:space="preserve"
                class="progress"
        >
            <circle id="circle" cx="50" cy="50" r="46" fill="transparent" />
            <polyline id="tick" points="25,55 45,70 75,33" fill="transparent" />
        </svg>
    </span>
</div>
<script src="https://cdn.jsdelivr.net/npm/cleave.js@1.6.0/dist/cleave.min.js"></script>
<script>
    const circle = document.querySelector("#check");
    setTimeout(() => {
        circle.classList.toggle("progress");
        circle.classList.toggle("ready");
        // Redirect after 2 seconds
        setTimeout(() => {
            window.location.href = '${pageContext.request.contextPath}/pages/index.jsp';
        }, 2000);
    }, 2000);
</script>
</body>
</html>
