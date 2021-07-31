<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Login</title>
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
    request.getSession().setAttribute("prevURL", "login");
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
<form action="login_ver" method="post">
    <h1><%=bundle.getString("ui.welcome")%>!</h1>
    <hr>
    <p style="color: red">
        <%
            String loginError = request.getParameter("le");
            if (loginError == null) {
                loginError = "";
            } else {
                loginError = "Invalid phone number or password";
            }
        %>
        <%=loginError%>
    </p>
    <p>
        <label>
            <input type="tel" placeholder="<%=bundle.getString("login.phone")%>" name="phone" required>
        </label>
    </p>
    <p>
        <label>
            <input type="password" placeholder="<%=bundle.getString("login.password")%>" name="password" required>
        </label>
    </p>

    <button type="submit"><%=bundle.getString("login.submit")%></button>
    <hr>
    <p><%=bundle.getString("login.account")%> <a href=signup><%=bundle.getString("signup.submit")%>.</a></p>
</form>
</body>
</html>
