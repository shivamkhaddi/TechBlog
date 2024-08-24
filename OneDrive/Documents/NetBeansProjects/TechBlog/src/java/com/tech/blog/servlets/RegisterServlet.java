
package com.tech.blog.servlets;

import com.tech.blog.dao.UserDao;
import com.tech.blog.entities.User;
import com.tech.blog.helper.ConnectionProvider;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Fetch all form data
            String check = request.getParameter("check");
            if (check == null) {
                out.println("Checkbox not checked");
                return;  // Exit method if checkbox is not checked
            }

            String name = request.getParameter("user_name");
            String email = request.getParameter("user_email");
            String password = request.getParameter("user_password");
            String gender = request.getParameter("gender");
            String about = request.getParameter("about");

            // Validate required fields (you might want to do more robust validation)
            if (name == null || email == null || password == null || gender == null) {
                out.println("Required fields are missing");
                return;  // Exit method if required fields are missing
            }

            // Create User object
            User user = new User(name, email, password, gender, about);

            // Save user using UserDao
            UserDao dao = new UserDao(ConnectionProvider.getConnection());
            if (dao.saveUser(user)) {
                out.println("Registration successful");
            } else {
                out.println("Registration failed");
            }
        }
    }

  
}