package com.oceanview.controller;

import com.oceanview.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        if (userService.login(user, pass)) {

            HttpSession session = request.getSession();
            session.setAttribute("username", user);

            response.sendRedirect("dashboard.jsp");

        } else {

            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("login.jsp")
                    .forward(request, response);
        }
    }
}