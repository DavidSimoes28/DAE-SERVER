package ejbs;

import entities.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton(name = "ConfigEJB")
@Startup
public class ConfigBean {
    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");
    @EJB
    AdministratorBean administratorBean;
    @EJB
    ModalityBean modalityBean;
    @EJB
    GraduationsBean graduationsBean;
    @EJB
    CoachBean coachBean;
    @EJB
    AthleteBean athleteBean;
    @EJB
    PartnerBean partnerBean;
    @EJB
    StateBean stateBean;
    @EJB
    EchelonBean echelonBean;
    @EJB
    PracticedModalityBean practicedModalityBean;
    @EJB
    TeachedModalityBean teachedModalityBean;
    @EJB
    HourTimeBean hourTimeBean;
    @EJB
    ScheduleBean scheduleBean;
    @EJB
    PaymentBean paymentBean;
    @EJB
    ProductBean productBean;
    @EJB
    ProductTypeBean productTypeBean;
    @EJB
    PurchaseBean purchaseBean;
    @EJB
    SubscriptionBean subscriptionBean;
    @EJB
    ClassBean classBean;

    public ConfigBean() {
    }

    @PostConstruct
    public void populateDB() {
        System.out.println("Seed DB");
        try {
            Administrator administrator = administratorBean.create("admin", "admin", "admin", "admin@mail.com");
            Administrator aaa = administratorBean.create("aaa", "aaa", "aaa", "aaa@aaa.aa");
            Coach john = coachBean.create("john","john","john","john@mail.com");
            Coach mary = coachBean.create("mary","mary","mary","mary@mail.com");
            Coach david = coachBean.create("david","david","david","davidsimoes791@gmail.com");
            Athlete mark = athleteBean.create("mark", "mark", "mark", "mark@mail.com");
            Athlete kayle = athleteBean.create("kayle", "kayle", "kayle", "kayle@mail.com");
            Partner charles = partnerBean.create("charles", "charles", "charles","charles@mail.com");

            Modality judo = modalityBean.create("judo",2019,true);
            Modality football = modalityBean.create("football",2019,true);


            Graduations graduations1 = graduationsBean.create( "grade 1",10,judo.getId());
            Graduations graduations2 = graduationsBean.create("grade 2",13,football.getId());

            Echelon echelon1 = echelonBean.create("Senior",18,99,judo.getId());
            Echelon echelon2 = echelonBean.create("Junior",15,17,judo.getId());
            Echelon echelon3 = echelonBean.create("Juvenil",13,14,football.getId());

            State paid = stateBean.create("Paid");
            State not_paid = stateBean.create("Not Paid");
            State partial = stateBean.create("Partial");

            //PracticedModality practicedModality = practicedModalityBean.createWithEchelon(judo.getId(),echelon1.getId(),mark.getName());


            TeachedModality teachedModality = teachedModalityBean.create(judo.getId(),john.getUsername(),echelon1.getId());
            TeachedModality teachedModality1 = teachedModalityBean.create(judo.getId(),john.getUsername(),echelon1.getId());

            HourTime hourTime = hourTimeBean.create(12,0);
            HourTime hourTime1 = hourTimeBean.create(13,0);
            HourTime hourTime2 = hourTimeBean.create(14,0);
            HourTime hourTime3 = hourTimeBean.create(15,30);

            Schedule schedule = scheduleBean.create(1,hourTime.getId(),hourTime1.getId());
            Schedule schedule1 = scheduleBean.create(2,hourTime.getId(),hourTime1.getId());
            Schedule schedule2 = scheduleBean.create(1,hourTime2.getId(),hourTime3.getId());
            Schedule schedule3 = scheduleBean.create(4,hourTime2.getId(),hourTime3.getId());
            Schedule schedule4 = scheduleBean.create(5,hourTime1.getId(),hourTime3.getId());

            judo.addSchedule(schedule);
            judo.addSchedule(schedule1);
            judo.addSchedule(schedule3);
            football.addSchedule(schedule2);
            football.addSchedule(schedule4);

            teachedModality.addSchedule(schedule);
            teachedModality.addSchedule(schedule1);
            teachedModality1.addSchedule(schedule3);

            /*practicedModality.addSchedule(schedule1);
            practicedModality.addSchedule(schedule);
            practicedModality.addSchedule(schedule3);*/
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse("2019-10-20");

            Classes classes = classBean.create(john.getUsername(),schedule.getId(),judo.getId(),"2019-10-20");
            classes.addAthletesPresent(mark);
            mark.addClass(classes);


            ProductType productType = productTypeBean.create("Shoes");
            ProductType productType1 = productTypeBean.create("Balls");
            ProductType productType2 = productTypeBean.create("Subscription");

            Product product = productBean.create(productType.getId(),"best shoes ever",100.0,10);
            Product product1 = productBean.create(productType.getId(),"Sport shoes",50.0,10);
            Product product2 = productBean.create(productType1.getId(),"football balls of adidas",60.0,5);
            Product product3 = productBean.createSubscriptionProduct(productType2.getId(),"Subscription");

            Purchase purchase = purchaseBean.create(charles.getUsername(),date,20.0);
            purchase.addProduct(product1);
            purchase.addProduct(product2);
            Purchase purchase1 = purchaseBean.create(charles.getUsername(),date,100.0);
            purchase1.addProduct(product);

            paymentBean.create(paid.getId(),purchase.getId());
            paymentBean.create(partial.getId(),purchase1.getId());


            Subscription subscription = subscriptionBean.create(mark.getUsername(),judo.getId(),schedule1.getId(),echelon1.getId(),0,date,20.0,paid.getId());
            Subscription subscription1 = subscriptionBean.create(kayle.getUsername(),judo.getId(),schedule1.getId(),echelon1.getId(),0,date,20.0,paid.getId());

        }catch (Exception e){
            logger.log(Level.SEVERE,e.getMessage());
        }
    }
}
