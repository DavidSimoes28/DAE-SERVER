package ejbs;

import entities.*;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Stateless(name = "ModalityEJB")
public class ModalityBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    ScheduleBean scheduleBean;

    public ModalityBean() {
    }

    public Modality create(String name, int sportYear, boolean active) throws Exception {
        Modality modality = new Modality(name,sportYear,active);
        em.persist(modality);
        return modality;
    }

    public List<Modality> all() {
        try {
            return (List<Modality>) em.createNamedQuery("getAllModalities").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_STUDENTS", e);
        }
    }

    public Modality find(int id) throws Exception {
        try{
            return em.find(Modality.class, id);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_ADMINISTRATOR", e);
        }
    }

    public List<Athlete> getAllAthleteWhoPracticedAModality(int id) throws Exception {
        try{
            List<Athlete> athletes = new ArrayList<>();
            Modality modality = em.find(Modality.class, id);
            for (PracticedModality practicedModality : modality.getPracticedModalities()) {
                athletes.add(practicedModality.getAthlete());
            }
            if(athletes.isEmpty()){
                return null;
            }
            return athletes;
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_ATHLETE", e);
        }
    }

    public Set<Schedule> getModalitySchedules(int id) throws Exception {
        try{
            Modality modality = em.find(Modality.class, id);
            return modality.getSchedules();
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_ATHLETE", e);
        }
    }

    public Modality addScheduleToModality(int modalityId,int scheduleId) throws Exception {
        try{
            Schedule schedule = scheduleBean.find(scheduleId);
            Modality modality = find(modalityId);
            modality.addSchedule(schedule);
            return modality;
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_ATHLETE", e);
        }
    }

    public Modality removeScheduleToModality(int modalityId, int scheduleId) throws MyIllegalArgumentException {
        try{
            Schedule schedule = scheduleBean.find(scheduleId);
            Modality modality = find(modalityId);
            int i = 0;
            for (Schedule modalitySchedule : modality.getSchedules()) {
                if(modalitySchedule.getId() == scheduleId){
                    i=1;
                }
            }
            if(i==1) modality.removeSchedule(schedule);
            return modality;
        } catch (Exception e) {
            throw new MyIllegalArgumentException("ERROR_FINDING_ATHLETE");
        }
    }

    public List<Schedule> missingScheduleFromModality(int modalityId) throws Exception {
        try{
            Modality modality = find(modalityId);
            List<Schedule> schedules = new ArrayList<>();
            List<Schedule> all = scheduleBean.all();
            List<Schedule> missing = new ArrayList<>();
            for (Schedule schedule : modality.getSchedules()) {
                schedules.add(schedule);
            }
            int i = 0;
            for (Schedule schedule : all) {
                for (Schedule schedule1: schedules) {
                    if(schedule.getId() == schedule1.getId()){
                        i = 1;
                    }
                }
                if(i!=1){
                    missing.add(schedule);
                    schedules.add(schedule);
                }
                i=0;
            }
            return missing;
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_ATHLETE", e);
        }
    }

    public Modality update(int id, String name) throws Exception {
        try{
            Modality modality = em.find(Modality.class, id);

            if(modality == null){
                throw new Exception("ERROR_FINDING_STUDENT");
            }

            //em.lock(administrator, LockModeType.OPTIMISTIC);
            modality.setName(name);
            em.merge(modality);
            return modality;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_STUDENT");
        }
    }

    public boolean delete(int id) throws Exception{
        try{
            Modality modality = em.find(Modality.class, id);

            if(modality == null){
                throw new Exception("ERROR_FINDING_STUDENT");
            }

            //em.lock(administrator, LockModeType.OPTIMISTIC);
            em.remove(modality);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_STUDENT");
        }
    }

    public Set<Modality> filter(String name) throws Exception {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Modality> criteria = builder.createQuery(Modality.class);
        Root<Modality> from = criteria.from(Modality.class);
        criteria.select(from);

        if(!name.equals(null)){
            criteria.where(builder.equal(from.get("name"), name));
        }

        TypedQuery<Modality> query= em.createQuery(criteria);

        Set<Modality> result = new LinkedHashSet<>();

        if((name.equals("") || name.equals(null))){
            for (Modality modality : all()) {
                result.add(modality);
            }
            return result;
        }


        if(!name.equals(null) && !name.equals("")){
            for (Modality modality : query.getResultList()) {
                result.add(modality);
            }
        }

        return result;
    }
}
