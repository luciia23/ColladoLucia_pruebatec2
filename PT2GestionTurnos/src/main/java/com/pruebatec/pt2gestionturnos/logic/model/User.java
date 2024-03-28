package com.pruebatec.pt2gestionturnos.logic.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import org.mindrot.jbcrypt.BCrypt;

@Entity(name = "Usuario")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable=false)
    private String name;
    @Column(name = "contrasenia", nullable=false)
    private String passwordHash;
    @Column(name = "rol", nullable=false)
    private String role;

    @OneToOne
    @JoinColumn(name = "ciudadano_id")
    private Citizen citizen;

    public User() {
    }

    public User(Long id, String name, String passwordHash, String role, Citizen citizen) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
        this.role = role;
        this.citizen = citizen;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean validatePassword(String password) {
        return BCrypt.checkpw(password, this.passwordHash);
    }

}
