package com.pruebatec.pt2gestionturnos.persistence;

import com.pruebatec.pt2gestionturnos.logic.model.Citizen;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.pruebatec.pt2gestionturnos.logic.model.User;
import com.pruebatec.pt2gestionturnos.logic.model.Turn;
import com.pruebatec.pt2gestionturnos.persistence.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CitizenJpaController implements Serializable {

    public CitizenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CitizenJpaController() {
        emf = Persistence.createEntityManagerFactory("turneroPU");
    }

    public void create(Citizen citizen) {
        if (citizen.getTurnsList() == null) {
            citizen.setTurnsList(new ArrayList<Turn>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = citizen.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getId());
                citizen.setUser(user);
            }
            List<Turn> attachedTurnsList = new ArrayList<Turn>();
            for (Turn turnsListTurnToAttach : citizen.getTurnsList()) {
                turnsListTurnToAttach = em.getReference(turnsListTurnToAttach.getClass(), turnsListTurnToAttach.getId());
                attachedTurnsList.add(turnsListTurnToAttach);
            }
            citizen.setTurnsList(attachedTurnsList);
            em.persist(citizen);
            if (user != null) {
                Citizen oldCitizenOfUser = user.getCitizen();
                if (oldCitizenOfUser != null) {
                    oldCitizenOfUser.setUser(null);
                    oldCitizenOfUser = em.merge(oldCitizenOfUser);
                }
                user.setCitizen(citizen);
                user = em.merge(user);
            }
            for (Turn turnsListTurn : citizen.getTurnsList()) {
                Citizen oldCitizenOfTurnsListTurn = turnsListTurn.getCitizen();
                turnsListTurn.setCitizen(citizen);
                turnsListTurn = em.merge(turnsListTurn);
                if (oldCitizenOfTurnsListTurn != null) {
                    oldCitizenOfTurnsListTurn.getTurnsList().remove(turnsListTurn);
                    oldCitizenOfTurnsListTurn = em.merge(oldCitizenOfTurnsListTurn);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Citizen citizen) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Citizen persistentCitizen = em.find(Citizen.class, citizen.getId());
            User userOld = persistentCitizen.getUser();
            User userNew = citizen.getUser();
            List<Turn> turnsListOld = persistentCitizen.getTurnsList();
            List<Turn> turnsListNew = citizen.getTurnsList();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getId());
                citizen.setUser(userNew);
            }
            List<Turn> attachedTurnsListNew = new ArrayList<Turn>();
            for (Turn turnsListNewTurnToAttach : turnsListNew) {
                turnsListNewTurnToAttach = em.getReference(turnsListNewTurnToAttach.getClass(), turnsListNewTurnToAttach.getId());
                attachedTurnsListNew.add(turnsListNewTurnToAttach);
            }
            turnsListNew = attachedTurnsListNew;
            citizen.setTurnsList(turnsListNew);
            citizen = em.merge(citizen);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.setCitizen(null);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                Citizen oldCitizenOfUser = userNew.getCitizen();
                if (oldCitizenOfUser != null) {
                    oldCitizenOfUser.setUser(null);
                    oldCitizenOfUser = em.merge(oldCitizenOfUser);
                }
                userNew.setCitizen(citizen);
                userNew = em.merge(userNew);
            }
            for (Turn turnsListOldTurn : turnsListOld) {
                if (!turnsListNew.contains(turnsListOldTurn)) {
                    turnsListOldTurn.setCitizen(null);
                    turnsListOldTurn = em.merge(turnsListOldTurn);
                }
            }
            for (Turn turnsListNewTurn : turnsListNew) {
                if (!turnsListOld.contains(turnsListNewTurn)) {
                    Citizen oldCitizenOfTurnsListNewTurn = turnsListNewTurn.getCitizen();
                    turnsListNewTurn.setCitizen(citizen);
                    turnsListNewTurn = em.merge(turnsListNewTurn);
                    if (oldCitizenOfTurnsListNewTurn != null && !oldCitizenOfTurnsListNewTurn.equals(citizen)) {
                        oldCitizenOfTurnsListNewTurn.getTurnsList().remove(turnsListNewTurn);
                        oldCitizenOfTurnsListNewTurn = em.merge(oldCitizenOfTurnsListNewTurn);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = citizen.getId();
                if (findCitizen(id) == null) {
                    throw new NonexistentEntityException("The citizen with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Citizen citizen;
            try {
                citizen = em.getReference(Citizen.class, id);
                citizen.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The citizen with id " + id + " no longer exists.", enfe);
            }
            User user = citizen.getUser();
            if (user != null) {
                user.setCitizen(null);
                user = em.merge(user);
            }
            List<Turn> turnsList = citizen.getTurnsList();
            for (Turn turnsListTurn : turnsList) {
                turnsListTurn.setCitizen(null);
                turnsListTurn = em.merge(turnsListTurn);
            }
            em.remove(citizen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Citizen> findCitizenEntities() {
        return findCitizenEntities(true, -1, -1);
    }

    public List<Citizen> findCitizenEntities(int maxResults, int firstResult) {
        return findCitizenEntities(false, maxResults, firstResult);
    }

    private List<Citizen> findCitizenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Citizen.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Citizen findCitizen(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Citizen.class, id);
        } finally {
            em.close();
        }
    }

    public Citizen findCitizenByDni(String dni) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Citizen.findByDni");
            q.setParameter("dni", dni);
            return (Citizen) q.getSingleResult();
        } finally {
            em.close();
        }
    }

    public int getCitizenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Citizen> rt = cq.from(Citizen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    List<Citizen> findCitizensSurname(String surname) {
        EntityManager em = getEntityManager();
        try {
            List<Citizen> citizens = em
                    .createNamedQuery("Citizen.findBySurname", Citizen.class)
                    .setParameter("surname", surname)
                    .getResultList();
            return citizens;
        } finally {
            em.close();
        }
    }

}
