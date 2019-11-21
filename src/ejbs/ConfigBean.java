package ejbs;

import entities.Administrator;
import entities.Graduations;
import entities.Modality;

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
    public ConfigBean() {
    }

    @PostConstruct
    public void populateDB() {
        System.out.println("Seed DB");
        try {
            Administrator administrator = administratorBean.create("admin", "admin", "admin", "admin@mail.com");
            Administrator aaa = administratorBean.create("aaa", "aaa", "aaa", "aaa@aaa.aa");
            Modality judo = modalityBean.create("judo");
            Modality football = modalityBean.create("football");
            Graduations graduations1 = graduationsBean.create( "graduations1");
            Graduations graduations2 = graduationsBean.create( "graduations2");
        }catch (Exception e){
            logger.log(Level.SEVERE,e.getMessage());
        }
    }
}
