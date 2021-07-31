<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Access error</title>
    <style>
        <%@include file="../css/style.css"%>
    </style>
</head>
<body>
<%
    String locale = (String) request.getSession().getAttribute("locale");
    if(locale == null || (!locale.equalsIgnoreCase("en") && !locale.equalsIgnoreCase("ru"))){
        locale = "en";
    }

    ResourceBundle bundle = ResourceBundle.getBundle("language", new Locale(locale));
    request.getSession().setAttribute("prevURL", "access_error");
%>
<ul class="hor">
    <li><a class="home" href="/delivery_war/home"><%=bundle.getString("ui.home")%></a></li>
    <li style="float: right"><a href="/delivery_war/login"><%=bundle.getString("ui.login")%></a></li>
    <li style="float: right"><a href="/delivery_war/signup"><%=bundle.getString("ui.signup")%></a></li>
    <li>
        <form action="/delivery_war/language" method="get">
            <label>
                <select onchange="this.form.submit()" name="locale" class="locale">
                    <option <c:if test="${sessionScope.locale.equalsIgnoreCase('en')}"> selected </c:if> value="en">eng</option>
                    <option <c:if test="${sessionScope.locale.equalsIgnoreCase('ru')}"> selected </c:if> value="ru">рус</option>
                </select>
            </label>
        </form>
    </li>
</ul>

<h2 style="text-align: center; margin-top: 150px">
    <strong><%=bundle.getString("ui.access_error")%> <a href="/delivery_war/login"><%=bundle.getString("login.submit")%></a> </strong>
</h2>
</body>
</html>
