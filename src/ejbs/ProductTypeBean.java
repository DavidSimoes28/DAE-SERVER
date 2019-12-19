package ejbs;

import entities.ProductType;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Stateless(name = "ProductTypeEJB")
public class ProductTypeBean {
    @PersistenceContext
    private EntityManager em;

    public ProductTypeBean(){

    }

    public ProductType create(String type) throws Exception {
        ProductType productType = new ProductType(type);
        em.persist(productType);
        return productType;
    }

    public List<ProductType> all() {
        try {
            return (List<ProductType>) em.createNamedQuery("getAllProductTypes").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PRODUCT_TYPE", e);
        }
    }

    public ProductType find(int id) throws Exception {
        try{
            return em.find(ProductType.class, id);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_PRODUCT_TYPE", e);
        }
    }
}
