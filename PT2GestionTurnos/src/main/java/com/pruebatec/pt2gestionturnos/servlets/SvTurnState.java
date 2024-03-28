package com.pruebatec.pt2gestionturnos.servlets;

import com.pruebatec.pt2gestionturnos.logic.Controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SvTurnState", urlPatterns = {"/SvTurnState"})
public class SvTurnState extends HttpServlet {

    Controller controller = new Controller();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idTurn = Long.valueOf(request.getParameter("turnId"));
        String state = request.getParameter("stateLink");

        if (state.equalsIgnoreCase("en espera")) {
            controller.updateTurn(idTurn, true);
        } else if (state.equalsIgnoreCase("atendido")){
            controller.updateTurn(idTurn, false);
        }
        response.sendRedirect("SvProcedure");
    }
}
