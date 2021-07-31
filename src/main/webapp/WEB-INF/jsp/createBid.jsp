<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>Create bid</title>
    <style>
        <%@include file="../css/style.css"%>
        <%@include file="../css/calc.css"%>
        <%@include file="../css/btn.css"%>
    </style>

</head>
<body>
<%
    String locale = (String) request.getSession().getAttribute("locale");
    if(locale == null || (!locale.equalsIgnoreCase("en") && !locale.equalsIgnoreCase("ru"))){
        locale = "en";
    }

    ResourceBundle bundle = ResourceBundle.getBundle("language", new Locale(locale));
    request.getSession().setAttribute("prevURL", "account/create_bid");
%>
<ul class="hor">
    <li><a class="home" href="/delivery_war/home"><%=bundle.getString("ui.home")%></a></li>
    <li><a href="/delivery_war/account"><%=bundle.getString("ui.account")%></a></li>
    <li style="float: right"><a href="/delivery_war/logout"><%=bundle.getString("ui.logout")%></a></li>
    <li style="float: right; color: white; margin-right: 10px"> <%=bundle.getString("ui.welcome")%>, ${sessionScope.user.firstName}!</li>
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
    <li><a href="/delivery_war/account"><%=bundle.getString("ui.info")%></a></li>
    <li><a href="/delivery_war/account/bids_serv"><%=bundle.getString("ui.my_bids")%></a></li>
    <li><a href="/delivery_war/account/create_bid"><%=bundle.getString("ui.create_bid")%></a></li>
</ul>

<form action="create_bid_ver" method="post">
    <h3>
        <%=bundle.getString("calculator.departure")%>:
    </h3>
    <p>
        <label>
            <select name="departure" class="select-css input-field">
                <option value="1"><%=bundle.getString("delivery.Dnipro")%></option>
                <option value="2"><%=bundle.getString("delivery.Kyiv")%></option>
                <option value="3"><%=bundle.getString("delivery.Kharkiv")%></option>
                <option value="4"><%=bundle.getString("delivery.Lviv")%></option>
                <option value="5"><%=bundle.getString("delivery.Odessa")%></option>
            </select>
        </label>
    </p>

    <h3>
        <%=bundle.getString("calculator.destination")%>:
    </h3>
    <p>
        <label>
            <select name="destination" class="select-css input-field">
                <option value="1"><%=bundle.getString("delivery.Dnipro")%></option>
                <option value="2"><%=bundle.getString("delivery.Kyiv")%></option>
                <option value="3"><%=bundle.getString("delivery.Kharkiv")%></option>
                <option value="4"><%=bundle.getString("delivery.Lviv")%></option>
                <option value="5"><%=bundle.getString("delivery.Odessa")%></option>
            </select>
        </label>
    </p>

    <h3>
        <%=bundle.getString("ui.type")%>:
    </h3>
    <p>
        <label>
            <select name="type" class="select-css input-field">
                <option value="regular"><%=bundle.getString("type.regular")%></option>
                <option value="fragile"><%=bundle.getString("type.fragile")%></option>
                <option value="insured"><%=bundle.getString("type.insured")%></option>
            </select>
        </label>
    </p>

    <h3>
        <%=bundle.getString("calculator.weight")%>:
    </h3>
    <p>
        <label>
            <input class="input-field"  type="number" placeholder="<%=bundle.getString("calculator.weight")%>" name="weight" step="any" min="0.1" max="100" required>
        </label>
    </p>

    <h3>
        <%=bundle.getString("calculator.volume")%>:
    </h3>
    <p>
        <label>
            <select name="volume" class="select-css input-field">
                <option value="1"><%=bundle.getString("calculator.small_box")%> (10, 24, 17)</option>
                <option value="2"><%=bundle.getString("calculator.low_box")%> (5, 34, 24)</option>
                <option value="3"><%=bundle.getString("calculator.medium_box")%> (10, 34, 24)</option>
                <option value="4"><%=bundle.getString("calculator.big_box")%> (12, 37, 18)</option>
            </select>
        </label>
    </p>
    <p>
        <button class="button" type="submit"><span> <%=bundle.getString("ui.submit")%> </span></button>
    </p>
</form>
</body>
</html>
