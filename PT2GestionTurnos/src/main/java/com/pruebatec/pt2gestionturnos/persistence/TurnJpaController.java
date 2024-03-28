package com.pruebatec.pt2gestionturnos.persistence;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.pruebatec.pt2gestionturnos.logic.model.Citizen;
import com.pruebatec.pt2gestionturnos.logic.model.Procedure;
import com.pruebatec.pt2gestionturnos.logic.model.Turn;
import com.pruebatec.pt2gestionturnos.persistence.exceptions.NonexistentEntityException;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TurnJpaController implements Serializable {

    public TurnJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TurnJpaController() {
        emf = Persistence.createEntityManagerFactory("turneroPU");
    }

    public void create(Turn turn) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Citizen citizen = turn.getCitizen();
            if (citizen != null) {
                citizen = em.getReference(citizen.getClass(), citizen.getId());
                turn.setCitizen(citizen);
            }
            Procedure procedure = turn.getProcedure();
            if (procedure != null) {
                procedure = em.getReference(procedure.getClass(), procedure.getId());
                turn.setProcedure(procedure);
            }
            em.persist(turn);
            if (citizen != null) {
                citizen.getTurnsList().add(turn);
                citizen = em.merge(citizen);
            }
            if (procedure != null) {
                procedure.getTurnsList().add(turn);
                procedure = em.merge(procedure);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Turn turn) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turn persistentTurn = em.find(Turn.class, turn.getId());
            Citizen citizenOld = persistentTurn.getCitizen();
            Citizen citizenNew = turn.getCitizen();
            Procedure procedureOld = persistentTurn.getProcedure();
            Procedure procedureNew = turn.getProcedure();
            if (citizenNew != null) {
                citizenNew = em.getReference(citizenNew.getClass(), citizenNew.getId());
                turn.setCitizen(citizenNew);
            }
            if (procedureNew != null) {
                procedureNew = em.getReference(procedureNew.getClass(), procedureNew.getId());
                turn.setProcedure(procedureNew);
            }
            turn = em.merge(turn);
            if (citizenOld != null && !citizenOld.equals(citizenNew)) {
                citizenOld.getTurnsList().remove(turn);
                citizenOld = em.merge(citizenOld);
            }
            if (citizenNew != null && !citizenNew.equals(citizenOld)) {
                citizenNew.getTurnsList().add(turn);
                citizenNew = em.merge(citizenNew);
            }
            if (procedureOld != null && !procedureOld.equals(procedureNew)) {
                procedureOld.getTurnsList().remove(turn);
                procedureOld = em.merge(procedureOld);
            }
            if (procedureNew != null && !procedureNew.equals(procedureOld)) {
                procedureNew.getTurnsList().add(turn);
                procedureNew = em.merge(procedureNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = turn.getId();
                if (findTurn(id) == null) {
                    throw new NonexistentEntityException("The turn with id " + id + " no longer exists.");
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
            Turn turn;
            try {
                turn = em.getReference(Turn.class, id);
                turn.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The turn with id " + id + " no longer exists.", enfe);
            }
            Citizen citizen = turn.getCitizen();
            if (citizen != null) {
                citizen.getTurnsList().remove(turn);
                citizen = em.merge(citizen);
            }
            Procedure procedure = turn.getProcedure();
            if (procedure != null) {
                procedure.getTurnsList().remove(turn);
                procedure = em.merge(procedure);
            }
            em.remove(turn);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Turn> findTurnEntities() {
        return findTurnEntities(true, -1, -1);
    }

    public List<Turn> findTurnEntities(int maxResults, int firstResult) {
        return findTurnEntities(false, maxResults, firstResult);
    }

    private List<Turn> findTurnEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Turn.class));
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

    public Turn findTurn(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Turn.class, id);
        } finally {
            em.close();
        }
    }

    public int getTurnCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Turn> rt = cq.from(Turn.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Turn> findTurnsCitizen(Citizen citizen) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Turn.findAllByCitizen");
            q.setParameter("dni", citizen.getDni());
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Turn> findAllTurnsByDate(LocalDate dateFormatted) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Turn.findAllByDate");
            q.setParameter("date", dateFormatted);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Turn> findAllTurnsByDateNCondition(LocalDate dateFormatted, boolean stateFilter) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Turn.findAllByDateNCondition");
            q.setParameter("date", dateFormatted);
            q.setParameter("condition", stateFilter);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Turn> findAllTurnsByCondition(boolean stateFilter) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Turn.findAllByCondition");
            q.setParameter("condition", stateFilter);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

}
