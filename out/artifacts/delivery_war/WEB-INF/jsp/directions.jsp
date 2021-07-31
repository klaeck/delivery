<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored = "false" %>
<html>
<head>
    <title>Directions</title>
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
    request.getSession().setAttribute("prevURL", "home/directions_serv");
%>
<ul class="hor">
    <li><a class="home" href="/delivery_war/home"><%=bundle.getString("ui.home")%></a></li>

    <c:choose>
        <c:when test="${sessionScope.user != null}">
            <li><a href="/delivery_war/account"><%=bundle.getString("ui.account")%></a></li>
            <li style="float: right"><a href="/delivery_war/logout"><%=bundle.getString("ui.logout")%></a></li>
            <li style="float: right; color: white; margin-right: 10px"> <%=bundle.getString("ui.welcome")%>, ${sessionScope.user.firstName}!</li>
        </c:when>
        <c:otherwise>
            <li style="float: right"><a href="/delivery_war/login"><%=bundle.getString("ui.login")%></a></li>
            <li style="float: right"><a href="/delivery_war/signup"><%=bundle.getString("ui.signup")%></a></li>
        </c:otherwise>
    </c:choose>
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

<ul class="acc">
    <li><a href="/delivery_war/home/directions_serv"><%=bundle.getString("ui.directions")%></a></li>
    <li><a href="/delivery_war/home/rates_serv"><%=bundle.getString("ui.rates")%></a></li>
    <li><a href="/delivery_war/home/calculator"><%=bundle.getString("ui.calculator")%></a></li>
</ul>

<table class="tab">
    <tr>
        <th><%=bundle.getString("ui.name")%></th>
    </tr>

    <c:forEach items="${requestScope.directions}" var="d">
        <tr>
            <td>${d.city}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
