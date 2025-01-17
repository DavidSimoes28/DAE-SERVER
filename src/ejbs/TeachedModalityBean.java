package ejbs;

import entities.*;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
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

    public TeachedModality create(int modalityId, String username, int echelonId) throws Exception {
        Modality modality = modalityBean.find(modalityId);
        Coach coach = coachBean.find(username);
        Echelon echelon = echelonBean.find(echelonId);

        TeachedModality teachedModality = new TeachedModality(modality,coach,echelon);
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
            throw new MyEntityNotFoundException("ERROR_FINDING_TEACHED_MODALITY");
        }
    }

    public TeachedModality addSchedule(int PM_id, int schedule_id) throws Exception {
        TeachedModality teachedModality= em.find(TeachedModality.class, PM_id);

        if(teachedModality == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_TEACHED_MODALITY");
        }

        Schedule schedule = scheduleBean.find(schedule_id);
        if(schedule == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_SCHEDULE");
        }

        for (Schedule practicedModalitySchedule : teachedModality.getSchedules()) {
            if(practicedModalitySchedule.getId() == schedule.getId()){
                throw new MyEntityExistsException("SCHEDULE_ALREADY_EXISTS");
            }
        }

        try{
            em.lock(teachedModality, LockModeType.OPTIMISTIC);
            teachedModality.addSchedule(schedule);
            em.merge(teachedModality);
            return teachedModality;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_TEACHED_MODALITY");
        }
    }

    public TeachedModality removeSchedule(int PM_id, int schedule_id) throws Exception {
        TeachedModality teachedModality= em.find(TeachedModality.class, PM_id);

        if(teachedModality == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_TEACHED_MODALITY");
        }

        Schedule schedule = scheduleBean.find(schedule_id);
        if(schedule == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_SCHEDULE");
        }

        try{
            em.lock(teachedModality, LockModeType.OPTIMISTIC);
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
