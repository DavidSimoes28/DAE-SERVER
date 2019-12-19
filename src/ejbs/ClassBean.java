package ejbs;

import entities.*;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless(name = "ClassEJB")
public class ClassBean {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private ModalityBean modalityBean;
    @EJB
    private CoachBean coachBean;
    @EJB
    private ScheduleBean scheduleBean;


    public Classes create(String coach_username, int schedule_id, int modality_id, Date date) throws Exception {
        Coach coach = coachBean.find(coach_username);
        Schedule schedule = scheduleBean.find(schedule_id);
        Modality modality = modalityBean.find(modality_id);
        Classes classes = new Classes(coach,schedule,modality,date);
        em.persist(classes);
        coach.addClasses(classes);
        return classes;
    }

    public List<Classes> all() {
        try {
            return em.createNamedQuery("getAllClasses").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_COACHES", e);
        }
    }

    public Classes find(int class_id) throws Exception {
        try{
            return em.find(Classes.class, class_id);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_COACH", e);
        }
    }

    public Classes update(int class_id,String coach_username, int schedule_id, Date date) throws Exception {
        try{
            Classes classes = find(class_id);
            Coach coach = em.find(Coach.class, coach_username);
            Schedule schedule = scheduleBean.find(schedule_id);
            if(classes == null){
                throw new Exception("ERROR_FINDING_COACH");
            }
            if(coach == null){
                throw new Exception("ERROR_FINDING_COACH");
            }
            if(schedule == null){
                throw new Exception("ERROR_FINDING_COACH");
            }
            classes.getCoach().removeClasses(classes);
            classes.setCoach(coach);
            classes.setSchedule(schedule);
            classes.setDate(date);
            return classes;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_COACH");
        }
    }

    public boolean delete(int class_id) throws Exception{
        try{
            Classes classes = em.find(Classes.class, class_id);

            if(classes == null){
                throw new Exception("ERROR_FINDING_COACH");
            }
            Date currentDate = new Date();

            if(classes.getDate().after(currentDate)){
                throw new Exception("ERROR_FINDING_COACH");
            }

            classes.getCoach().removeClasses(classes);
            for (Athlete athlete : classes.getAthletesPresent()) {
                athlete.removeClass(classes);
            }
            em.remove(classes);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_COACH");
        }
    }
}
