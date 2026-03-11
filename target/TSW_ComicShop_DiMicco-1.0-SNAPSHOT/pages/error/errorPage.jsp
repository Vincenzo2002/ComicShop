<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 19/07/24
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error Page</title>
    <meta http-equiv="refresh" content="5;url=${pageContext.request.contextPath}/pages/index.jsp">
</head>
<body>
<h1>Error</h1>
<div style="color: red;">
    <h2>Error:</h2>
    <p><%= request.getAttribute("errorMsg") %></p>
</div>
<p>You will be redirected to the signup page in 5 seconds...</p>
<a href="${pageContext.request.contextPath}/pages/index.jsp">Back to Home</a>
</body>
</html>
