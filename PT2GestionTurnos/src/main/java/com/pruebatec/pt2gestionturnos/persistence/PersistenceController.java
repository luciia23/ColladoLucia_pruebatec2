package com.pruebatec.pt2gestionturnos.persistence;

import com.pruebatec.pt2gestionturnos.logic.model.Citizen;
import com.pruebatec.pt2gestionturnos.logic.model.Procedure;
import com.pruebatec.pt2gestionturnos.logic.model.Turn;
import com.pruebatec.pt2gestionturnos.logic.model.User;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenceController {
    CitizenJpaController citJPA = new CitizenJpaController();
    ProcedureJpaController procJPA = new ProcedureJpaController();
    TurnJpaController turnJPA = new TurnJpaController();
    UserJpaController usuJPA = new UserJpaController();

    public void createCitizen(Citizen citizen) {
        citJPA.create(citizen);
    }

    public void createUser(User user) {
        usuJPA.create(user);
    }
    
    public List<User> getUsers(){
        return usuJPA.findUserEntities();
    }

    public Citizen getCitizenByDni(String dni) {
        return citJPA.findCitizenByDni(dni);
    }

    public void createTurn(Turn turn) {
        turnJPA.create(turn);
    }

    public void createProcedure(Procedure procedure) {
        procJPA.create(procedure);
    }

    public List<Turn> getAllCitizenTurns(Citizen citizen) {
        return turnJPA.findTurnsCitizen(citizen);
    }

    public List<Procedure> findAllProcedures() {
        return procJPA.findProcedureEntities();
    }

    public Procedure findProcedure(Long idProcedure) {
        return procJPA.findProcedure(idProcedure);
    }

    public List<Citizen> findCitizensSurname(String surname) {
        return citJPA.findCitizensSurname(surname);
    }

    public List<Turn> findAllTurns() {
       return turnJPA.findTurnEntities();
    }

    public List<Turn> findAllTurnsByDate(LocalDate dateFormatted) {
       return turnJPA.findAllTurnsByDate(dateFormatted);
    }

    public List<Turn> findTurnsByDateNCondition(LocalDate dateFormatted, boolean stateFilter) {
       return turnJPA.findAllTurnsByDateNCondition(dateFormatted, stateFilter);
    }

    public List<Turn> findTurnsByCondition(boolean stateFilter) {
           return turnJPA.findAllTurnsByCondition(stateFilter);
    }

    public void updateTurn(Turn turn) {
        try {
            turnJPA.edit(turn);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
