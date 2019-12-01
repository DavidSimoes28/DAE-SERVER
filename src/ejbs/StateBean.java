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
        if (find(name)!=null){
            throw new Exception("State '" + name + "' already exists");
        }
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

    public State find(String name) throws Exception {
        try{
            return em.find(State.class, name);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_STATE", e);
        }
    }

    /*public State update(String name) throws Exception {
        try{
            State state1 = em.find(State.class, name);

            if(state1 == null){
                throw new Exception("ERROR_FINDING_STATE");
            }

            //em.lock(coach, LockModeType.OPTIMISTIC);
            state1.setName(name);
            em.merge(state1);
            return state1;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_STATE");
        }
    }*/

    public boolean delete(String name) throws Exception{
        try{
            State state = em.find(State.class, name);

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
