<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<%
    String errorMessage = request.getParameter("error");
    if (errorMessage == null) {
        errorMessage = "Unknown error";
    }
%>
<h1 style="color: red; align: center">
    <%= errorMessage%>
</h1>
</body>
</html>
