package org.event.manage.eventmanage.controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.event.manage.eventmanage.dto.RegisterRequest;
import org.event.manage.eventmanage.exception.UserAlreadyExist;
import org.event.manage.eventmanage.service.AuthService;
import org.event.manage.eventmanage.service.impl.AuthServiceImpl;
import org.event.manage.eventmanage.util.Response;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(value = "/register")
public class RegisterServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() throws ServletException {
        authService = new AuthServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        Response response = new Response();

        try {
            BufferedReader bufferedReader = req.getReader();
            RegisterRequest registerRequest = gson.fromJson(bufferedReader, RegisterRequest.class);

            System.out.println(registerRequest);

            boolean isRegistered = authService.register(registerRequest);

            if (isRegistered) {
                response.setCode(HttpServletResponse.SC_CREATED);
                response.setMessage("User registered successfully...");
                response.setData(null);
            }
        } catch (UserAlreadyExist e) {
            response.setCode(HttpServletResponse.SC_CONFLICT);
            response.setMessage(e.getMessage());
            response.setData(null);
        } catch (Exception e) {
            response.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Server error during registration");
            response.setData(null);
        }
        String jsonResponse = gson.toJson(response);
        resp.getWriter().write(jsonResponse);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("GET not allowed. Use POST for registration.");
    }
}