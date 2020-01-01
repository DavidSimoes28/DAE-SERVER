package ejbs;

import entities.Coach;
import entities.Modality;
import entities.PracticedModality;
import entities.TeachedModality;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
    public CoachBean() {
    }

    public Coach create(String username, String password, String name, String email) throws Exception {
        if (find(username)!=null){
            throw new Exception("Username '" + username + "' already exists");
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

    public Coach update(String username, String password, String name, String email) throws Exception {
        try{
            Coach coach = em.find(Coach.class, username);

            if(coach == null){
                throw new Exception("ERROR_FINDING_COACH");
            }

            //em.lock(coach, LockModeType.OPTIMISTIC);
            coach.setName(name);
            coach.setEmail(email);
            coach.setPassword(password);
            em.merge(coach);
            return coach;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_COACH");
        }
    }

    public boolean delete(String username) throws Exception{
        try{
            Coach coach = em.find(Coach.class, username);

            if(coach == null){
                throw new Exception("ERROR_FINDING_COACH");
            }

            //em.lock(coach, LockModeType.OPTIMISTIC);
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