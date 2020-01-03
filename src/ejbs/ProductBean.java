package ejbs;

import entities.Product;
import entities.ProductType;
import entities.Subscription;
import exceptions.MyEntityCantBeDeletedException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.LinkedHashSet;
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

    public Product create(int typeId, String description, Double value,int stock) throws Exception {
        ProductType productType = productTypeBean.find(typeId);
        if(description.equals("") || value <= 0 || stock <= 0){
            throw new MyIllegalArgumentException("Product fields can't be empty");
        }
        Product product = new Product(productType,description,value,stock,Product.class.getName());
        em.persist(product);
        return product;
    }

    public Product createSubscriptionProduct(int typeId, String description) throws Exception {
        ProductType productType = productTypeBean.find(typeId);
        if(description.equals("")){
            throw new MyIllegalArgumentException("Product fields can't be empty");
        }
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
        Product product = em.find(Product.class, id);
        if(product == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_PRODUCT");
        }

        try{
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
        Product product = em.find(Product.class, id);

        if(product == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_PRODUCT");
        }else if(!product.getPurchases().isEmpty()){
            throw new MyEntityCantBeDeletedException("PRODUCT_ALREADY_SOLD");
        }

        try{
            em.remove(product);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PRODUCT");
        }
    }

    public Set<Product> filter(int id, int productTypeId) throws Exception {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> from = criteria.from(Product.class);
        criteria.select(from);

        if(id>0){
            criteria.where(builder.equal(from.get("id"), id));
        }

        if(productTypeId > 0){
            criteria.where(builder.equal(from.get("type"), productTypeBean.find(productTypeId)));
        }

        TypedQuery<Product> query = em.createQuery(criteria);

        Set<Product> result = new LinkedHashSet<>();

        if(id<=0 && productTypeId <= 0 ){
            result.addAll(query.getResultList());
            return result;
        }

        for (Product product : query.getResultList()) {
            if (product.getId()==id){
                result.add(product);
            }
            if (productTypeId==product.getType().getId()){
                result.add(product);
            }
        }

        return result;
    }
}
