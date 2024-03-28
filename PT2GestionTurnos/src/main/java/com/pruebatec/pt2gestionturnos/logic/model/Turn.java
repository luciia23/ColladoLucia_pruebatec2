package com.pruebatec.pt2gestionturnos.logic.model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity(name="Turno")
@NamedQueries({
    @NamedQuery(name = "Turn.findAllByCitizen", query = "SELECT t FROM Turno t WHERE t.citizen.dni = :dni"),
    @NamedQuery(name = "Turn.findAllByDate", query = "SELECT t FROM Turno t WHERE t.date = :date"),
    @NamedQuery(name = "Turn.findAllByDateNCondition", query = "SELECT t FROM Turno t WHERE t.date = :date AND t.condition = :condition"),
    @NamedQuery(name = "Turn.findAllByCondition", query = "SELECT t FROM Turno t WHERE t.condition = :condition"),
})
public class Turn implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha", nullable=false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "id_ciudadano")
    private Citizen citizen;
    @ManyToOne
    @JoinColumn(name = "id_tramite")
    private Procedure procedure;
    @Column(name = "estado", nullable=false)
    private boolean condition;

    public Turn() {
    }

    public Turn(Long id, Citizen citizen, Procedure procedure) {
        this.id = id;
        this.date = LocalDate.now();
        this.citizen = citizen;
        this.procedure = procedure;
        this.condition = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

}
