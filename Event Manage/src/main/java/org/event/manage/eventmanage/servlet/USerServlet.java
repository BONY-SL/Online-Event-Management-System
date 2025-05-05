package org.event.manage.eventmanage.servlet;

//import jakarta.annotation.security.RolesAllowed;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.event.manage.eventmanage.dto.EventDTO;
import org.event.manage.eventmanage.dto.MyEventDTO;
import org.event.manage.eventmanage.dto.UserBookEventDTO;
import org.event.manage.eventmanage.exception.EventNotFound;
import org.event.manage.eventmanage.exception.InsufficientTicketsException;
import org.event.manage.eventmanage.exception.UserNotFound;
import org.event.manage.eventmanage.service.AdminService;
import org.event.manage.eventmanage.service.UserService;
import org.event.manage.eventmanage.service.impl.AdminServiceImpl;
import org.event.manage.eventmanage.service.impl.UserServiceImpl;
import org.event.manage.eventmanage.util.LocalDateAdapter;
import org.event.manage.eventmanage.util.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(value = "/user/*")
//@RolesAllowed("USER")
public class USerServlet extends HttpServlet {

    private AdminService adminService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        adminService = new AdminServiceImpl();
        userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        Response response = new Response();

        try {
            BufferedReader reader = req.getReader();
            UserBookEventDTO userBookEventDTO = gson.fromJson(reader, UserBookEventDTO.class);

            if(userBookEventDTO.getAction().equals("book-event")){
                boolean isBookEvent = userService.eventBook(userBookEventDTO);
                if (isBookEvent) {
                    response.setCode(HttpServletResponse.SC_CREATED);
                    response.setMessage("Book Event Successfully");
                }
            }else {
                response.setCode(HttpServletResponse.SC_BAD_REQUEST);
                response.setMessage("Unknown Action");
                resp.getWriter().write(gson.toJson(response));
            }

        } catch (InsufficientTicketsException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setCode(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage(e.getMessage());
        }
        catch (UserNotFound e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCode(HttpServletResponse.SC_NOT_FOUND);
            response.setMessage(e.getMessage());
        }
        catch (EventNotFound e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCode(HttpServletResponse.SC_UNAUTHORIZED);
            response.setMessage(e.getMessage());
        }
        catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Internal server error: " + e.getMessage());
        }

        resp.getWriter().write(gson.toJson(response));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String getAction = req.getParameter("action");
        String getEmail = req.getParameter("email");

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

            } else if ("get-my-events".equals(getAction)) {
                List<MyEventDTO> myEventDTOS = userService.getMyAllEvents(getEmail);
                System.out.println(myEventDTOS);
                response.setCode(HttpServletResponse.SC_OK);
                response.setMessage("OK");
                response.setData(myEventDTOS);
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
