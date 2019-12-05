package ejbs;

import entities.HourTime;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Stateless(name = "HourTimeEJB")
public class HourTimeBean {
    @PersistenceContext
    private EntityManager em;

    public HourTimeBean() {
    }

    public HourTime create(int hour,int minutes){
        HourTime hourTime = new HourTime(hour, minutes);
        em.persist(hourTime);
        return hourTime;
    }

        public List<HourTime> all() {
        try {
            return (List<HourTime>) em.createNamedQuery("getAllHours").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_HOUR_TIMES", e);
        }
    }

    public HourTime find(int id) throws Exception {
        try{
            return em.find(HourTime.class, id);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_HOUR_TIME", e);
        }
    }

    public boolean delete(int id) throws Exception{
        try{
            HourTime hourTime = em.find(HourTime.class, id);

            if(hourTime == null){
                throw new Exception("ERROR_FINDING_HOUR_TIME");
            }
            if(hourTime.getSchedulesStart() == null){
                throw new Exception("ERROR_DELETING_HOUR_TIME");
            }
            if(hourTime.getSchedulesEnd() == null){
                throw new Exception("ERROR_DELETING_HOUR_TIME");
            }

            em.remove(hourTime);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_HOUR_TIME");
        }
    }
}
