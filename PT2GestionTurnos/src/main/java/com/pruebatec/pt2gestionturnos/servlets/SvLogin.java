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

@WebServlet(name = "SvLogin", urlPatterns = {"/SvLogin"})
public class SvLogin extends HttpServlet {

    Controller controller = new Controller();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dni = request.getParameter("dniUser");
        String paswd = request.getParameter("password");

        boolean valid = false;
        valid = controller.checkValidUser(dni, paswd);
        if (valid) {
            HttpSession session = request.getSession(true);
            Citizen citizen = controller.getCitizenByDni(dni);
            session.setAttribute("citizenName", citizen.getName());
            session.setAttribute("citizenSession", citizen);
            session.setAttribute("userRole", citizen.getUser().getRole());
            response.sendRedirect("index.jsp");
        } else {
            response.sendRedirect("loginError.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
