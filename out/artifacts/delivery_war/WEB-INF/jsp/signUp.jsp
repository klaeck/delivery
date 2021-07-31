<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>SignUp</title>
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
    request.getSession().setAttribute("prevURL", "signup");
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
<form action="signup_ver" method="post" style="align: center">
    <h1><%=bundle.getString("signup.submit")%></h1>
    <hr>

    <p style="color: red">
        <%
            String phoneError = request.getParameter("phe");
            if (phoneError == null) {
                phoneError = "";
            } else if (phoneError.equals("ipn")) {
                phoneError = "Invalid phone number";
            } else if (phoneError.equals("pae")) {
                phoneError = "This phone number is already registered!";
            }

            System.out.println(phoneError);
        %>
        <%= phoneError %>
    </p>
    <p>
        <label>
            <input type="tel" placeholder="<%=bundle.getString("signup.phone")%>" name="phone" required>
        </label>
    </p>

    <p style="color: red">
        <%
            String nameError = request.getParameter("ne");
            if (nameError == null) {
                nameError = "";
            } else {
                nameError = "Invalid name";
            }
        %>
        <%= nameError %>
    </p>
    <p>
        <label>
            <input type="text" placeholder="<%=bundle.getString("signup.first_name")%>" name="fname" required>
        </label>
        <label>
            <input type="text" placeholder="<%=bundle.getString("signup.last_name")%>" name="lname" required>
        </label>
    </p>

    <p style="color: red">
        <%
            String passwordError = request.getParameter("pe");
            if (passwordError == null) {
                passwordError = "";
            } else {
                passwordError = "Password must be 8+ symbols long, contain only latin characters symbols [.,!#$%^*()_]";
            }
        %>
        <%= passwordError %>
    </p>
    <p>
        <label>
            <input type="password" placeholder="<%=bundle.getString("signup.password")%>" name="password" required>
        </label>
    </p>

    <p style="color: red">
        <%
            String rPasswordError = request.getParameter("rpe");
            if (rPasswordError == null) {
                rPasswordError = "";
            } else {
                rPasswordError = "Passwords dont match";
            }
        %>
        <%= rPasswordError %>
    </p>
    <p>
        <label>
            <input type="password" placeholder="<%=bundle.getString("signup.r_password")%>" name="rpassword" required>
        </label>
    </p>

    <button type="submit"><%=bundle.getString("signup.submit")%></button>

    <hr>
    <p><%=bundle.getString("signup.account")%> <a href=login><%=bundle.getString("login.submit")%></a></p>
</form>
</body>
</html>
