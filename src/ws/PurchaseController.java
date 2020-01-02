package ws;


import dtos.PurchaseDTO;
import ejbs.PurchaseBean;
import entities.Product;
import entities.Purchase;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/purchases")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PurchaseController {
    @EJB
    private PurchaseBean purchaseBean;

    public static PurchaseDTO toDTO(Purchase purchase) {
        PurchaseDTO purchaseDTO = new PurchaseDTO(
                purchase.getId(),
                purchase.getPartner().getUsername(),
                purchase.getReleaseDate(),
                purchase.getPrice()
        );
        return purchaseDTO;
    }

    public static PurchaseDTO toDTODetails(Purchase purchase){
        PurchaseDTO purchaseDTO = new PurchaseDTO(
                purchase.getId(),
                purchase.getPartner().getUsername(),
                purchase.getReleaseDate(),
                purchase.getPrice()
        );

        purchaseDTO.setProducts(ProductController.toDTOs(purchase.getProducts()));

        return purchaseDTO;
    }

    public static List<PurchaseDTO> toDTOs(Collection<Purchase> purchases) {
        return purchases.stream().map(PurchaseController::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<PurchaseDTO> all() {
        try {
            return toDTOs(purchaseBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_MODALITIES", e);
        }
    }

    @GET
    @Path("{id}")
    public Response getAdministratorDetails(@PathParam("id") int id) throws Exception {
        Purchase purchase = purchaseBean.find(id);
        try{
            return Response.status(Response.Status.OK).entity(toDTODetails(purchase)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_MODALITIES", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewPurchase (PurchaseDTO purchaseDTO) throws Exception {
        purchaseBean.create(purchaseDTO.getPartnerUsername(),purchaseDTO.getReleaseDate(),purchaseDTO.getPrice());
        try{
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_MODALITY", e);
        }
    }

    @PUT
    @Path("/{id}")
    public Response updatePurchase (PurchaseDTO purchaseDTO) throws Exception {
        Purchase purchase = purchaseBean.update(purchaseDTO.getId(), purchaseDTO.getPrice());
        try{
            return Response.status(Response.Status.OK).entity(toDTO(purchase)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_MODALITY", e);
        }
    }

}
