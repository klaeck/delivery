package web.servlet;

import web.DAO.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup_ver")
public class SignUpServlet extends HttpServlet {
    private final UserDAO userDAO = UserDAO.getInstance();
    private String phoneError;
    private String passwordError;
    private String rPasswordError;
    private String nameError;

    private String fname;
    private String lname;
    private String phone;
    private String password;

    boolean verifyPhone(String phone) {
        String regex = "\\+380(93|95|97|99|50|73|63|66|67|68)(\\d){7}";
        return phone.matches(regex);
    }

    boolean verifyName(String name) {
        String regex = "(([A-Z][a-z]+)|([А-Я][а-я]+))";
        return name.matches(regex);
    }

    boolean verifyPassword(String password) {
        String regex = "[A-Za-z0-9.,!#$%^*()_]{8,}";

        return password.matches(regex);
    }

    boolean isPasswordsEqual(String pass, String rpass) {
        return pass.equals(rpass);
    }

    boolean verifyData(HttpServletRequest request) {
        phoneError = "";
        nameError = "";
        passwordError = "";
        rPasswordError = "";

        phone = request.getParameter("phone");
        if (!verifyPhone(phone)) {
            phoneError += "ipn";
        } else if (userDAO.isExists(request.getParameter("phone"))) {
            phoneError += "pae";
        }

        fname = request.getParameter("fname");
        lname = request.getParameter("lname");
        if (!verifyName(fname) || !verifyName(lname)) {
            nameError += "e";
        }

        password = request.getParameter("password");
        if (!verifyPassword(password)) {
            passwordError += "e";
        }

        if (!isPasswordsEqual(password, request.getParameter("rpassword"))) {
            rPasswordError += "e";
        }

        return phoneError.equals("") && passwordError.equals("") && nameError.equals("");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (verifyData(req)) {
            userDAO.insertUser(fname, lname, phone, password);

            resp.sendRedirect("/delivery_war/login");
        } else {
            resp.sendRedirect("/delivery_war/signup" + generateErrorParams());
        }
    }

    private String generateErrorParams() {
        StringBuilder sb = new StringBuilder("?");

        if (!phoneError.isEmpty()) {
            sb.append("phe=").append(phoneError);
        }

        if (!nameError.isEmpty()) {
            if (!sb.toString().equals("?")) {
                sb.append("&");
            }

            sb.append("ne=").append(nameError);
        }

        if (!passwordError.isEmpty()) {
            if (!sb.toString().equals("?")) {
                sb.append("&");
            }

            sb.append("pe=").append(passwordError);
        }

        if (!rPasswordError.isEmpty()) {
            if (!sb.toString().equals("?")) {
                sb.append("&");
            }

            sb.append("rpe=").append(rPasswordError);
        }

        return sb.toString();
    }
}
