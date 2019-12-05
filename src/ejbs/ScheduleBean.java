package ejbs;

import entities.Schedule;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "ScheduleEJB")
public class ScheduleBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private ModalityBean modalityBean;

    public ScheduleBean() {
    }

    /*public Schedule create(String username, String password, String name, String email) throws Exception {
        if (find(username)!=null){
            throw new Exception("Username '" + username + "' already exists");
        }
        Partner partner = new Partner(username,password,name,email);
        //Modality modality = modalityBean.find(modalityID);
        //athlete.addModality(modality);
        em.persist(partner);
        return partner;
    }

    public List<Partner> all() {
        try {
            return (List<Partner>) em.createNamedQuery("getAllPartners").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ATHLETE", e);
        }
    }

    public Partner find(String username) throws Exception {
        try{
            return em.find(Partner.class, username);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_PARTNER", e);
        }
    }

    public Partner update(String username, String password, String name, String email) throws Exception {
        try{
            Partner partner = em.find(Partner.class, username);

            if(partner == null){
                throw new Exception("ERROR_FINDING_PARTNER");
            }

            //em.lock(administrator, LockModeType.OPTIMISTIC);
            partner.setName(name);
            partner.setEmail(email);
            partner.setPassword(password);
            em.merge(partner);
            return partner;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PARTNER");
        }
    }

    public boolean delete(String username) throws Exception{
        try{
            Partner partner = em.find(Partner.class, username);

            if(partner == null){
                throw new Exception("ERROR_FINDING_PARTNER");
            }

            //em.lock(coach, LockModeType.OPTIMISTIC);
            em.remove(partner);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PARTNER");
        }
    }

    public Partner enroll(int modalityId, String athleteUsername) throws Exception {

        Modality modality = modalityBean.find(modalityId);
        Partner partner = find(athleteUsername);
        try{
            if (partner.getModalities().contains(modality) != modality.getAthletes().contains(partner)){
                throw new EJBException("ERROR_FINDING_PARTNER");
            }
            partner.addModality(modality);
            //modality.addPartner(partner);
            em.merge(partner);
            return partner;
        }catch (Exception e){
            throw new EJBException("ERROR_FINDING_PARTNER", e);
        }
    }

    public Partner unroll(int modalityId, String athleteUsername) throws Exception {

        Modality modality = modalityBean.find(modalityId);
        Partner partner = find(athleteUsername);
        try{
            if (partner.getModalities().contains(modality) != modality.getAthletes().contains(partner)){
                throw new EJBException("ERROR_FINDING_PARTNER");
            }
            partner.removeModality(modality);
            //modality.removePartner(partner);
            em.merge(partner);
            return partner;
        }catch (Exception e){
            throw new EJBException("ERROR_FINDING_PARTNER", e);
        }
    }*/
}
