package ejbs;

import com.sun.org.apache.xpath.internal.operations.Mod;
import entities.Athlete;
import entities.Modality;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        //Modality modality = modalityBean.find(modalityID);
        //athlete.addModality(modality);
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

    public Athlete enroll(int modalityId, String athleteUsername) throws Exception {

        Modality modality = modalityBean.find(modalityId);
        Athlete athlete = find(athleteUsername);
        try{
            if (athlete.getModalities().contains(modality) != modality.getAthletes().contains(athlete)){
                throw new EJBException("ERROR_FINDING_ATHLETE");
            }
            athlete.addModality(modality);
            modality.addAthlete(athlete);
            em.merge(athlete);
            return athlete;
        }catch (Exception e){
            throw new EJBException("ERROR_FINDING_ATHLETE", e);
        }
    }

    public Athlete unroll(int modalityId, String athleteUsername) throws Exception {

        Modality modality = modalityBean.find(modalityId);
        Athlete athlete = find(athleteUsername);
        try{
            if (athlete.getModalities().contains(modality) != modality.getAthletes().contains(athlete)){
                throw new EJBException("ERROR_FINDING_ATHLETE");
            }
            athlete.removeModality(modality);
            modality.removeAthlete(athlete);
            em.merge(athlete);
            return athlete;
        }catch (Exception e){
            throw new EJBException("ERROR_FINDING_ATHLETE", e);
        }
    }

}
