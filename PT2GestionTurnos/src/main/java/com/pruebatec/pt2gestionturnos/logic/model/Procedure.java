package com.pruebatec.pt2gestionturnos.logic.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "Tramite")
public class Procedure implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "descripcion", nullable = false, unique = true)
    private String description;

    @OneToMany(mappedBy = "procedure")
    private List<Turn> turnsList;

    public Procedure() {
    }

    public Procedure(Long id, String description, List<Turn> turnsList) {
        this.id = id;
        this.description = description;
        this.turnsList = turnsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Turn> getTurnsList() {
        return turnsList;
    }

    public void setTurnsList(List<Turn> turnsList) {
        this.turnsList = turnsList;
    }

}
