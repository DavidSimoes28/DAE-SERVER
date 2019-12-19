package ejbs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "SubscriptionEJB")
public class SubscriptionBean {
    @PersistenceContext
    private EntityManager em;
}
