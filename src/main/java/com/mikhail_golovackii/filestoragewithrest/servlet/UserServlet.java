
package com.mikhail_golovackii.filestoragewithrest.servlet;

import com.google.gson.Gson;
import com.mikhail_golovackii.filestoragewithrest.model.Event;
import com.mikhail_golovackii.filestoragewithrest.model.User;
import com.mikhail_golovackii.filestoragewithrest.service.EventService;
import com.mikhail_golovackii.filestoragewithrest.service.UserService;
import com.mikhail_golovackii.filestoragewithrest.service.impl.EventServiceImpl;
import com.mikhail_golovackii.filestoragewithrest.service.impl.UserServiceImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "api/users/*")
public class UserServlet extends HttpServlet{

    private UserService userService;
    private EventService eventService;
    
    public void init() {
        this.userService = new UserServiceImpl();
        this.eventService = new EventServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        
        String pathInfo = req.getPathInfo();

        if (pathInfo == null) {
            try (PrintWriter writer = resp.getWriter()) {
                userService.getAllElements().forEach(elem -> writer.write(new Gson().toJson(elem)));
            }
        } else {
            String[] parts = pathInfo.split("/");
            Long userId = Long.parseLong(parts[1]);
            
            try (PrintWriter writer = resp.getWriter()) {
                User user = userService.getElementById(userId);
                writer.write(new Gson().toJson(user));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        User newUser = gson.fromJson(reader, User.class);
        
        if (newUser != null) {
            userService.saveElement(newUser);
            
            eventService.saveElement(new Event("User saved: " + newUser.getName() + "; date: " + LocalDateTime.now().toString()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        User newUser = gson.fromJson(reader, User.class);
        
        User user = userService.getElementById(newUser.getId());
        
        if (user != null && newUser != null) {
            userService.updateElement(newUser);
            
            eventService.saveElement(new Event("User updated: " + newUser.getName() + "; date: " + LocalDateTime.now().toString()));
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String[] params = pathInfo.split("/");
        Long userId = Long.parseLong(params[1]);

        User user = userService.getElementById(userId);
        
        if (user != null) {
            userService.deleteElement(user);
            
            eventService.saveElement(new Event("User deleted: " + user.getName() + "; date: " + LocalDateTime.now().toString()));
        }
    }
}
