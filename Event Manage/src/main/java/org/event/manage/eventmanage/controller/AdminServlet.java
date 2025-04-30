package org.event.manage.eventmanage.controller;
import com.google.gson.Gson;
//import jakarta.annotation.security.RolesAllowed;

import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.event.manage.eventmanage.dto.EventDTO;
import org.event.manage.eventmanage.service.AdminService;
import org.event.manage.eventmanage.service.impl.AdminServiceImpl;
import org.event.manage.eventmanage.util.LocalDateAdapter;
import org.event.manage.eventmanage.util.Response;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;


@WebServlet(value = "/admin/*")
//@RolesAllowed("ADMIN")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 15
)
public class AdminServlet extends HttpServlet {

    private AdminService adminService;

    @Override
    public void init() throws ServletException {
        adminService = new AdminServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        Response response = new Response();

        try {
            String action = req.getParameter("action");
            if ("add-event".equals(action)) {
                handleAddEvent(req, resp);
            } else {
                response.setCode(HttpServletResponse.SC_BAD_REQUEST);
                response.setMessage("Unknown Action");
                resp.getWriter().write(gson.toJson(response));
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Server Error: " + e.getMessage());
            resp.getWriter().write(gson.toJson(response));
        }
    }

    private void handleAddEvent(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();


        String name = req.getParameter("name");
        String dateStr = req.getParameter("date");
        String venue = req.getParameter("venue");
        String description = req.getParameter("description");
        int capacity = Integer.parseInt(req.getParameter("capacity"));

        LocalDate date = LocalDate.parse(dateStr);

        Part brochurePart = req.getPart("brochureFilePath");
        String fileName = Paths.get(brochurePart.getSubmittedFileName()).getFileName().toString();

        String uploadPath = getServletContext().getRealPath("/") + "uploads";
        System.out.println(uploadPath);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String savedFilePath = uploadPath + File.separator + fileName;
        try (InputStream fileContent = brochurePart.getInputStream();
             FileOutputStream fos = new FileOutputStream(savedFilePath)) {
            fileContent.transferTo(fos);
        }

        EventDTO eventDTO = EventDTO.builder()
                .name(name)
                .date(date)
                .venue(venue)
                .description(description)
                .capacity(capacity)
                .brochureFilePath("uploads/" + fileName)
                .build();

        EventDTO savedEvent = adminService.addNewEvent(eventDTO);
        Response response = new Response(201, "Event Created", savedEvent);
        resp.getWriter().write(gson.toJson(response));
    }
}
