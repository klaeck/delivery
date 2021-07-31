<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Calculator</title>
    <style>
        <%@include file="../css/style.css"%>
        <%@include file="../css/calc.css"%>
        <%@include file="../css/btn.css"%>
    </style>
</head>
<body>
<%
    String locale = (String) request.getSession().getAttribute("locale");
    if (locale == null || (!locale.equalsIgnoreCase("en") && !locale.equalsIgnoreCase("ru"))) {
        locale = "en";
    }

    ResourceBundle bundle = ResourceBundle.getBundle("language", new Locale(locale));
    request.getSession().setAttribute("prevURL", "home/calculator");
%>
<ul class="hor">
    <li><a class="home" href="/delivery_war/home"><%=bundle.getString("ui.home")%>
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
        <form action="/delivery_war/language" method="get">
            <label>
                <select onchange="this.form.submit()" name="locale" class="locale">
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

<form action="calculator_serv" method="post" style="font-size: 40px" class="calc" onsubmit='return document.forms[1].weight.value !== "";'>
    <h3>
        <%=bundle.getString("calculator.departure")%>:
    </h3>
    <p>
        <label>
            <select name="departure" class="select-css input-field">
                <option value="1"><%=bundle.getString("delivery.Dnipro")%>
                </option>
                <option value="2"><%=bundle.getString("delivery.Kyiv")%>
                </option>
                <option value="3"><%=bundle.getString("delivery.Kharkiv")%>
                </option>
                <option value="4"><%=bundle.getString("delivery.Lviv")%>
                </option>
                <option value="5"><%=bundle.getString("delivery.Odessa")%>
                </option>
            </select>
        </label>
    </p>

    <h3>
        <%=bundle.getString("calculator.destination")%>:
    </h3>
    <p>
        <label>
            <select name="destination" class="select-css input-field">
                <option value="1"><%=bundle.getString("delivery.Dnipro")%>
                </option>
                <option value="2"><%=bundle.getString("delivery.Kyiv")%>
                </option>
                <option value="3"><%=bundle.getString("delivery.Kharkiv")%>
                </option>
                <option value="4"><%=bundle.getString("delivery.Lviv")%>
                </option>
                <option value="5"><%=bundle.getString("delivery.Odessa")%>
                </option>
            </select>
        </label>
    </p>

    <strong>
        <%=bundle.getString("calculator.weight")%>:
        <%
            String answer = "";
            if (request.getParameter("price") != null) {
                answer = bundle.getString("calculator.answer") + request.getParameter("price");
            }
        %>

        <tag style="margin-left: 500px; text-decoration: underline">
            <%= answer %>
        </tag>

    </strong>
    <p>
        <label>
            <input class="input-field" type="number" placeholder="<%=bundle.getString("calculator.weight")%>" name="weight" step="any"
                   min="0.1" max="100" required>
        </label>
    </p>

    <h3>
        <%=bundle.getString("calculator.volume")%>:
    </h3>

    <p>
        <label>
            <select name="volume" class="select-css input-field" style="width: fit-content">
                <option value="1"><%=bundle.getString("calculator.small_box")%> (10, 24, 17)</option>
                <option value="2"><%=bundle.getString("calculator.low_box")%> (5, 34, 24)"</option>
                <option value="3"><%=bundle.getString("calculator.medium_box")%> (10, 34, 24)</option>
                <option value="4"><%=bundle.getString("calculator.big_box")%> (12, 37, 18)</option>
            </select>
        </label>

    <p>
    <button class="button" onclick=" if(!document.forms[1].weight.value().empty()) {submit()} "><span> <%=bundle.getString("ui.submit")%> </span></button>
    </p>

</form>

</body>
</html>
