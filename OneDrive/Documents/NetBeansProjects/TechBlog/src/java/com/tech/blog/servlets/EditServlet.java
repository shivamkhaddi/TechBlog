
package com.tech.blog.servlets;

import com.tech.blog.dao.UserDao;
import com.tech.blog.entities.Message;
import com.tech.blog.entities.User;
import com.tech.blog.helper.ConnectionProvider;
import com.tech.blog.helper.Helper;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig
public class EditServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String userEmail = request.getParameter("user_email");
            String userName = request.getParameter("user_name");
            String userPassword = request.getParameter("user_password");
            String userAbout = request.getParameter("user_about");

            Part part = request.getPart("image");
            String imageName = "";
            if (part != null && part.getSize() > 0) {
                imageName = part.getSubmittedFileName();
                // Save the file or process it as needed
                // Example: part.write("/path/to/save/" + imageName);
            }
            
            //get user from session
            
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("currentUser");
            if (user == null) {
                response.sendRedirect("login.jsp"); // Redirect to login page if session expired
                return;
            }

            user.setEmail(userEmail);
            user.setName(userName);
            user.setPassword(userPassword);
            user.setAbout(userAbout);
            String oldFile = user.getProfile();
            user.setProfile(imageName);

            //update database
            UserDao userDao = new UserDao(ConnectionProvider.getConnection());
            boolean ans = userDao.updateUser(user);

            if (ans) {
                out.println("updated to db"); 
                
                String path = request.getRealPath("/")+"images"+File.separator + user.getProfile();
                //delete file
                String pathOldFile = request.getRealPath("/")+"images"+File.separator + oldFile;
                if(!oldFile.equals("default.png")){
                Helper.deleteFile(pathOldFile);
                }
                //save file
                    if (Helper.save(part.getInputStream(), path)) {
                    Message msg = new Message("profile details updated...", "success", "alert-success");
                    session.setAttribute("msg", msg);
                }
                
            } else {
                out.println("Failed to update user."); // Example error message
                 Message msg = new Message("Something went wrong ! try again","error","alert-danger");
                
                session.setAttribute("msg", msg);
            }
            
             response.sendRedirect("profile.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "EditServlet";
    }
}
