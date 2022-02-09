
package com.mikhail_golovackii.filestoragewithrest.servlet;

import com.google.gson.Gson;
import com.mikhail_golovackii.filestoragewithrest.model.User;
import com.mikhail_golovackii.filestoragewithrest.service.UserService;
import com.mikhail_golovackii.filestoragewithrest.service.impl.UserServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/users")
public class UserServlet extends HttpServlet{

    private UserService service;
    
    public void init() {
        service = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        String[] idValues = req.getParameterValues("id");
        
        if (idValues == null) {
            try (PrintWriter writer = resp.getWriter()) {
                service.getAllElements().forEach(elem -> writer.write(new Gson().toJson(elem)));
            }
        }
        else {
            try (PrintWriter writer = resp.getWriter()) {
                
                Long userId;
                User user;
                
                for (String id : idValues) {
                    userId = Long.parseLong(id);
                    user = service.getElementById(userId);
                    writer.write(new Gson().toJson(user));
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("name");
        
        User user = new User(userName);
        
        service.saveElement(user);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        String userName = req.getParameter("name");
        
        User user = service.getElementById(Long.parseLong(userId));
        
        if (user != null) {
            user.setName(userName);
            service.updateElement(user);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        User user = service.getElementById(Long.parseLong(userId));
        
        service.deleteElement(user);
    }
}
