
package com.tech.blog.servlets;

import com.tech.blog.dao.LikeDao;
import com.tech.blog.helper.ConnectionProvider;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LikeServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String operation = request.getParameter("operation");
            int userId = Integer.parseInt(request.getParameter("userId"));
            int pid = Integer.parseInt(request.getParameter("pid"));
            
            LikeDao ldao = new LikeDao(ConnectionProvider.getConnection());
            
            if (operation.equals("like")) {
                boolean alreadyLiked = ldao.isLikedByUser(pid, userId);
                
                if (alreadyLiked) {
                    out.println("You have already liked the post.");
                } else {
                    boolean f = ldao.insertLike(pid, userId);
                    if (f) {
                        out.println("Liked successfully.");
                    } else {
                        out.println("Failed to like the post.");
                    }
                }
            }
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
        return "Short description";
    }
}
