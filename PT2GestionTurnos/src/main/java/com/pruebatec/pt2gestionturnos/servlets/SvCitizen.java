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

    //Gets all the turns that belong to the user logged in
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
//        request.setAttribute("errorMessage", "You need to log in to access this page.");
//request.getRequestDispatcher("error.jsp").forward(request, response);

    }

}
