package ejbs;

import entities.Administrator;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "AdministratorEJB")
public class AdministratorBean {
    @PersistenceContext
    private EntityManager em;

    public AdministratorBean() {
    }

    public Administrator create(String username, String password, String name, String email) throws Exception {
        if (find(username)!=null){
            throw new Exception("Username '" + username + "' already exists");
        }
        Administrator administrator = new Administrator(username,password,name,email);
        em.persist(administrator);
        return administrator;
    }

    public List<Administrator> all() {
        try {
            return (List<Administrator>) em.createNamedQuery("getAllAdministrators").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_STUDENTS", e);
        }
    }

    public Administrator find(String username) throws Exception {
        try{
            return em.find(Administrator.class, username);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_ADMINISTRATOR", e);
        }
    }

    public Administrator update(String username, String password, String name, String email) throws Exception {
        try{
            Administrator administrator = em.find(Administrator.class, username);

            if(administrator == null){
                throw new Exception("ERROR_FINDING_STUDENT");
            }

            //em.lock(administrator, LockModeType.OPTIMISTIC);
            administrator.setName(name);
            administrator.setEmail(email);
            administrator.setPassword(password);
            em.merge(administrator);
            return administrator;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_STUDENT");
        }
    }

    public boolean delete(String username) throws Exception{
        try{
            Administrator administrator = em.find(Administrator.class, username);

            if(administrator == null){
                throw new Exception("ERROR_FINDING_STUDENT");
            }

            //em.lock(administrator, LockModeType.OPTIMISTIC);
            em.remove(administrator);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_STUDENT");
        }
    }
}
