package org.event.manage.eventmanage.servlet;

//import jakarta.annotation.security.RolesAllowed;
//import jakarta.annotation.security.RolesAllowed;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.event.manage.eventmanage.dto.EventDTO;
import org.event.manage.eventmanage.service.AdminService;
import org.event.manage.eventmanage.service.impl.AdminServiceImpl;
import org.event.manage.eventmanage.util.LocalDateAdapter;
import org.event.manage.eventmanage.util.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(value = "/user/*")
//@RolesAllowed("USER")
public class USerServlet extends HttpServlet {

    private AdminService adminService;

    @Override
    public void init() throws ServletException {
        adminService = new AdminServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Welcome, User!");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String getAction = req.getParameter("action");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        Response response = new Response();

        try {
            if ("get-all".equals(getAction)) {

                List<EventDTO> eventDTOList = adminService.getAllEvents();
                System.out.println(eventDTOList);
                response.setCode(HttpServletResponse.SC_OK);
                response.setMessage("OK");
                response.setData(eventDTOList);

            } else {
                response.setCode(HttpServletResponse.SC_BAD_REQUEST);
                response.setMessage("Unknown Action");
                resp.getWriter().write(gson.toJson(response));
            }

        } catch (Exception e) {
            response.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Server Error: " + e.getMessage());
            resp.getWriter().write(gson.toJson(response));
        }
        resp.getWriter().write(gson.toJson(response));
    }
}
