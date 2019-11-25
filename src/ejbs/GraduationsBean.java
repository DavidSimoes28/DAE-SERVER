package ejbs;

import entities.Graduations;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "GraduationsEJB")
public class GraduationsBean {

    @PersistenceContext
    private EntityManager em;

    public GraduationsBean() {
    }

    public Graduations create(String name) throws Exception {
        Graduations graduations = new Graduations(name);
        em.persist(graduations);
        return graduations;
    }

    public List<Graduations> all() {
        try {
            return (List<Graduations>) em.createNamedQuery("getAllGraduations").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_STUDENTS", e);
        }
    }

    public Graduations find(int id) throws Exception{
        try{
            return em.find(Graduations.class, id);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_ADMINISTRATOR", e);
        }
    }

    public Graduations update(int id, String name) throws Exception {
        try{
            Graduations graduations = em.find(Graduations.class, id);

            if(graduations == null){
                throw new Exception("ERROR_FINDING_STUDENT");
            }

            //em.lock(administrator, LockModeType.OPTIMISTIC);
            graduations.setName(name);
            em.merge(graduations);
            return graduations;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_STUDENT");
        }
    }

    public boolean delete(int id) throws Exception{
        try{
            Graduations graduations = em.find(Graduations.class, id);

            if(graduations == null){
                throw new Exception("ERROR_FINDING_STUDENT");
            }

            //em.lock(administrator, LockModeType.OPTIMISTIC);
            em.remove(graduations);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_STUDENT");
        }
    }
}
