package com.pruebatec.pt2gestionturnos.servlets;

import com.pruebatec.pt2gestionturnos.logic.Controller;
import com.pruebatec.pt2gestionturnos.logic.model.Citizen;
import com.pruebatec.pt2gestionturnos.logic.model.Procedure;
import com.pruebatec.pt2gestionturnos.logic.model.Turn;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//This servlet takes care of the creation of a new turn and to get the list of turns
@WebServlet(name = "SvTurnAdmin", urlPatterns = {"/SvTurnAdmin"})
public class SvTurnAdmin extends HttpServlet {

    Controller controller = new Controller();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Turn> turnsList;
        String dateParam = request.getParameter("dateFilter");
        String stateFilter = request.getParameter("state");
        boolean condition = !"En Espera".equalsIgnoreCase(stateFilter); // Simplified condition based on stateFilter

        // Convert the procDateParam to a LocalDate object
        LocalDate dateFormatted = null;
        if (dateParam != null) {
            dateFormatted = controller.dateFormatter(dateParam);
        }

        // Assuming controller methods to get filtered turns based on the conditions
        if (dateFormatted != null) {
            if (stateFilter != null) {
                turnsList = controller.getTurnsByDateNCondition(dateFormatted, condition);
            } else {
                turnsList = controller.getTurnsByDate(dateFormatted);
            }
        } else {
            if (stateFilter != null) {
                turnsList = controller.getTurnsCondition(condition);
            } else {
                turnsList = controller.getAllTurns();
            }
        }

        request.setAttribute("results", turnsList);
        request.getRequestDispatcher("SvProcedure").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Citizen citizen = controller.getCitizenByDni(request.getParameter("searchResultsSelect"));
        LocalDate dateFormatted = controller.dateFormatter(request.getParameter("procDate"));

        Procedure procedure = controller.findProcedure(Long.valueOf(request.getParameter("procName")));

        Turn turn = new Turn();
        turn.setDate(dateFormatted);
        turn.setCitizen(citizen);
        turn.setProcedure(procedure);
        turn.setCondition(false);
        controller.createTurn(turn);
        response.sendRedirect("SvProcedure");
    }

}
