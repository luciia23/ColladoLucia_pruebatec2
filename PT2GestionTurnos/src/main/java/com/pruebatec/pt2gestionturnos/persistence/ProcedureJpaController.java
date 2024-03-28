
package com.pruebatec.pt2gestionturnos.persistence;

import com.pruebatec.pt2gestionturnos.logic.model.Procedure;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.pruebatec.pt2gestionturnos.logic.model.Turn;
import com.pruebatec.pt2gestionturnos.persistence.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ProcedureJpaController implements Serializable {

    public ProcedureJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public ProcedureJpaController(){
        emf = Persistence.createEntityManagerFactory("turneroPU");
    }

    public void create(Procedure procedure) {
        if (procedure.getTurnsList() == null) {
            procedure.setTurnsList(new ArrayList<Turn>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Turn> attachedTurnsList = new ArrayList<Turn>();
            for (Turn turnsListTurnToAttach : procedure.getTurnsList()) {
                turnsListTurnToAttach = em.getReference(turnsListTurnToAttach.getClass(), turnsListTurnToAttach.getId());
                attachedTurnsList.add(turnsListTurnToAttach);
            }
            procedure.setTurnsList(attachedTurnsList);
            em.persist(procedure);
            for (Turn turnsListTurn : procedure.getTurnsList()) {
                Procedure oldProcedureOfTurnsListTurn = turnsListTurn.getProcedure();
                turnsListTurn.setProcedure(procedure);
                turnsListTurn = em.merge(turnsListTurn);
                if (oldProcedureOfTurnsListTurn != null) {
                    oldProcedureOfTurnsListTurn.getTurnsList().remove(turnsListTurn);
                    oldProcedureOfTurnsListTurn = em.merge(oldProcedureOfTurnsListTurn);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Procedure procedure) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Procedure persistentProcedure = em.find(Procedure.class, procedure.getId());
            List<Turn> turnsListOld = persistentProcedure.getTurnsList();
            List<Turn> turnsListNew = procedure.getTurnsList();
            List<Turn> attachedTurnsListNew = new ArrayList<Turn>();
            for (Turn turnsListNewTurnToAttach : turnsListNew) {
                turnsListNewTurnToAttach = em.getReference(turnsListNewTurnToAttach.getClass(), turnsListNewTurnToAttach.getId());
                attachedTurnsListNew.add(turnsListNewTurnToAttach);
            }
            turnsListNew = attachedTurnsListNew;
            procedure.setTurnsList(turnsListNew);
            procedure = em.merge(procedure);
            for (Turn turnsListOldTurn : turnsListOld) {
                if (!turnsListNew.contains(turnsListOldTurn)) {
                    turnsListOldTurn.setProcedure(null);
                    turnsListOldTurn = em.merge(turnsListOldTurn);
                }
            }
            for (Turn turnsListNewTurn : turnsListNew) {
                if (!turnsListOld.contains(turnsListNewTurn)) {
                    Procedure oldProcedureOfTurnsListNewTurn = turnsListNewTurn.getProcedure();
                    turnsListNewTurn.setProcedure(procedure);
                    turnsListNewTurn = em.merge(turnsListNewTurn);
                    if (oldProcedureOfTurnsListNewTurn != null && !oldProcedureOfTurnsListNewTurn.equals(procedure)) {
                        oldProcedureOfTurnsListNewTurn.getTurnsList().remove(turnsListNewTurn);
                        oldProcedureOfTurnsListNewTurn = em.merge(oldProcedureOfTurnsListNewTurn);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = procedure.getId();
                if (findProcedure(id) == null) {
                    throw new NonexistentEntityException("The procedure with id " + id + " no longer exists.");
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
            Procedure procedure;
            try {
                procedure = em.getReference(Procedure.class, id);
                procedure.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The procedure with id " + id + " no longer exists.", enfe);
            }
            List<Turn> turnsList = procedure.getTurnsList();
            for (Turn turnsListTurn : turnsList) {
                turnsListTurn.setProcedure(null);
                turnsListTurn = em.merge(turnsListTurn);
            }
            em.remove(procedure);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Procedure> findProcedureEntities() {
        return findProcedureEntities(true, -1, -1);
    }

    public List<Procedure> findProcedureEntities(int maxResults, int firstResult) {
        return findProcedureEntities(false, maxResults, firstResult);
    }

    private List<Procedure> findProcedureEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Procedure.class));
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

    public Procedure findProcedure(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Procedure.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcedureCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Procedure> rt = cq.from(Procedure.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
