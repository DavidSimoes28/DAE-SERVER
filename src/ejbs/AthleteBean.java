package ejbs;

import entities.Athlete;
import entities.Modality;
import entities.PracticedModality;
import entities.Purchase;
import exceptions.MyEntityCantBeDeletedException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Stateless(name = "AthleteEJB")
public class AthleteBean {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private ModalityBean modalityBean;
    @EJB
    private EchelonBean echelonBean;
    @EJB
    private GraduationsBean graduationsBean;
    @EJB
    private PurchaseBean purchaseBean;

    public AthleteBean(){

    }

    public Athlete create(String username, String password, String name, String email) throws Exception {
        if (find(username)!=null){
            throw new MyEntityExistsException("Username '" + username + "' already exists");
        }
        if(username.equals("") || password.equals("") || name.equals("") || email.equals("")){
            throw new MyIllegalArgumentException("Athlete fields can't be empty");
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
            throw new MyEntityNotFoundException("ERROR_FINDING_ATHLETE");
        }
    }

    public Set<PracticedModality> getAllPracticedModalityByAthlete(String username) throws Exception {
        try{
            return em.find(Athlete.class, username).getPracticedModalities();
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_ATHLETE", e);
        }
    }

    public PracticedModality findPracticedModalityByAthlete(String username, int id) throws Exception {
        try{
            Athlete athlete = em.find(Athlete.class, username);
            for (PracticedModality practicedModality : athlete.getPracticedModalities()) {
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
            for (PracticedModality practicedModality : athlete.getPracticedModalities()) {
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

    public Athlete update(String username, String name, String email) throws Exception {
        Athlete athlete = em.find(Athlete.class, username);

        if(athlete == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_ATHLETE");
        }
        if(username.equals("") || name.equals("") || email.equals("")){
            throw new MyIllegalArgumentException("Administrator fields can't be empty");
        }

        try{
            em.lock(athlete, LockModeType.OPTIMISTIC);
            athlete.setName(name);
            athlete.setEmail(email);
            em.merge(athlete);
            return athlete;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_ATHLETE");
        }
    }

    public boolean delete(String username) throws Exception{
        Athlete athlete = em.find(Athlete.class, username);

        if(athlete == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_ATHLETE");
        }


        if(!athlete.getPracticedModalities().isEmpty()){
            throw new MyEntityCantBeDeletedException("ERROR_DELETING_ATHLETE");
        }

        for (Purchase purchase : purchaseBean.all()) {
            if(purchase.getPartner().getUsername().equals(athlete.getUsername())){
                throw new MyEntityCantBeDeletedException("ERROR_DELETING_ATHLETE");
            }
        }

        try{
            em.lock(athlete, LockModeType.OPTIMISTIC);
            em.remove(athlete);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_ATHLETE");
        }
    }

    public Set<Athlete> filter(String username, int modalityId, int echelonId, int graduationId) throws Exception {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Athlete> criteria = builder.createQuery(Athlete.class);
        Root<Athlete> from = criteria.from(Athlete.class);
        criteria.select(from);
        if(!username.equals(null)){
            criteria.where(builder.equal(from.get("username"), username));
        }
        CriteriaQuery<PracticedModality> criteriaPracticed = builder.createQuery(PracticedModality.class);
        Root<PracticedModality> fromPracticed = criteria.from(PracticedModality.class);
        criteriaPracticed.select(fromPracticed);

        if(modalityId > 0){
            criteriaPracticed.where(builder.equal(fromPracticed.get("modality"), modalityBean.find(modalityId)));
        }
        if(echelonId > 0){
            criteriaPracticed.where(builder.equal(fromPracticed.get("echelon"), echelonBean.find(echelonId)));
        }
        if(graduationId > 0){
            criteriaPracticed.where(builder.equal(fromPracticed.get("graduation"), graduationsBean.find(graduationId)));
        }

        TypedQuery<Athlete> queryAthlete = em.createQuery(criteria);
        TypedQuery<PracticedModality> queryPracticed = em.createQuery(criteriaPracticed);

        int i = 0;
        Set<Athlete> result = new LinkedHashSet<>();

        if((username.equals("") || username.equals(null)) && modalityId <= 0 && echelonId <= 0 && graduationId <= 0){
            for (Athlete athlete : all()) {
                result.add(athlete);
            }
            return result;
        }


        if(!username.equals(null) && !username.equals("")){
            for (Athlete athlete : queryAthlete.getResultList()) {
                result.add(athlete);
            }
        }

        for (PracticedModality practicedModality : queryPracticed.getResultList()) {
            if(practicedModality.getModality() != null && modalityId == practicedModality.getModality().getId()){
                result.add(practicedModality.getAthlete());
            }
            if(practicedModality.getGraduations() != null && graduationId == practicedModality.getGraduations().getId()){
                result.add(practicedModality.getAthlete());
            }
            if(practicedModality.getEchelon() != null && echelonId == practicedModality.getEchelon().getId()){
                result.add(practicedModality.getAthlete());
            }
        }


        return result;
    }
}
