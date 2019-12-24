package ejbs;

import entities.Athlete;
import entities.Modality;
import entities.PracticedModality;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Stateless(name = "AthleteEJB")
public class AthleteBean {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private ModalityBean modalityBean;
    public AthleteBean(){

    }

    public Athlete create(String username, String password, String name, String email) throws Exception {
        if (find(username)!=null){
            throw new Exception("Username '" + username + "' already exists");
        }
        Athlete athlete = new Athlete(username,password,name,email);
        em.persist(athlete);
        return athlete;
    }

    public List<Athlete> all() {
        try {
            return (List<Athlete>) em.createNamedQuery("getAllAthletes").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ATHLETE", e);
        }
    }

    public Athlete find(String username) throws Exception {
        try{
            return em.find(Athlete.class, username);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_ATHLETE", e);
        }
    }

    public Set<PracticedModality> getAllPracticedModalityByAthlete(String username) throws Exception {
        try{
            return em.find(Athlete.class, username).getModalities();
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_ATHLETE", e);
        }
    }

    public PracticedModality findPracticedModalityByAthlete(String username, int id) throws Exception {
        try{
            Athlete athlete = em.find(Athlete.class, username);
            for (PracticedModality practicedModality : athlete.getModalities()) {
                if(practicedModality.getId() == id){
                    return practicedModality;
                }
            }
            return null;
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_ATHLETE", e);
        }
    }

    public List<Modality> getAllModalityPracticedByAthlete(String username, int id) throws Exception {
        try{
            List<Modality> modalities = new ArrayList<>();
            Athlete athlete = em.find(Athlete.class, username);
            for (PracticedModality practicedModality : athlete.getModalities()) {
                modalities.add(practicedModality.getModality());
            }
            if(modalities.isEmpty()){
                return null;
            }
            return modalities;
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_ATHLETE", e);
        }
    }

    public Athlete update(String username, String password, String name, String email) throws Exception {
        try{
            Athlete athlete = em.find(Athlete.class, username);

            if(athlete == null){
                throw new Exception("ERROR_FINDING_ATHLETE");
            }

            //em.lock(administrator, LockModeType.OPTIMISTIC);
            athlete.setName(name);
            athlete.setEmail(email);
            athlete.setPassword(password);
            em.merge(athlete);
            return athlete;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_ATHLETE");
        }
    }

    public boolean delete(String username) throws Exception{
        try{
            Athlete athlete = em.find(Athlete.class, username);

            if(athlete == null){
                throw new Exception("ERROR_FINDING_ATHLETE");
            }

            //em.lock(coach, LockModeType.OPTIMISTIC);
            em.remove(athlete);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_ATHLETE");
        }
    }
}
