package ejbs;

import entities.*;
import exceptions.MyEntityCantBeDeletedException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Stateless(name = "CoachEJB")
public class CoachBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    ModalityBean modalityBean;
    @EJB
    AthleteBean athleteBean;
    public CoachBean() {
    }

    public Coach create(String username, String password, String name, String email) throws Exception {
        if (find(username)!=null){
            throw new MyEntityExistsException("Username '" + username + "' already exists");
        }
        if(username.equals("") || password.equals("") || name.equals("") || email.equals("")){
            throw new MyIllegalArgumentException("Administrator fields can't be empty");
        }
        Coach coach = new Coach(username,password,name,email);
        em.persist(coach);
        return coach;
    }

    public List<Coach> all() {
        try {
            return em.createNamedQuery("getAllCoaches").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_COACHES", e);
        }
    }

    public Coach find(String username) throws Exception {
        try{
            return em.find(Coach.class, username);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_COACH", e);
        }
    }

    public Set<Athlete> findCoachAthletes(String username) throws Exception {
        try{
            Set<Athlete> athletes = new LinkedHashSet<>();
            Coach coach = em.find(Coach.class, username);
            for (Athlete athlete : athleteBean.all()) {
                for (PracticedModality practicedModality : athlete.getPracticedModalities()) {
                    for (Modality modality : coach.getModalities()) {
                        if(practicedModality.getModality().getId() == modality.getId()){
                            athletes.add(athlete);
                        }
                    }
                }
            }
            return athletes;
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_COACH", e);
        }
    }

    public Coach update(String username, String name, String email) throws Exception {
        Coach coach = em.find(Coach.class, username);

        if(coach == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_COACH");
        }
        if(username.equals("") || name.equals("") || email.equals("")){
            throw new MyIllegalArgumentException("Administrator fields can't be empty");
        }

        try{
            em.lock(coach, LockModeType.OPTIMISTIC);
            coach.setName(name);
            coach.setEmail(email);
            em.merge(coach);
            return coach;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_COACH");
        }
    }

    public boolean delete(String username) throws Exception{
        Coach coach = em.find(Coach.class, username);

        if(coach == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_COACH");
        }
        if(!coach.getTeachedModalities().isEmpty()){
            throw new MyEntityCantBeDeletedException("ERROR_DELETING_COACH");
        }

        try{
            em.lock(coach, LockModeType.OPTIMISTIC);
            em.remove(coach);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_COACH");
        }
    }

    public Set<Coach> filter(String username, int modalityId) throws Exception {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Coach> criteria = builder.createQuery(Coach.class);
        Root<Coach> from = criteria.from(Coach.class);
        criteria.select(from);

        if(!username.equals(null)){
            criteria.where(builder.equal(from.get("username"), username));
        }
        CriteriaQuery<TeachedModality> criteriaTeached = builder.createQuery(TeachedModality.class);
        Root<TeachedModality> fromTeached = criteria.from(TeachedModality.class);
        criteriaTeached.select(fromTeached);

        if(modalityId > 0){
            criteriaTeached.where(builder.equal(fromTeached.get("modality"), modalityBean.find(modalityId)));
        }

        TypedQuery<Coach> queryCoach = em.createQuery(criteria);
        TypedQuery<TeachedModality> queryTeached = em.createQuery(criteriaTeached);

        Set<Coach> result = new LinkedHashSet<>();

        if((username.equals("") || username.equals(null)) && modalityId <= 0 ){
            for (Coach coach : all()) {
                result.add(coach);
            }
            return result;
        }


        if(!username.equals(null) && !username.equals("")){
            for (Coach coach : queryCoach.getResultList()) {
                result.add(coach);
            }
        }

        for (TeachedModality teachedModality : queryTeached.getResultList()) {
            if(teachedModality.getModality() != null && modalityId == teachedModality.getModality().getId()){
                result.add(teachedModality.getCoach());
            }
        }

        return result;
    }
}