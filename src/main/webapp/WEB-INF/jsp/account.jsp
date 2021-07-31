<%@ page import="entity.User" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Account</title>
    <style> <%@include file="../css/style.css"%> </style>
</head>
<body>
<%
    String locale = (String) request.getSession().getAttribute("locale");
    if(locale == null || (!locale.equalsIgnoreCase("en") && !locale.equalsIgnoreCase("ru"))){
        locale = "en";
    }

    ResourceBundle bundle = ResourceBundle.getBundle("language", new Locale(locale));
    request.getSession().setAttribute("prevURL", "account");
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
<ul class="add">
    <li><a href="/delivery_war/account"><%=bundle.getString("ui.info")%></a></li>
    <li><a href="/delivery_war/account/bids_serv"><%=bundle.getString("ui.my_bids")%></a></li>
    <li><a href="/delivery_war/account/create_bid"><%=bundle.getString("ui.create_bid")%></a></li>
</ul>

<h1 style="alignment: center">
    <% User user = (User) request.getSession().getAttribute("user");%>

    <%=bundle.getString("ui.welcome")%>, <%= user.getFirstName()%>!

    <br><br>
    <%=bundle.getString("info.full") + ":"%>
    <br>
    <%=bundle.getString("info.number") + " : " + user.getPhone() %>
    <br>
    <%=bundle.getString("info.name") + " : " + user.getFirstName() + " " + user.getLastName()%>
    <br>
</h1>
</body>
</html>
