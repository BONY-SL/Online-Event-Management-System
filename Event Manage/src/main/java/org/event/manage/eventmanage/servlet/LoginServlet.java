package org.event.manage.eventmanage.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.event.manage.eventmanage.dto.GetUserRole;
import org.event.manage.eventmanage.dto.LoginRequest;
import org.event.manage.eventmanage.exception.InvalidCredentials;
import org.event.manage.eventmanage.exception.UserNotFound;
import org.event.manage.eventmanage.service.AuthService;
import org.event.manage.eventmanage.service.impl.AuthServiceImpl;
import org.event.manage.eventmanage.util.Response;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        authService = new AuthServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        Response response = new Response();

        try {
            BufferedReader reader = req.getReader();
            LoginRequest loginRequest = gson.fromJson(reader, LoginRequest.class);

            if (loginRequest == null || loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
                throw new InvalidCredentials("Invalid login request");
            }

            GetUserRole getRole = authService.login(loginRequest);

            if (getRole != null) {

                getRole.setEmail(loginRequest.getEmail());
                response.setCode(HttpServletResponse.SC_OK);
                response.setMessage("Login successful");
                response.setData(getRole);
            }

        } catch (UserNotFound e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCode(HttpServletResponse.SC_NOT_FOUND);
            response.setMessage(e.getMessage());

        } catch (InvalidCredentials e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCode(HttpServletResponse.SC_UNAUTHORIZED);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Internal server error: " + e.getMessage());
        }

        resp.getWriter().write(gson.toJson(response));
    }
}
