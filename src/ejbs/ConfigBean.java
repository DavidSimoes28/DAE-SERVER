package ejbs;

import entities.Administrator;
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
    public ConfigBean() {
    }

    @PostConstruct
    public void populateDB() {
        System.out.println("Seed DB");
        try {
            Administrator administrator = administratorBean.create("admin", "admin", "admin", "admin@mail.com");
            Administrator aaa = administratorBean.create("aaa", "aaa", "aaa", "aaa@aaa.aa");
            Modality judo = modalityBean.create(1,"judo");
            Modality football = modalityBean.create(1,"football");
        }catch (Exception e){
            logger.log(Level.SEVERE,e.getMessage());
        }
    }
}
