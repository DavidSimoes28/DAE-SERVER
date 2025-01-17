package ejbs;

import entities.State;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "StateEJB")
public class StateBean {

    @PersistenceContext
    private EntityManager em;

    public StateBean() {
    }

    public State create(String name) throws Exception {
        if(name.equals("")){
            throw new MyIllegalArgumentException("State field can't be empty");
        }
        State state = new State(name);
        em.persist(state);
        return state;
    }

    public List<State> all() {
        try {
            return (List<State>) em.createNamedQuery("getAllStates").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_STATES", e);
        }
    }

    public State find(int id) throws Exception {
        try{
            return em.find(State.class, id);
        } catch (Exception e) {
            throw new MyEntityNotFoundException("ERROR_FINDING_STATE");
        }
    }

    public State getPaidState() throws Exception {
        try{
            for (State state : all()) {
                if(state.getName().equals("Paid")){
                    return state;
                }
            }
            return null;
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_STATE", e);
        }
    }

    public State getNotPaidState() throws Exception {
        try{
            for (State state : all()) {
                if(state.getName().equals("Not Paid")){
                    return state;
                }
            }
            return null;
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_STATE", e);
        }
    }

    /*public boolean delete(int id) throws Exception{
        try{
            State state = em.find(State.class, id);

            if(state == null){
                throw new Exception("ERROR_FINDING_STATE");
            }

            em.lock(state, LockModeType.OPTIMISTIC);
            em.remove(state);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_STATE");
        }
    }*/
}
