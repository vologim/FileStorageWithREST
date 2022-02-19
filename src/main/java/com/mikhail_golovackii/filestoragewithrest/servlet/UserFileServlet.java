package com.mikhail_golovackii.filestoragewithrest.servlet;

import com.google.gson.Gson;
import com.mikhail_golovackii.filestoragewithrest.model.Event;
import com.mikhail_golovackii.filestoragewithrest.model.User;
import com.mikhail_golovackii.filestoragewithrest.model.UserFile;
import com.mikhail_golovackii.filestoragewithrest.service.EventService;
import com.mikhail_golovackii.filestoragewithrest.service.UserFileService;
import com.mikhail_golovackii.filestoragewithrest.service.UserService;
import com.mikhail_golovackii.filestoragewithrest.service.impl.EventServiceImpl;
import com.mikhail_golovackii.filestoragewithrest.service.impl.UserFileServiceImpl;
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

@WebServlet(urlPatterns = "api/files/*")
public class UserFileServlet extends HttpServlet {

    private EventService eventService;
    private UserFileService userFileService;
    private UserService userService;
    private String filePath;
    private final int BYFFER_SIZE = 1024;

    @Override
    public void init() {
        this.eventService = new EventServiceImpl();
        this.userFileService = new UserFileServiceImpl();
        this.userService = new UserServiceImpl();

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

        Long userId = Long.parseLong(req.getHeader("userId"));
        User user = userService.getElementById(userId);
        
        if (user == null) {
            return;
        }
        
        resp.setContentType("application/octet-stream");

        String pathInfo = req.getPathInfo();

        if (pathInfo == null) {
            try (PrintWriter writer = resp.getWriter()) {
                user.getFiles().forEach(elem -> writer.write(new Gson().toJson(elem)));
                
                eventService.saveElement(new Event("User: " + user.getName() + " downloaded all files"
                + "; date: " + LocalDateTime.now().toString()));
            }
        } else {
            String[] parts = pathInfo.split("/");
            Long userFileId = Long.parseLong(parts[1]);
            UserFile userFile = userFileService.getElementById(userFileId);
            
            if (userFile == null) {
                return;
            }
            
            String filePath = userFile.getFilePath() + userFile.getFileName();
            
            try (InputStream in = new FileInputStream(filePath);
                OutputStream out = resp.getOutputStream();) {
                byte[] buffer = new byte[BYFFER_SIZE];

                int numBytesRead;

                while ((numBytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, numBytesRead);
                }

                eventService.saveElement(new Event("User: " + user.getName() + "; downloaded: " + userFile.getFileName() + 
                        "; file path: " + userFile.getFilePath() + "; date: " + LocalDateTime.now().toString()));
                
                in.close();
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long userId = Long.parseLong(req.getHeader("userId"));
        User user = userService.getElementById(userId);

        if (user == null) {
            return;
        }

        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
            for (FileItem item : items) {
                File file = new File(filePath, item.getName());
                item.write(file);

                UserFile userFile = new UserFile(item.getName(), filePath);
                userFileService.saveElement(userFile);

                user.addFile(userFile);
                userService.updateElement(user);
                
                eventService.saveElement(new Event("User: " + user.getName() + "; save file: " + userFile.getFileName()
                        + "; file path: " + userFile.getFilePath() + "; date: " + LocalDateTime.now().toString()));
            }

        } catch (FileUploadException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long userId = Long.parseLong(req.getHeader("userId"));
        User user = userService.getElementById(userId);

        if (user == null) {
            return;
        }

        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
            for (FileItem item : items) {
                File file = new File(filePath, item.getName());
                item.write(file);

                UserFile userFile = userFileService.getUserFileByFileName(item.getName());

                userFileService.updateElement(userFile);

                user.getFiles().set(user.getFiles().indexOf(userFile), userFile);
                userService.updateElement(user);
                
                eventService.saveElement(new Event("User: " + user.getName() + "; update file: " + userFile.getFileName()
                        + "; file path: " + userFile.getFilePath() + "; date: " + LocalDateTime.now().toString()));
            }
        } catch (FileUploadException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        Long userId = Long.parseLong(req.getHeader("userId"));
        User user = userService.getElementById(userId);
        
        if (user == null) {
            return;
        }
        
        String pathInfo = req.getPathInfo();
        String[] params = pathInfo.split("/");
        Long userFileId = Long.parseLong(params[1]);

        UserFile userFile = userFileService.getElementById(userFileId);
        
        if (user.getFiles().contains(userFile)) {
            userFileService.deleteElement(userFile);

            eventService.saveElement(new Event("User: " + user.getName() + "; deleted file: " + userFile.getFileName()
                    + "; file path: " + userFile.getFilePath() + "; date: " + LocalDateTime.now().toString()));
        }
    }
}
