package ejbs;

import entities.*;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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

    public PracticedModality create(int modality_id, String echelon_name, String graduation_name, String username) throws Exception {
        Modality modality = modalityBean.find(modality_id);
        Echelon echelon = echelonBean.find(echelon_name);
        Graduations graduations = graduationsBean.find(graduation_name);
        Athlete athlete = athleteBean.find(username);

        PracticedModality practicedModality = new PracticedModality(modality,echelon,graduations,athlete);
        em.persist(practicedModality);
        modality.addPracticedModality(practicedModality);
        athlete.addModality(practicedModality);
        return practicedModality;
    }

    public PracticedModality createWithEchelon(int modality_id, String echelon_name, String username) throws Exception {
        Modality modality = modalityBean.find(modality_id);
        Echelon echelon = echelonBean.find(echelon_name);
        Athlete athlete = athleteBean.find(username);

        PracticedModality practicedModality = new PracticedModality(modality,echelon,athlete);
        em.persist(practicedModality);
        modality.addPracticedModality(practicedModality);
        athlete.addModality(practicedModality);
        return practicedModality;
    }

    public PracticedModality createWithGraduation(int modality_id, String graduation_name, String username) throws Exception {
        Modality modality = modalityBean.find(modality_id);
        Graduations graduations = graduationsBean.find(graduation_name);
        Athlete athlete = athleteBean.find(username);

        PracticedModality practicedModality = new PracticedModality(modality,graduations,athlete);
        em.persist(practicedModality);
        modality.addPracticedModality(practicedModality);
        athlete.addModality(practicedModality);
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
            throw new Exception("ERROR_FINDING_PRACTICED_MODALITY", e);
        }
    }

    public PracticedModality update(int PM_id, String echelon_name, String graduation_name) throws Exception {
        try{
            PracticedModality practicedModality = em.find(PracticedModality.class, PM_id);

            if(practicedModality == null){
                throw new Exception("ERROR_FINDING_PRACTICED_MODALITY");
            }
            if(graduation_name != null){
                Graduations graduations = graduationsBean.find(graduation_name);
                practicedModality.setGraduations(graduations);
            }
            if(graduation_name != null){
                Echelon echelon = echelonBean.find(echelon_name);
                practicedModality.setEchelon(echelon);
            }

            em.merge(practicedModality);
            return practicedModality;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PRACTICED_MODALITY");
        }
    }

    public PracticedModality addSchedule(int PM_id, int schedule_id) throws Exception {
        try{
            PracticedModality practicedModality = em.find(PracticedModality.class, PM_id);

            if(practicedModality == null){
                throw new Exception("ERROR_FINDING_PRACTICED_MODALITY");
            }

            Schedule schedule = scheduleBean.find(schedule_id);
            if(schedule == null){
                throw new Exception("ERROR_FINDING_SCHEDULE");
            }

            for (Schedule practicedModalitySchedule : practicedModality.getSchedules()) {
                if(practicedModalitySchedule.getId() == schedule.getId()){
                    throw new Exception("SCHEDULE_ALREADY_EXISTS");
                }
            }

            practicedModality.addSchedule(schedule);

            em.merge(practicedModality);
            return practicedModality;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PRACTICED_MODALITY");
        }
    }

    public PracticedModality removeSchedule(int PM_id, int schedule_id) throws Exception {
        try{
            PracticedModality practicedModality = em.find(PracticedModality.class, PM_id);

            if(practicedModality == null){
                throw new Exception("ERROR_FINDING_PRACTICED_MODALITY");
            }

            Schedule schedule = scheduleBean.find(schedule_id);
            if(schedule == null){
                throw new Exception("ERROR_FINDING_SCHEDULE");
            }


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
    /*public boolean delete(int PM_id) throws Exception{
        try{
            PracticedModality practicedModality = em.find(PracticedModality.class, PM_id);

            if(practicedModality == null){
                throw new Exception("ERROR_FINDING_PRACTICED_MODALITY");
            }

            //em.lock(coach, LockModeType.OPTIMISTIC);
            em.remove(practicedModality);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PRACTICED_MODALITY");
        }
    }*/
}
