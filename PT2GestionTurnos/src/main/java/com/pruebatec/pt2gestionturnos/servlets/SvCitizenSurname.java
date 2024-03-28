package com.pruebatec.pt2gestionturnos.servlets;

import com.pruebatec.pt2gestionturnos.logic.Controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SvCitizenSurname", urlPatterns = {"/SvCitizenSurname"})
public class SvCitizenSurname extends HttpServlet {

    Controller controller = new Controller();

    /**
     * Busca una lista de ciudadanos que coincidan con el apellido
     * proporcionado.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String surname = request.getParameter("searchSurname");
        request.setAttribute("searchResults", controller.findCitizensSurname(surname));
        request.getRequestDispatcher("/SvProcedure").forward(request, response);
    }

}
