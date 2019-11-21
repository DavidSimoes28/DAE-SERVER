package ejbs;




import entities.Coach;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

    @Stateless(name = "CoachEJB")
    public class CoachBean {
        @PersistenceContext
        private EntityManager em;

        public CoachBean() {
        }

        public Coach create(String username, String password, String name, String email) throws Exception {
            if (find(username)!=null){
                throw new Exception("Username '" + username + "' already exists");
            }
            Coach coach = new Coach(username,password,name,email);
            em.persist(coach);
            return coach;
        }

        public List<Coach> all() {
            try {
                return (List<Coach>) em.createNamedQuery("getAllCoaches").getResultList();
            } catch (Exception e) {
                throw new EJBException("ERROR_RETRIEVING_COACHES", e);
            }
        }

        public Coach find(String username) throws Exception {
            try{
                return em.find(Coach.class, username);
            } catch (Exception e) {
                throw new Exception("ERROR_FINDING_COACH", e);
            }
        }

        public Coach update(String username, String password, String name, String email) throws Exception {
            try{
                Coach coach = em.find(Coach.class, username);

                if(coach == null){
                    throw new Exception("ERROR_FINDING_COACH");
                }

                //em.lock(coach, LockModeType.OPTIMISTIC);
                coach.setName(name);
                coach.setEmail(email);
                coach.setPassword(password);
                em.merge(coach);
                return coach;
            }catch (Exception e){
                throw new Exception("ERROR_FINDING_COACH");
            }
        }

        public boolean delete(String username) throws Exception{
            try{
                Coach coach = em.find(Coach.class, username);

                if(coach == null){
                    throw new Exception("ERROR_FINDING_COACH");
                }

                //em.lock(coach, LockModeType.OPTIMISTIC);
                em.remove(coach);
                return true;
            }catch (Exception e){
                throw new Exception("ERROR_FINDING_COACH");
            }
        }
    }


