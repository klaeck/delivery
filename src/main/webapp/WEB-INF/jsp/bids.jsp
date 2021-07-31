<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Bids</title>
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
    request.getSession().setAttribute("prevURL", "account/bids_serv");
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

<form class="filter" action="bids_serv" method="post">
    <ul>
        <li> <%=bundle.getString("ui.from")%>:</li>
        <li style="margin-left: 3px">
            <label>
                <select name="from">
                    <option value="0"><%=bundle.getString("delivery.any")%></option>
                    <option value="1"><%=bundle.getString("delivery.Dnipro")%></option>
                    <option value="2"><%=bundle.getString("delivery.Kyiv")%></option>
                    <option value="3"><%=bundle.getString("delivery.Kharkiv")%></option>
                    <option value="4"><%=bundle.getString("delivery.Lviv")%></option>
                    <option value="5"><%=bundle.getString("delivery.Odessa")%></option>
                </select>
            </label>
        </li>
        <li> <%=bundle.getString("ui.to")%>:</li>
        <li style="margin-left: 3px">
            <label>
                <select name="to">
                    <option value="0"><%=bundle.getString("delivery.any")%></option>
                    <option value="1"><%=bundle.getString("delivery.Dnipro")%></option>
                    <option value="2"><%=bundle.getString("delivery.Kyiv")%></option>
                    <option value="3"><%=bundle.getString("delivery.Kharkiv")%></option>
                    <option value="4"><%=bundle.getString("delivery.Lviv")%></option>
                    <option value="5"><%=bundle.getString("delivery.Odessa")%></option>
                </select>
            </label>
        </li>
        <li> <%=bundle.getString("ui.status")%>:</li>
        <li style="margin-left: 3px">
            <label>
                <select name="status">
                    <option value="any"><%=bundle.getString("status.any")%></option>
                    <option value="open"><%=bundle.getString("status.open")%></option>
                    <option value="paid"><%=bundle.getString("status.paid")%></option>
                    <option value="validated"><%=bundle.getString("status.validated")%></option>
                    <option value="delivered"><%=bundle.getString("status.delivered")%></option>
                    <option value="canceled"><%=bundle.getString("status.canceled")%></option>
                </select>
            </label>
        </li>
        <li> <%=bundle.getString("ui.type")%>:</li>
        <li style="margin-left: 3px">
            <label>
                <select name="type">
                    <option value="any"><%=bundle.getString("status.any")%></option>
                    <option value="regular"><%=bundle.getString("type.regular")%></option>
                    <option value="fragile"><%=bundle.getString("type.fragile")%></option>
                    <option value="insured"><%=bundle.getString("type.insured")%></option>
                </select>
            </label>
        </li>
        <li><input type="submit" value="<%=bundle.getString("ui.sort")%>"></li>
    </ul>
</form>

<table class="tab">
    <h2 style="text-align: center">
        <%=bundle.getString("ui.bids")%>:
    </h2>
    <tr>
        <th> <%=bundle.getString("ui.from")%></th>
        <th> <%=bundle.getString("ui.to")%></th>
        <th> <%=bundle.getString("ui.status")%></th>
        <th> <%=bundle.getString("ui.type")%></th>
        <th> <%=bundle.getString("ui.created_date")%></th>
        <th> <%=bundle.getString("ui.delivery_date")%></th>
        <th><%=bundle.getString("ui.total")%></th>
    </tr>
    <c:forEach items="${requestScope.bids}" var="bid">
        <tr>
            <td>${bid.fromDirection.city}</td>
            <td>${bid.toDirection.city}</td>
            <c:choose>
            <c:when test="${bid.status.ordinal == '0'}">
                <td><%=bundle.getString("status.open")%></td>
            </c:when>
            <c:when test="${bid.status.ordinal == '1'}">
                <td style="color: dodgerblue"><%=bundle.getString("status.validated")%></td>
            </c:when>
            <c:when test="${bid.status.ordinal == '2'}">
                <td style="color: orange"><%=bundle.getString("status.paid")%></td>
            </c:when>
            <c:when test="${bid.status.ordinal == '3'}">
                <td style="color: limegreen"><%=bundle.getString("status.delivered")%></td>
            </c:when>
            <c:when test="${bid.status.ordinal == '4'}">
                <td style="color: red"><%=bundle.getString("status.canceled")%></td>
            </c:when>
        </c:choose>
            <td>${bid.type}</td>
            <td>${bid.createdDate}</td>
            <td>${bid.deliveryDate}</td>
            <td>${bid.totalPrice}
                <c:if test="${bid.status.ordinal == '1'}">
                    <form action="bids_serv" method="post">
                        <input type="hidden" name="change_bid" value="${bid.id}">
                        <input type="submit" value="<%=bundle.getString("ui.pay")%>">
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>

<nav style="background: #696969">
    <ul>
        <c:if test="${requestScope.currentPage != 1}">
            <li>
                <a href="bids_serv?page=${requestScope.currentPage-1}"><%=bundle.getString("pagination.previous")%></a>
            </li>
        </c:if>

        <c:forEach begin="1" end="${requestScope.pagesCount}" var="i">
            <c:choose>
                <c:when test="${requestScope.currentPage eq i}">
                    <li class="selected">
                        <a>${i}</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="bids_serv?page=${i}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${requestScope.currentPage lt requestScope.pagesCount}">
            <li>
                <a href="bids_serv?page=${requestScope.currentPage+1}"><%=bundle.getString("pagination.next")%></a>
            </li>
        </c:if>
    </ul>
</nav>

</body>
</html>
