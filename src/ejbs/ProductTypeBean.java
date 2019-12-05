package ejbs;

import entities.ProductType;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

@Stateless(name = "ProductTypeEJB")
public class ProductTypeBean {
    @PersistenceContext
    private EntityManager em;

    public ProductTypeBean(){

    }

    public ProductType create(String type) throws Exception {
        if (find(type)!=null){
            throw new Exception("Type of product '" + type + "' already exists");
        }
        ProductType productType = new ProductType(type);
        em.persist(productType);
        return productType;
    }

    public Set<ProductType> all() {
        try {
            return (Set<ProductType>) em.createNamedQuery("getAllProductTypes").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PRODUCT_TYPE", e);
        }
    }

    public ProductType find(String type) throws Exception {
        try{
            return em.find(ProductType.class, type);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_PRODUCT_TYPE", e);
        }
    }
}
