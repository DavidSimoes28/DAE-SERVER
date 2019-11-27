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
            Graduations graduations1 = graduationsBean.create( "graduations1");
            Graduations graduations2 = graduationsBean.create( "graduations2");
            Athlete mark = athleteBean.create("mark", "mark", "mark", "mark@mail.com");

            coachBean.enroll(judo.getId(),john.getUsername());
            coachBean.enroll(judo.getId(),mary.getUsername());
            coachBean.unroll(judo.getId(),mary.getUsername());
            athleteBean.enroll(judo.getId(), mark.getUsername());
        }catch (Exception e){
            logger.log(Level.SEVERE,e.getMessage());
        }
    }
}
