package ejbs;

import entities.*;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless(name = "SubscriptionEJB")
public class SubscriptionBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    ModalityBean modalityBean;
    @EJB
    GraduationsBean graduationsBean;
    @EJB
    AthleteBean athleteBean;
    @EJB
    EchelonBean echelonBean;
    @EJB
    ScheduleBean scheduleBean;
    @EJB
    PracticedModalityBean practicedModalityBean;

    public Subscription createWithGraduation(String athleteUsername, int modalityId, int scheduleId, int graduationsId, Date subscriptionDate, Double subscriptionPrice) throws Exception {
        Athlete athlete = athleteBean.find(athleteUsername);
        Modality modality = modalityBean.find(modalityId);
        Schedule schedule = scheduleBean.find(scheduleId);
        Graduations graduations = graduationsBean.find(graduationsId);
        Subscription subscription = new Subscription(athlete,modality,schedule,graduations,subscriptionDate,subscriptionPrice);
        em.persist(subscription);
        PracticedModality practicedModalityWithEchelon = practicedModalityBean.createWithGraduation(modalityId, graduationsId, athleteUsername);
        practicedModalityWithEchelon.addSchedule(schedule);
        return subscription;
    }

    public Subscription createWithEchelon(String athleteUsername, int modalityId, int scheduleId, int echelonId, Date subscriptionDate, Double subscriptionPrice) throws Exception {
        Athlete athlete = athleteBean.find(athleteUsername);
        Modality modality = modalityBean.find(modalityId);
        Schedule schedule = scheduleBean.find(scheduleId);
        Echelon echelon = echelonBean.find(echelonId);
        Subscription subscription = new Subscription(athlete,modality,schedule,echelon,subscriptionDate,subscriptionPrice);
        em.persist(subscription);

        PracticedModality practicedModalityWithEchelon = practicedModalityBean.createWithEchelon(modalityId, echelonId, athleteUsername);
        practicedModalityWithEchelon.addSchedule(schedule);
        return subscription;
    }

    public List<Subscription> all() {
        try {
            return (List<Subscription>) em.createNamedQuery("getAllSubscriptions").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PRODUCTS", e);
        }
    }

    public Subscription find(int id) throws Exception {
        try{
            return em.find(Subscription.class, id);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_ADMINISTRATOR", e);
        }
    }

    public Subscription update(int id,Double price) throws Exception {
        try{
            Subscription subscription = em.find(Subscription.class, id);

            if(subscription == null){
                throw new Exception("ERROR_FINDING_STUDENT");
            }

            subscription.setSubscriptionPrice(price);
            //em.lock(administrator, LockModeType.OPTIMISTIC);

            em.merge(subscription);
            return subscription;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_STUDENT");
        }
    }
}
