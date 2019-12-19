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

    @PersistenceContext
    private EntityManager em;


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
            Modality judo = modalityBean.create("judo",2019,true);
            Modality football = modalityBean.create("football",2019,true);
            Graduations graduations1 = graduationsBean.create( "grade 1","grade 1",10,judo.getId());
            Graduations graduations2 = graduationsBean.create( "grade 2","grade 2",13,football.getId());
            Athlete mark = athleteBean.create("mark", "mark", "mark", "mark@mail.com");
            Partner charles = partnerBean.create("charles", "charles", "charles","charles@mail.com",15.0);
            State paid = stateBean.create("Paid");
            State not_paid = stateBean.create("Not Paid");
            State partial = stateBean.create("Partial");
            Echelon echelon1 = echelonBean.create("Senior",18,99,judo.getId());
            Echelon echelon2 = echelonBean.create("Junior",15,17,judo.getId());
            Echelon echelon3 = echelonBean.create("Juvenil",13,14,football.getId());

            practicedModalityBean.createWithEchelon(judo.getId(),echelon1.getName(),mark.getName());

        }catch (Exception e){
            logger.log(Level.SEVERE,e.getMessage());
        }
    }
}
