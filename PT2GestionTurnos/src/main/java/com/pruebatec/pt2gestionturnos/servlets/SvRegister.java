package com.pruebatec.pt2gestionturnos.servlets;

import com.pruebatec.pt2gestionturnos.logic.Controller;
import com.pruebatec.pt2gestionturnos.logic.model.Citizen;
import com.pruebatec.pt2gestionturnos.logic.model.User;
import java.io.IOException;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SvRegister", urlPatterns = {"/SvRegister"})
public class SvRegister extends HttpServlet {

    Controller controller = new Controller();

    /*Registra un nuevo ciudadano y un usuario a la bbdd, siempre y cuando el DNI no existe previamente en la bbdd*/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String lastName = request.getParameter("lastName");
        String dni = request.getParameter("dni");
        String role = request.getParameter("role");
        String password = request.getParameter("password");

        Citizen citizen = new Citizen();
        citizen.setName(name);
        citizen.setSurname(lastName);
        citizen.setDni(dni);

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setRole(role);
        user.setCitizen(citizen);

        try {
            controller.createCitizen(citizen);
            controller.createUser(user);
            response.sendRedirect("login.jsp");
        } catch (PersistenceException e) {
            String errorMessage = "Ya hay un ciudadano con ese DNI.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }

    }

}
