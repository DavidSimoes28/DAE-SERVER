package ejbs;

import entities.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
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
    StateBean stateBean;
    @EJB
    EchelonBean echelonBean;
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
            Modality judo = modalityBean.create("judo");
            Modality football = modalityBean.create("football");
            Graduations graduations1 = graduationsBean.create( "grade 1","grade 1",10);
            Graduations graduations2 = graduationsBean.create( "grade 2","grade 2",13);
            Athlete mark = athleteBean.create("mark", "mark", "mark", "mark@mail.com");
            State paid = stateBean.create("Paid");
            State not_paid = stateBean.create("Not Paid");
            State partial = stateBean.create("Partial");
            Echelon echelon1 = echelonBean.create("Senior",18,99);
            Echelon echelon2 = echelonBean.create("Junior",15,17);
            Echelon echelon3 = echelonBean.create("Juvenil",13,14);

            coachBean.enroll(judo.getId(),john.getUsername());
            coachBean.enroll(judo.getId(),mary.getUsername());
            coachBean.unroll(judo.getId(),mary.getUsername());
            athleteBean.enroll(judo.getId(), mark.getUsername());
        }catch (Exception e){
            logger.log(Level.SEVERE,e.getMessage());
        }
    }
}
