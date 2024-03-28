package com.pruebatec.pt2gestionturnos.servlets;

import com.pruebatec.pt2gestionturnos.logic.Controller;
import com.pruebatec.pt2gestionturnos.logic.model.Citizen;
import com.pruebatec.pt2gestionturnos.logic.model.Procedure;
import com.pruebatec.pt2gestionturnos.logic.model.Turn;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SvTurn", urlPatterns = {"/SvTurn"})
public class SvTurn extends HttpServlet {

    Controller controller = new Controller();

    /*Crea un nuevo turno con el ciudadano que ha iniciado sesión.*/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            LocalDate dateFormatted = controller.dateFormatter(request.getParameter("procDate"));

            Procedure procedure = controller.findProcedure(Long.valueOf(request.getParameter("procName")));

            Turn turn = new Turn();
            turn.setDate(dateFormatted);

            Citizen citizen = (Citizen) session.getAttribute("citizenSession");
            if (citizen != null) {
                turn.setCitizen(citizen);
            } else {
                response.sendRedirect("login.jsp");
            }

            turn.setProcedure(procedure);
            turn.setCondition(false);

            request.setAttribute("registroCorrecto", "El turno se ha creado correctamente");

            controller.createTurn(turn);
            response.sendRedirect("SvProcedure");
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
