package ejbs;

import entities.Graduations;
import entities.Modality;
import org.xml.sax.ext.DeclHandler;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Stateless(name = "GraduationEJB")
public class GraduationsBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private ModalityBean modalityBean;

    public GraduationsBean() {
    }

    public Graduations create(String code, String name, int minimumAge, int modality_id) throws Exception {
        if (find(code)!=null){
            throw new Exception("Graduation '" + code + "' already exists");
        }

        Modality modality = modalityBean.find(modality_id);
        Graduations graduations = new Graduations(code,name,minimumAge,modality);
        em.persist(graduations);
        modality.addGraduation(graduations);
        return graduations;
    }

    public List<Graduations> all() {
        try {
            return (List<Graduations>) em.createNamedQuery("getAllGraduations").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_GRADUATIONS", e);
        }
    }

    public Graduations find(String echelon) throws Exception {
        try{
            return em.find(Graduations.class, echelon);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_GRADUATION", e);
        }
    }

    public Graduations update(String code, String name, int minimumAge) throws Exception {
        try{
            Graduations graduations = em.find(Graduations.class, code);

            if(graduations == null){
                throw new Exception("ERROR_FINDING_GRADUATION");
            }

            graduations.setName(name);
            graduations.setMinimumAge(minimumAge);
            em.merge(graduations);
            return graduations;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_GRADUATION");
        }
    }

    public boolean delete(String code) throws Exception{
        try{
            Graduations graduations = em.find(Graduations.class, code);

            if(graduations == null){
                throw new Exception("ERROR_FINDING_GRADUATION");
            }

            em.remove(graduations);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_GRADUATION");
        }
    }
}
