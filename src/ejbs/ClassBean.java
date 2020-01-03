package ejbs;

import entities.*;
import exceptions.MyEntityCantBeDeletedException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Stateless(name = "ClassEJB")
public class ClassBean {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private ModalityBean modalityBean;
    @EJB
    private CoachBean coachBean;
    @EJB
    private AthleteBean athleteBean;
    @EJB
    private ScheduleBean scheduleBean;


    public Classes create(String coach_username, int schedule_id, int modality_id, String dateString) throws Exception {
        Coach coach = coachBean.find(coach_username);
        Schedule schedule = scheduleBean.find(schedule_id);
        Modality modality = modalityBean.find(modality_id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateString);
        Classes classes = new Classes(coach,schedule,modality,date);
        em.persist(classes);
        coach.addClasses(classes);
        return classes;
    }

    public List<Classes> all() {
        try {
            return em.createNamedQuery("getAllClasses").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_COACHES", e);
        }
    }

    public Classes find(int classId) throws Exception {
        try{
            return em.find(Classes.class, classId);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_COACH", e);
        }
    }

    public List<Athlete> findNotPresentAthletes(int classId) throws Exception {
        try{
            Classes classes = find(classId);
            Set<PracticedModality> practicedModalities = classes.getModality().getPracticedModalities();
            List<Athlete> athletesNotPresent = new ArrayList<>();
            Set<Athlete> present = classes.getAthletesPresent();
            List<String> usernamePresent = new ArrayList<>();
            List<Athlete> allAthletes = new ArrayList<>();

            for (Athlete athlete : present) {
                usernamePresent.add(athlete.getUsername());
            }

            for (PracticedModality practicedModality : practicedModalities) {
                if(practicedModality.getModality().getId() == classes.getModality().getId()){
                    allAthletes.add(practicedModality.getAthlete());
                }
            }

            int i = 0;
            for (Athlete Athlete : allAthletes) {
                for (String username : usernamePresent) {
                    if(username.equals(Athlete.getUsername())){
                        i = 1;
                    }
                }
                if(i!=1){
                    athletesNotPresent.add(Athlete);
                    usernamePresent.add(Athlete.getUsername());
                }
                i=0;
            }
            return athletesNotPresent;
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_COACH", e);
        }
    }

    public Classes update(int classId,String coachUsername, int scheduleId) throws Exception {
        Classes classes = find(classId);
        Coach coach = em.find(Coach.class, coachUsername);
        Schedule schedule = scheduleBean.find(scheduleId);
        if(classes == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_CLASS");
        }
        if(coach == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_COACH");
        }
        if(schedule == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_SCHEDULE");
        }

        try{
            classes.getCoach().removeClasses(classes);
            classes.setCoach(coach);
            classes.setSchedule(schedule);
            return classes;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_COACH");
        }
    }

    public Classes addPresentAthlete(int classId,String athlete_username) throws Exception {
        Classes classes = find(classId);
        Athlete athlete = athleteBean.find(athlete_username);

        if(classes == null){
            throw new Exception("ERROR_FINDING_CLASS");
        }

        try{
            classes.getAthletesPresent().add(athlete);
            return classes;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_CLASS");
        }
    }

    public Classes removePresentAthlete(int classId,String athlete_username) throws Exception {
        Classes classes = find(classId);
        Athlete athlete = athleteBean.find(athlete_username);

        if(classes == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_CLASS");
        }

        try{
            classes.getAthletesPresent().remove(athlete);
            return classes;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_COACH");
        }
    }

    public boolean delete(int classId) throws Exception{
        Classes classes = em.find(Classes.class, classId);

        if(classes == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_COACH");
        }
        Date currentDate = new Date();

        if(classes.getDate().after(currentDate)){
            throw new MyEntityCantBeDeletedException("ERROR_FINDING_COACH");
        }

        if(!classes.getAthletesPresent().isEmpty()){
            throw new MyEntityCantBeDeletedException("ERROR_FINDING_COACH");
        }

        try{
            em.lock(classes, LockModeType.OPTIMISTIC);
            classes.getCoach().getClasses().remove(classes);
            em.remove(classes);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_COACH");
        }
    }
}
