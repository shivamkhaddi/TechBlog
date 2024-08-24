
package com.tech.blog.servlets;

import com.tech.blog.dao.PostDao;
import com.tech.blog.entities.Post;
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
public class AddPostServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Retrieve form data
            int cid = Integer.parseInt(request.getParameter("cid"));
            String pTitle = request.getParameter("pTitle");
            String pContent = request.getParameter("pContent");
            String pCode = request.getParameter("pCode");
            
            // Retrieve file part
            Part part = request.getPart("pic");
            
            // Retrieve current user's ID from session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("currentUser");
            int userId = user.getId();
            
            // Output for testing
           // out.println("Your title is: " + pTitle);
            //out.println("File name: " + part.getSubmittedFileName());

            // Create Post object
            Post p = new Post(pTitle, pContent, pCode, part.getSubmittedFileName(), null, cid, userId);
            
            // Save Post using DAO
            PostDao dao = new PostDao(ConnectionProvider.getConnection());
            if (dao.savePost(p)) {
                // Save file to server
                String path = request.getServletContext().getRealPath("/") + "blog_pics" + File.separator + part.getSubmittedFileName();
                Helper.save(part.getInputStream(), path);  // Helper class method to save InputStream to file
                out.println("Done");
            } else {
                out.println("Error");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately
            e.printStackTrace();
            // Return an error message if necessary
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred while processing the request.");
        }
    }

    // Handle HTTP GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // Handle HTTP POST requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding a new post";
    }
}
