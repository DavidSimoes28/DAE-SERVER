package ejbs;

import entities.Administrator;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Stateless(name = "AdministratorEJB")
public class AdministratorBean {
    @PersistenceContext
    private EntityManager em;

    public AdministratorBean() {
    }

    public Administrator create(String username, String password, String name, String email) throws MyEntityExistsException, MyEntityNotFoundException, MyIllegalArgumentException {
        if (find(username)!=null){
            throw new MyEntityExistsException("Username '" + username + "' already exists");
        }
        if(username.equals("") || password.equals("") || name.equals("") || email.equals("")){
            throw new MyIllegalArgumentException("Administrator fields can't be empty");
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

    public Administrator find(String username) throws MyEntityNotFoundException {
        try{
            return em.find(Administrator.class, username);
        } catch (Exception e) {
            throw new MyEntityNotFoundException("ERROR_FINDING_ADMINISTRATOR");
        }
    }

    public Administrator update(String username, String name, String email) throws Exception {
        Administrator administrator = em.find(Administrator.class, username);

        if(administrator == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_STUDENT");
        }

        if(username.equals("") || name.equals("") || email.equals("")){
            throw new MyIllegalArgumentException("Administrator fields can't be empty");
        }

        try{
            em.lock(administrator, LockModeType.OPTIMISTIC);
            administrator.setName(name);
            administrator.setEmail(email);
            em.merge(administrator);
            return administrator;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_STUDENT");
        }
    }

    public boolean delete(String username) throws Exception{
        Administrator administrator = em.find(Administrator.class, username);

        if(administrator == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_STUDENT");
        }

        try{
            em.lock(administrator, LockModeType.OPTIMISTIC);
            em.remove(administrator);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_STUDENT");
        }
    }

    public Set<Administrator> filter(String username) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Administrator> criteria = builder.createQuery(Administrator.class);
        Root<Administrator> from = criteria.from(Administrator.class);
        criteria.select(from);

        if(!username.equals(null)){
            criteria.where(builder.equal(from.get("username"), username));
        }

        TypedQuery<Administrator> query = em.createQuery(criteria);
        Set<Administrator> result = new LinkedHashSet<>();

        if((username.equals("") || username.equals(null))){
            for (Administrator administrator : all()) {
                result.add(administrator);
            }
            return result;
        }


        if(!username.equals(null) && !username.equals("")){
            for (Administrator administrator : query.getResultList()) {
                result.add(administrator);
            }
        }

        return result;
    }
}
