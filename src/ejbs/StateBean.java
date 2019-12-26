package ejbs;

import entities.State;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "StateEJB")
public class StateBean {

    @PersistenceContext
    private EntityManager em;

    public StateBean() {
    }

    public State create(String name) throws Exception {
        State state = new State(name);
        em.persist(state);
        return state;
    }

    public List<State> all() {
        try {
            return (List<State>) em.createNamedQuery("getAllStates").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_COACHES", e);
        }
    }

    public State find(int id) throws Exception {
        try{
            return em.find(State.class, id);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_STATE", e);
        }
    }

    public boolean delete(int id) throws Exception{
        try{
            State state = em.find(State.class, id);

            if(state == null){
                throw new Exception("ERROR_FINDING_STATE");
            }

            //em.lock(coach, LockModeType.OPTIMISTIC);
            em.remove(state);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_STATE");
        }
    }
}
