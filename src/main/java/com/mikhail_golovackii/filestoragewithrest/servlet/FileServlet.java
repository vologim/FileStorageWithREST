package com.mikhail_golovackii.filestoragewithrest.servlet;

import com.google.gson.Gson;
import com.mikhail_golovackii.filestoragewithrest.model.Event;
import com.mikhail_golovackii.filestoragewithrest.model.User;
import com.mikhail_golovackii.filestoragewithrest.service.EventService;
import com.mikhail_golovackii.filestoragewithrest.service.UserService;
import com.mikhail_golovackii.filestoragewithrest.service.impl.EventServiceImpl;
import com.mikhail_golovackii.filestoragewithrest.service.impl.UserServiceImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(urlPatterns = "/files/*")
public class FileServlet extends HttpServlet {

    private EventService eventService;
    private UserService userService;
    private String filePath;
    private final int BYFFER_SIZE = 1024;

    @Override
    public void init() {
        eventService = new EventServiceImpl();
        userService = new UserServiceImpl();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties property = new Properties();
        try ( InputStream is = loader.getResourceAsStream("application.properties")) {
            property.load(is);
            filePath = property.getProperty("filePath");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/octet-stream");

        String pathInfo = req.getPathInfo();

        if (pathInfo == null) {
            try (PrintWriter writer = resp.getWriter()) {
                eventService.getAllElements().forEach(elem -> writer.write(new Gson().toJson(elem)));
            }
        } else {
            String[] parts = pathInfo.split("/");
            Long eventId = Long.parseLong(parts[1]);
            Event event = eventService.getElementById(eventId);
            
            if (event == null) {
                return;
            }
            
            String filePath = event.getFilePath() + event.getFileName();
            
            try (InputStream in = new FileInputStream(filePath);
                OutputStream out = resp.getOutputStream();) {
                byte[] buffer = new byte[BYFFER_SIZE];

                int numBytesRead;

                while ((numBytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, numBytesRead);
                }

                in.close();
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        String[] params = pathInfo.split("/");
        Long userId = Long.parseLong(params[1]);
        User user = userService.getElementById(userId);

        if (user == null) {
            return;
        }

        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
            for (FileItem item : items) {
                File file = new File(filePath, item.getName());
                item.write(file);

                Event event = new Event(item.getName(), filePath);
                eventService.saveElement(event);

                user.addEvent(event);
                userService.updateElement(user);
            }

        } catch (FileUploadException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        String[] params = pathInfo.split("/");
        Long userId = Long.parseLong(params[1]);
        User user = userService.getElementById(userId);

        if (user == null) {
            return;
        }

        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
            for (FileItem item : items) {
                File file = new File(filePath, item.getName());
                item.write(file);

                Event event = eventService.getEventByFileName(item.getName());

                event.setDateLastModifiedFile(LocalDateTime.now());
                eventService.updateElement(event);

                user.getEvents().set(user.getEvents().indexOf(event), event);
                userService.updateElement(user);
            }
        } catch (FileUploadException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String pathInfo = req.getPathInfo();
        String[] params = pathInfo.split("/");
        Long eventId = Long.parseLong(params[1]);

        Event event = eventService.getElementById(eventId);
        
        if (event != null) {
            eventService.deleteElement(event);
        }
    }
}
