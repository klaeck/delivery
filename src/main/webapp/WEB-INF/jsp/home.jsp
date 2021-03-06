<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html" pageEncoding="UTF-16BE" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<html>
<head>
    <title>Welcome</title>
    <meta charset="UTF-8">
    <style>
        <%@include file="../css/style.css"%>
    </style>
</head>
<body>

<%
    String locale = (String) request.getSession().getAttribute("locale");
    if (locale == null || (!locale.equalsIgnoreCase("en") && !locale.equalsIgnoreCase("ru"))) {
        locale = "en";
    }

    ResourceBundle bundle = ResourceBundle.getBundle("language", new Locale(locale));
    request.getSession().setAttribute("prevURL", "home");
%>

<ul class="hor">
    <li><a class="home" href="home"><%=bundle.getString("ui.home")%>
    </a></li>
    <c:choose>
        <c:when test="${sessionScope.user != null}">
            <li><a href="/delivery_war/account"><%=bundle.getString("ui.account")%>
            </a></li>

            <li style="float: right"><a href="/delivery_war/logout"><%=bundle.getString("ui.logout")%>
            </a></li>
            <li style="float: right; color: white; margin-right: 10px"><%=bundle.getString("ui.welcome")%>
                , ${sessionScope.user.firstName}!
            </li>
        </c:when>
        <c:otherwise>
            <li style="float: right"><a href="/delivery_war/login"><%=bundle.getString("ui.login")%>
            </a></li>
            <li style="float: right"><a href="/delivery_war/signup"><%=bundle.getString("ui.signup")%>
            </a></li>
        </c:otherwise>
    </c:choose>

    <li>
        <form action="/delivery_war/language" method="get" class="locale">
            <label>
                <select class="locale" onchange="this.form.submit()" name="locale">
                    <option <c:if test="${sessionScope.locale.equalsIgnoreCase('en')}"> selected </c:if> value="en">
                        eng
                    </option>
                    <option <c:if test="${sessionScope.locale.equalsIgnoreCase('ru')}"> selected </c:if> value="ru">
                        рус
                    </option>
                </select>
            </label>
        </form>
    </li>
</ul>

<ul class="acc">
    <li><a href="/delivery_war/home/directions_serv"><%=bundle.getString("ui.directions")%>
    </a></li>
    <li><a href="/delivery_war/home/rates_serv"><%=bundle.getString("ui.rates")%>
    </a></li>
    <li><a href="/delivery_war/home/calculator"><%=bundle.getString("ui.calculator")%>
    </a></li>
</ul>
<div class="infozone">
   <%=bundle.getString("home.text")%>
</div>
</body>
</html>
