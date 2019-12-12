package ejbs;

import entities.Coach;
import entities.Modality;
import entities.Schedule;
import entities.TeachedModality;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "TeachedModalityEJB")
public class TeachedModalityBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    ModalityBean modalityBean;
    @EJB
    EchelonBean echelonBean;
    @EJB
    CoachBean coachBean;
    @EJB
    ScheduleBean scheduleBean;

    public TeachedModalityBean() {
    }

    public TeachedModality create(int modality_id, String username) throws Exception {
        Modality modality = modalityBean.find(modality_id);
        Coach coach = coachBean.find(username);

        TeachedModality teachedModality = new TeachedModality(modality,coach);
        em.persist(teachedModality);
        modality.addTeachedModalities(teachedModality);
        coach.addTeachedModalities(teachedModality);
        return teachedModality;
    }

    public List<TeachedModality> all() {
        try {
            return (List<TeachedModality>) em.createNamedQuery("getAllTeachedModalities").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_TEACHED_MODALITIES", e);
        }
    }

    public TeachedModality find(int id) throws Exception {
        try{
            return em.find(TeachedModality.class, id);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_TEACHED_MODALITY", e);
        }
    }

    public TeachedModality addSchedule(int PM_id, int schedule_id) throws Exception {
        try{
            TeachedModality teachedModality= em.find(TeachedModality.class, PM_id);

            if(teachedModality == null){
                throw new Exception("ERROR_FINDING_TEACHED_MODALITY");
            }

            Schedule schedule = scheduleBean.find(schedule_id);
            if(schedule == null){
                throw new Exception("ERROR_FINDING_SCHEDULE");
            }

            for (Schedule practicedModalitySchedule : teachedModality.getSchedules()) {
                if(practicedModalitySchedule.getId() == schedule.getId()){
                    throw new Exception("SCHEDULE_ALREADY_EXISTS");
                }
            }

            teachedModality.addSchedule(schedule);

            em.merge(teachedModality);
            return teachedModality;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_TEACHED_MODALITY");
        }
    }

    public TeachedModality removeSchedule(int PM_id, int schedule_id) throws Exception {
        try{
            TeachedModality teachedModality= em.find(TeachedModality.class, PM_id);

            if(teachedModality == null){
                throw new Exception("ERROR_FINDING_TEACHED_MODALITY");
            }

            Schedule schedule = scheduleBean.find(schedule_id);
            if(schedule == null){
                throw new Exception("ERROR_FINDING_SCHEDULE");
            }


            for (Schedule practicedModalitySchedule : teachedModality.getSchedules()) {
                if(practicedModalitySchedule.getId() == schedule.getId()){
                    teachedModality.removeSchedule(schedule);
                }
            }

            em.merge(teachedModality);
            return teachedModality;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_TEACHED_MODALITY");
        }
    }
}
