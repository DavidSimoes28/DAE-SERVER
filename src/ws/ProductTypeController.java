package ws;

import dtos.ProductTypeDTO;
import ejbs.ProductTypeBean;
import entities.ProductType;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("/productTypes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductTypeController {

    @EJB
    private ProductTypeBean productTypeBean;

    public static ProductTypeDTO toDTO(ProductType productType) {
        ProductTypeDTO productTypeDTO = new ProductTypeDTO(
                productType.getId(),
                productType.getType()

        );
        return productTypeDTO;
    }

    public static List<ProductTypeDTO> toDTOs(Collection<ProductType> productTypes) {
        return productTypes.stream().map(ProductTypeController::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ProductTypeDTO> all() {
        try {
            return toDTOs(productTypeBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_CLASSES", e);
        }
    }

    @GET
    @Path("/{id}")
    public Response getProductDetails(@PathParam("id") int id) throws Exception {
        ProductType productType = productTypeBean.find(id);
        try {
            return Response.status(Response.Status.OK).entity(toDTO(productType)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_PRODUCTS", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewProductType (ProductTypeDTO productTypeDTO) throws Exception {
        productTypeBean.create(productTypeDTO.getType());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_PRODUCT_TYPE", e);
        }
    }

}
