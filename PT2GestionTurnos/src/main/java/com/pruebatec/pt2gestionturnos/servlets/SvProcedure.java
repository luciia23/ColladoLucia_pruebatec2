package com.pruebatec.pt2gestionturnos.servlets;

import com.pruebatec.pt2gestionturnos.logic.Controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SvProcedure", urlPatterns = {"/SvProcedure"})
public class SvProcedure extends HttpServlet {

    Controller controller = new Controller();

    /* Obtiene todos los trámites y dirije al usuario a la página correspondiente 
 * para crear un nuevo turno, dependiendo de su rol de usuario.*/
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("procedures", controller.findAllProcedures());
        String role = (String) request.getSession().getAttribute("userRole");
        if (role != null && role.equalsIgnoreCase("admin")) {
            request.getRequestDispatcher("createTurnAdmin.jsp").forward(request, response);
        } else if (role != null && role.equalsIgnoreCase("basic")) {
            request.getRequestDispatcher("createTurn.jsp").forward(request, response);
        }
    }

}
