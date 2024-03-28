package com.pruebatec.pt2gestionturnos.logic;

import com.pruebatec.pt2gestionturnos.logic.model.Citizen;
import com.pruebatec.pt2gestionturnos.logic.model.Procedure;
import com.pruebatec.pt2gestionturnos.logic.model.Turn;
import com.pruebatec.pt2gestionturnos.logic.model.User;
import com.pruebatec.pt2gestionturnos.persistence.PersistenceController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controller {
    PersistenceController pController = new PersistenceController();

    public void createCitizen(Citizen citizen) {
       pController.createCitizen(citizen);
    }

    public void createUser(User user) {
       pController.createUser(user);
    }
    
    public List<User> getUsers(){
        return pController.getUsers();
    }

    public boolean checkValidUser(String dni, String paswd) {
        List<User> users = getUsers();
        return users.stream().
                filter(user -> user.getCitizen().getDni().equals(dni))
                .anyMatch(user -> user.validatePassword(paswd));
    }

    public Citizen getCitizenByDni(String dni) {
        return pController.getCitizenByDni(dni);
    }

    public void createTurn(Turn turn) {
        pController.createTurn(turn);
    }

    public void createProcedure(Procedure procedure) {
       pController.createProcedure(procedure);
    }

    public List<Turn> getAllCitizenTurns(Citizen citizen) {
        return pController.getAllCitizenTurns(citizen);
    }

    public List<Procedure> findAllProcedures() {
       return  pController.findAllProcedures();
    }

    public Procedure findProcedure(Long idProcedure) {
        return pController.findProcedure(idProcedure);
    }

    public List<Citizen> findCitizensSurname(String surname) {
        return pController.findCitizensSurname(surname);
    }

    public List<Turn> getAllTurns() {
        return pController.findAllTurns();
    }
    
        /**
     * Método que dada una fecha pasada como string la formatea según el orden
     * indicado y la devuelve como un tipo LocalDate
     *
     * @param fechaString
     * @return
     */
    public LocalDate dateFormatter(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateFormatted = null;
        if (!date.isEmpty()) {
            dateFormatted = LocalDate.parse(date, formatter);
        }
        return dateFormatted;
    }

    public List<Turn> getTurnsByDate(LocalDate dateFormatted) {
        return pController.findAllTurnsByDate(dateFormatted);
    }

    public List<Turn> getTurnsByDateNCondition(LocalDate dateFormatted, boolean stateFilter) {
        return pController.findTurnsByDateNCondition(dateFormatted, stateFilter);
    }

    public List<Turn> getTurnsCondition(boolean stateFilter) {
        return pController.findTurnsByCondition(stateFilter);
    }

    public void updateTurn(Long idTurn, boolean state) {
        List<Turn> list = getAllTurns();
        System.out.println("boolean:"+ state);
        Turn turn = list.stream().filter(t -> t.getId().equals(idTurn)).findFirst().orElse(null);
        turn.setCondition(state);
        pController.updateTurn(turn);
    }

}
