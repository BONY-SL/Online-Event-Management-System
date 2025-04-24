package org.event.manage.eventmanage.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.event.manage.eventmanage.dto.RegisterRequest;
import org.event.manage.eventmanage.service.impl.AuthServiceImpl;

import java.io.IOException;

@WebServlet(value = "/register")
public class RegisterServlet extends HttpServlet {

    private AuthServiceImpl authService;

    @Override
    public void init() throws ServletException {
        authService = new AuthServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String role = req.getParameter("role");

        RegisterRequest request = RegisterRequest.builder()
                .name(name)
                .email(email)
                .password(password)
                .phone(phone)
                .role(role)
                .build();

        System.out.println(request);

        boolean isRegistered = authService.register(request);

        if (isRegistered) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");

        } else {
            resp.getWriter().write("Registration failed.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("GET not allowed. Use POST for registration.");
    }
}
