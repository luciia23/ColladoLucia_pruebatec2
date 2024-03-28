package com.pruebatec.pt2gestionturnos.logic.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name = "Ciudadano")
@NamedQueries({
    @NamedQuery(name = "Citizen.findByDni", query = "SELECT c FROM Ciudadano c WHERE c.dni = :dni"),
    @NamedQuery(name = "Citizen.findBySurname", query = "SELECT c FROM Ciudadano c WHERE c.surname = :surname AND c.user.role = 'Basic'")
})
public class Citizen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String name;
    @Column(name = "apellido", nullable = false)
    private String surname;
    @Column(name = "dni", nullable = false, unique = true)
    private String dni;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "citizen")
    private List<Turn> turnsList;

    public Citizen() {
    }

    public Citizen(Long id, String name, String surname, String dni, User user, List<Turn> turnsList) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.user = user;
        this.turnsList = turnsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDni() {
        return dni;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public List<Turn> getTurnsList() {
        return turnsList;
    }

    public void setTurnsList(List<Turn> turnsList) {
        this.turnsList = turnsList;
    }

}
