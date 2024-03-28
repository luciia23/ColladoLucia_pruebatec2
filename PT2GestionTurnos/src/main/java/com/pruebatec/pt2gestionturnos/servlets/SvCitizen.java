package com.pruebatec.pt2gestionturnos.servlets;

import com.pruebatec.pt2gestionturnos.logic.Controller;
import com.pruebatec.pt2gestionturnos.logic.model.Citizen;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SvCitizen", urlPatterns = {"/SvCitizen"})
public class SvCitizen extends HttpServlet {

    Controller controller = new Controller();

    /**
     * Obtiene todos los turnos asociados al ciudadano que ha iniciado sesión.
     *
     * Si la sesión no existe le redirije al login de vuelta
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Citizen citizen = (Citizen) session.getAttribute("citizenSession");
            if (citizen != null) {
                request.setAttribute("turnsList", controller.getAllCitizenTurns(citizen));
                request.getRequestDispatcher("SvProcedure").forward(request, response);
            } else {
                response.sendRedirect("login.jsp");
            }
        } else {
            response.sendRedirect("login.jsp");
        }

    }

}
