package ejbs;

import entities.PracticedModality;
import entities.TeachedModality;
import entities.User;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless(name = "EmailEJB")
public class EmailBean {
    @Resource(name = "java:/jboss/mail/gmail")
    private Session mailSession;
    private static final Logger logger = Logger.getLogger("EmailBean.logger");
    @PersistenceContext
    private EntityManager em;
    @EJB
    private ModalityBean modalityBean;
    @EJB
    private EchelonBean echelonBean;
    @EJB
    private CoachBean coachBean;
    @EJB
    private AthleteBean athleteBean;

    public void send(String to, String subject, String text) {
        Thread emailJob = new Thread(() -> {
            Message message = new MimeMessage(mailSession);
            Date timestamp = new Date();
            try {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
                message.setSubject(subject);
                message.setText(text);
                message.setSentDate(timestamp);
                Transport.send(message);
            } catch (MessagingException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });
        emailJob.start();
    }

    public Set<User> allUsers() throws Exception {
        Set<User> result = new LinkedHashSet<>();
        result.addAll(coachBean.all());
        result.addAll(athleteBean.all());
        return result;
    }

    public Set<User> filter(int modalityId, int echelonId) throws Exception {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<PracticedModality> criteriaPracticed = builder.createQuery(PracticedModality.class);
        Root<PracticedModality> fromPracticed = criteriaPracticed.from(PracticedModality.class);
        criteriaPracticed.select(fromPracticed);


        CriteriaQuery<TeachedModality> criteriaTeached = builder.createQuery(TeachedModality.class);
        Root<TeachedModality> fromTeached = criteriaTeached.from(TeachedModality.class);
        criteriaTeached.select(fromTeached);

        if(modalityId > 0){
            criteriaPracticed.where(builder.equal(fromPracticed.get("modality"), modalityBean.find(modalityId)));
        }
        if(echelonId > 0){
            criteriaPracticed.where(builder.equal(fromPracticed.get("echelon"), echelonBean.find(echelonId)));
        }

        if(modalityId > 0){
            criteriaTeached.where(builder.equal(fromPracticed.get("modality"), modalityBean.find(modalityId)));
        }
        if(echelonId > 0){
            criteriaTeached.where(builder.equal(fromPracticed.get("echelon"), echelonBean.find(echelonId)));
        }
        TypedQuery<PracticedModality> queryPracticed = em.createQuery(criteriaPracticed);
        TypedQuery<TeachedModality> queryTeached = em.createQuery(criteriaTeached);
        Set<User> result = new LinkedHashSet<>();

        if(modalityId <= 0 && echelonId<=0){
            result.addAll(coachBean.all());
            result.addAll(athleteBean.all());
            return result;
        }

        for (TeachedModality teachedModality : queryTeached.getResultList()) {
            if(teachedModality.getModality() != null && modalityId == teachedModality.getModality().getId()){
                result.add(teachedModality.getCoach());
            }
            if(teachedModality.getEchelon() != null && echelonId == teachedModality.getEchelon().getId()){
                result.add(teachedModality.getCoach());
            }
        }
        for (PracticedModality practicedModality : queryPracticed.getResultList()) {
            if(practicedModality.getModality() != null && modalityId == practicedModality.getModality().getId()){
                result.add(practicedModality.getAthlete());
            }
            if(practicedModality.getEchelon() != null && echelonId == practicedModality.getEchelon().getId()){
                result.add(practicedModality.getAthlete());
            }
        }

        return result;
    }
}
