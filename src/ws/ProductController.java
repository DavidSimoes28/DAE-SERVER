package ws;

import dtos.ProductDTO;
import ejbs.ProductBean;
import entities.Product;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/products")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductController {
    @EJB
    private ProductBean productBean;

    public static ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO(
                product.getId(),
                product.getType().getId(),
                product.getDescription(),
                product.getValueInEur(),
                product.getStock()
        );
        return productDTO;
    }

    public static List<ProductDTO> toDTOs(Collection<Product> administrators) {
        return administrators.stream().map(ProductController::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ProductDTO> all() {
        try {
            return toDTOs(productBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_PRODUCTS", e);
        }
    }

    @GET
    @Path("/{id}")
    public Response getProductDetails(@PathParam("id") int id) throws Exception {
        Product product = productBean.find(id);
        try {
            return Response.status(Response.Status.OK).entity(toDTO(product)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_PRODUCTS", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewProduct (ProductDTO productDTO) throws Exception {
        productBean.create(productDTO.getProductTypeId(), productDTO.getDescription(), productDTO.getValueInEur(), productDTO.getStock());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_PRODUCT", e);
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateProduct (ProductDTO productDTO) throws Exception {
        Product product = productBean.update(productDTO.getId(),productDTO.getProductTypeId(),productDTO.getDescription(),productDTO.getValueInEur(), productDTO.getStock());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(product)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_PRODUCT", e);
        }
    }
}
