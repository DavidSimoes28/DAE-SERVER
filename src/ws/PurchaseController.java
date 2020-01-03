package ws;


import dtos.ProductDTO;
import dtos.PurchaseDTO;
import ejbs.ProductBean;
import ejbs.PurchaseBean;
import entities.Payment;
import entities.Product;
import entities.Purchase;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.text.SimpleDateFormat;
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
    @EJB
    private ProductBean productBean;
    @Context
    private SecurityContext securityContext;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static PurchaseDTO toDTO(Purchase purchase) {
        PurchaseDTO purchaseDTO = new PurchaseDTO(
                purchase.getId(),
                purchase.getPartner().getUsername(),
                format.format(purchase.getReleaseDate()),
                purchase.getPrice()
        );
        return purchaseDTO;
    }

    public static PurchaseDTO toDTODetails(Purchase purchase){
        PurchaseDTO purchaseDTO = new PurchaseDTO(
                purchase.getId(),
                purchase.getPartner().getUsername(),
                format.format(purchase.getReleaseDate()),
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
    @RolesAllowed({"Administrator"})
    public List<PurchaseDTO> all() {
        try {
            if(securityContext.isUserInRole("Administrator")) {
                return toDTOs(purchaseBean.all());
            }
            return null;
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_MODALITIES", e);
        }
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"Administrator","Partner"})
    public Response getAdministratorDetails(@PathParam("id") int id) throws Exception {
        if(securityContext.isUserInRole("Administrator") || securityContext.isUserInRole("Partner")) {
            Purchase purchase = purchaseBean.find(id);
            try {
                return Response.status(Response.Status.OK).entity(toDTODetails(purchase)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_GET_MODALITIES", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("{id}/payments")
    @RolesAllowed({"Administrator"})
    public Response getPurchasePayments(@PathParam("id") int id) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            List<Payment> purchasePayments = purchaseBean.findPurchasePayments(id);
            try {
                return Response.status(Response.Status.OK).entity(PaymentController.toDTOs(purchasePayments)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_GET_MODALITIES", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrator"})
    public Response createNewPurchase (PurchaseDTO purchaseDTO) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            Purchase purchase = purchaseBean.create(purchaseDTO.getPartnerUsername(), purchaseDTO.getReleaseDate(), purchaseDTO.getPrice());
            for (ProductDTO product : purchaseDTO.getProducts()) {
                purchaseBean.addProduct(purchase.getId(), product.getId());
            }
            try {
                return Response.status(Response.Status.OK).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_CREATING_MODALITY", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Administrator"})
    public Response updatePurchase (PurchaseDTO purchaseDTO) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            Purchase purchase = purchaseBean.update(purchaseDTO.getId(), purchaseDTO.getPrice());
            try {
                return Response.status(Response.Status.OK).entity(toDTO(purchase)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_UPDATING_MODALITY", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

}
