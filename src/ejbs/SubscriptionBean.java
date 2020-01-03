package ejbs;

import entities.*;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
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
    @EJB
    ProductBean productBean;
    @EJB
    PurchaseBean purchaseBean;
    @EJB
    PaymentBean paymentBean;
    @EJB
    ProductTypeBean productTypeBean;

    public Subscription create(String athleteUsername, int modalityId, int scheduleId, int echelonId, int graduationId, Date subscriptionDate, Double subscriptionPrice, int stateId) throws Exception {
        Athlete athlete = athleteBean.find(athleteUsername);
        Modality modality = modalityBean.find(modalityId);
        Schedule schedule = scheduleBean.find(scheduleId);
        Echelon echelon = null;
        Graduations graduations = null;
        if(echelonId>0){ echelon = echelonBean.find(echelonId);}
        if(graduationId>0){ graduations = graduationsBean.find(graduationId);}
        if(subscriptionDate == null || subscriptionPrice<=0){
            throw new MyIllegalArgumentException("State field can't be empty");
        }
        Subscription subscription = new Subscription(athlete,modality,schedule,echelon,graduations,subscriptionDate,subscriptionPrice);
        em.persist(subscription);

        Product product = null;
        for (Product product1 : productBean.all()) {
            if(product1.getTable_name().equals(Subscription.class.getName())){
                product = product1;
            }
        }

        ProductType productType = null;
        for (ProductType productType1 : productTypeBean.all()) {
            if(productType1.getType().toUpperCase().equals("SUBSCRIPTION")){
                productType = productType1;
            }
        }

        if(productType == null){
           productType = productTypeBean.create("Subscription");
        }

        if(product == null){
            product = productBean.createSubscriptionProduct(productType.getId(),"Subscription");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Purchase purchase = purchaseBean.create(athleteUsername, format.format(subscriptionDate), subscriptionPrice);
        purchaseBean.addProduct(purchase.getId(),product.getId());

        Payment payment = paymentBean.create(stateId,purchase.getId(),subscriptionPrice);


        PracticedModality practicedModality = practicedModalityBean.create(modalityId, echelonId, graduationId, athleteUsername);
        practicedModality.addSchedule(schedule);
        return subscription;
    }

    public List<Subscription> all() {
        try {
            return (List<Subscription>) em.createNamedQuery("getAllSubscriptions").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_SUBSCRIPTIONS", e);
        }
    }

    public Subscription find(int id) throws Exception {
        try{
            return em.find(Subscription.class, id);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_SUBSCRIPTION", e);
        }
    }

    public Subscription update(int id,Double price) throws Exception {
        Subscription subscription = em.find(Subscription.class, id);

        if(subscription == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_SUBSCRIPTION");
        }
        try{
            em.lock(subscription, LockModeType.OPTIMISTIC);
            subscription.setSubscriptionPrice(price);
            em.merge(subscription);
            return subscription;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_SUBSCRIPTION");
        }
    }
}
