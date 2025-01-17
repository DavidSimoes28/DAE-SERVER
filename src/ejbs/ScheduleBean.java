package ejbs;

import entities.HourTime;
import entities.Schedule;
import exceptions.MyEntityCantBeDeletedException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Stateless(name = "ScheduleEJB")
public class ScheduleBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private HourTimeBean hourTimeBean;

    public ScheduleBean() {
    }

    public Schedule create(int dayNumber, int startDateId, int endDateId) throws Exception {
        if(dayNumber<0 || dayNumber >7){
            throw new MyIllegalArgumentException("Invalid Day");
        }
        DayOfWeek dayOfWeek = DayOfWeek.of(dayNumber);
        HourTime ht1 = hourTimeBean.find(startDateId);
        HourTime ht2 = hourTimeBean.find(endDateId);
        Schedule schedule = new Schedule(dayOfWeek,ht1,ht2);
        em.persist(schedule);
        return schedule;
    }

    public List<Schedule> all() {
        try {
            return (List<Schedule>) em.createNamedQuery("getAllSchedules").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_SCHEDULE", e);
        }
    }

    public Schedule find(int id) throws Exception {
        try{
            return em.find(Schedule.class, id);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_SCHEDULE", e);
        }
    }

    public Schedule update(int id,int dayNumber, int startDateId, int endDateId) throws Exception {
        Schedule schedule = em.find(Schedule.class, id);
        DayOfWeek dayOfWeek = DayOfWeek.of(dayNumber);
        HourTime ht1 = hourTimeBean.find(startDateId);
        HourTime ht2 = hourTimeBean.find(endDateId);
        if(schedule == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_SCHEDULE");
        }

        try{
            em.lock(schedule, LockModeType.OPTIMISTIC);
            schedule.setDayOfWeek(dayOfWeek);
            schedule.setStartDate(ht1);
            schedule.setEndDate(ht2);
            em.merge(schedule);
            return schedule;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_SCHEDULE");
        }
    }

    public boolean delete(int id) throws Exception{
        Schedule schedule = em.find(Schedule.class, id);

        if(schedule == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_SCHEDULE");
        }
        if(schedule.getPracticedModalities() == null){
            throw new MyEntityCantBeDeletedException("ERROR_DELETING_SCHEDULE");
        }

        try{
            em.remove(schedule);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_SCHEDULE");
        }
    }
}
