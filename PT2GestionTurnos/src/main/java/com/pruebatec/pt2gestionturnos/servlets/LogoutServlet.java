
package com.pruebatec.pt2gestionturnos.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpSession;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Invalidar la sesión
        HttpSession session = request.getSession(false); // Obtener la sesión actual
        if (session != null) {
            session.invalidate(); // Invalidar la sesión
        }

        // Redireccionar al usuario a la página de inicio de sesión
        response.sendRedirect("login.jsp");
    }
}