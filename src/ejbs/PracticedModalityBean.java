package ejbs;

import entities.*;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "PracticedModalityEJB")
public class PracticedModalityBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    ModalityBean modalityBean;
    @EJB
    EchelonBean echelonBean;
    @EJB
    GraduationsBean graduationsBean;
    @EJB
    AthleteBean athleteBean;
    @EJB
    ScheduleBean scheduleBean;

    public PracticedModalityBean() {
    }

    public PracticedModality create(int modalityId, int echelonId, int graduationId, String username) throws Exception {
        Modality modality = modalityBean.find(modalityId);
        Echelon echelon = null;
        Graduations graduations = null;
        if(echelonId>0){ echelon = echelonBean.find(echelonId);}
        if(graduationId>0){ graduations = graduationsBean.find(graduationId);}
        Athlete athlete = athleteBean.find(username);

        PracticedModality practicedModality = new PracticedModality(modality,echelon,graduations,athlete);
        em.persist(practicedModality);
        modality.addPracticedModality(practicedModality);
        athlete.addPracticedModality(practicedModality);
        return practicedModality;
    }

    public List<PracticedModality> all() {
        try {
            return (List<PracticedModality>) em.createNamedQuery("getAllPracticedModalities").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PRACTICED_MODALITIES", e);
        }
    }

    public PracticedModality find(int id) throws Exception {
        try{
            return em.find(PracticedModality.class, id);
        } catch (Exception e) {
            throw new MyEntityNotFoundException("ERROR_FINDING_PRACTICED_MODALITY");
        }
    }

    public PracticedModality update(int PM_id, int echelonId, int graduationId) throws Exception {
        PracticedModality practicedModality = em.find(PracticedModality.class, PM_id);

        if(practicedModality == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_PRACTICED_MODALITY");
        }
        if(graduationId != 0){
            Graduations graduations = graduationsBean.find(graduationId);
            practicedModality.setGraduations(graduations);
        }
        if(echelonId != 0){
            Echelon echelon = echelonBean.find(echelonId);
            practicedModality.setEchelon(echelon);
        }

        try{
            em.lock(practicedModality, LockModeType.OPTIMISTIC);
            em.merge(practicedModality);
            return practicedModality;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PRACTICED_MODALITY");
        }
    }

    public PracticedModality addSchedule(int PM_id, int schedule_id) throws Exception {
        PracticedModality practicedModality = em.find(PracticedModality.class, PM_id);

        if(practicedModality == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_PRACTICED_MODALITY");
        }

        Schedule schedule = scheduleBean.find(schedule_id);
        if(schedule == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_SCHEDULE");
        }

        for (Schedule practicedModalitySchedule : practicedModality.getSchedules()) {
            if(practicedModalitySchedule.getId() == schedule.getId()){
                throw new MyEntityNotFoundException("SCHEDULE_ALREADY_EXISTS");
            }
        }

        try{
            em.lock(practicedModality, LockModeType.OPTIMISTIC);
            practicedModality.addSchedule(schedule);
            em.merge(practicedModality);
            return practicedModality;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PRACTICED_MODALITY");
        }
    }

    public PracticedModality removeSchedule(int PM_id, int schedule_id) throws Exception {
        PracticedModality practicedModality = em.find(PracticedModality.class, PM_id);

        if(practicedModality == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_PRACTICED_MODALITY");
        }

        Schedule schedule = scheduleBean.find(schedule_id);
        if(schedule == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_SCHEDULE");
        }

        try{
            em.lock(practicedModality, LockModeType.OPTIMISTIC);
            for (Schedule practicedModalitySchedule : practicedModality.getSchedules()) {
                if(practicedModalitySchedule.getId() == schedule.getId()){
                    practicedModality.removeSchedule(schedule);
                }
            }

            em.merge(practicedModality);
            return practicedModality;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PRACTICED_MODALITY");
        }
    }
}
