package ejbs;

import entities.Product;
import entities.ProductType;
import entities.Subscription;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Stateless(name = "ProductEJB")
public class ProductBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private ProductTypeBean productTypeBean;

    public ProductBean() {
    }

    public Product create(int type_id, String description, Double value,int stock) throws Exception {
        ProductType productType = productTypeBean.find(type_id);
        Product product = new Product(productType,description,value,stock,Product.class.getName());
        em.persist(product);
        return product;
    }

    public Product createSubscriptionProduct(int type_id, String description) throws Exception {
        ProductType productType = productTypeBean.find(type_id);
        Product product = new Product(productType,description,0.0,0, Subscription.class.getName());
        em.persist(product);
        return product;
    }

    public List<Product> all() {
        try {
            return (List<Product>) em.createNamedQuery("getAllProducts").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PRODUCTS", e);
        }
    }

    public Product find(int id) throws Exception {
        try{
            return em.find(Product.class, id);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_PRODUCT", e);
        }
    }

    public Product update(int id, int type_id, String description, Double value, int stock) throws Exception {
        try{
            Product product = em.find(Product.class, id);
            if(product == null){
                throw new Exception("ERROR_FINDING_PRODUCT");
            }

            Product p1 = create(type_id,description,value, stock);
            p1.setParentProduct(product);
            product.addChildrenProducts(p1);

            em.merge(p1);
            return p1;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PRODUCT");
        }
    }

    public boolean delete(int id) throws Exception{
        try{
            Product product = em.find(Product.class, id);

            if(product == null){
                throw new Exception("ERROR_FINDING_PRODUCT");
            }else if(product.getPurchases() != null){
                throw new Exception("PRODUCT_ALREADY_SOLD");
            }
            em.remove(product);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PRODUCT");
        }
    }
}
