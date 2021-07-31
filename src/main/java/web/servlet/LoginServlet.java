package web.servlet;

import entity.User;
import enums.Role;
import exceptions.ErrorPageException;
import web.DAO.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login_ver")
public class LoginServlet extends HttpServlet {
    UserDAO userDAO = UserDAO.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (userDAO.validateUser(request.getParameter("phone"), request.getParameter("password"))) {

                User user;
                user = userDAO.getUserByPhone(request.getParameter("phone"));

                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                if (user.getRole() == Role.user) {
                    request.getRequestDispatcher("/account").forward(request, response);
                } else if (user.getRole() == Role.manager) {
                    request.getRequestDispatcher("/manager_serv?get=1").forward(request, response);
                }
            } else {
                response.sendRedirect("/delivery_war/login?le=e");
            }
        } catch (ErrorPageException | ServletException e) {
            response.sendRedirect("/delivery_war/error");
        }
    }
}

