package ws;

import dtos.EmailDTO;
import dtos.FilterCoachDTO;
import dtos.FilterUserDTO;
import dtos.PartnerDTO;
import ejbs.EmailBean;
import ejbs.PartnerBean;
import ejbs.PurchaseBean;
import entities.*;
import exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/partners")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class PartnerController {
    @EJB
    private PartnerBean partnerBean;
    @EJB
    private PurchaseBean purchaseBean;
    @EJB
    private EmailBean emailBean;
    @Context
    private SecurityContext securityContext;

    public static PartnerDTO toDTO(Partner partner) {
        PartnerDTO partnerDTO = new PartnerDTO(
                partner.getUsername(),
                partner.getPassword(),
                partner.getName(),
                partner.getEmail()
        );
        return partnerDTO;
    }

    public static Set<PartnerDTO> toDTOs(Collection<Partner> administrators) {
        return administrators.stream().map(PartnerController::toDTO).collect(Collectors.toSet());
    }

    @GET
    @Path("/")
    @RolesAllowed({"Administrator"})
    public Set<PartnerDTO> all() {
        try {
            return toDTOs(partnerBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_PARTNERS", e);
        }
    }

    @GET
    @Path("{username}")
    @RolesAllowed({"Administrator","Partner"})
    public Response getAdministratorDetails(@PathParam("username") String username) throws Exception {
        Principal principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Administrator") || principal.getName().equals(username)) {
            Partner partner = partnerBean.find(username);
            try {
                return Response.status(Response.Status.OK).entity(toDTO(partner/*,null*/)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_GET_PARTNERS", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @RolesAllowed({"Administrator","Partner"})
    @Path("{username}/payments")
    public Response getPartnerPayments(@PathParam("username") String username) throws Exception {
        Principal principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Administrator") || principal.getName().equals(username)) {
            List<Payment> partnerPayments = purchaseBean.findPartnerPayments(username);
            try {
                return Response.status(Response.Status.OK).entity(PaymentController.toDTOs(partnerPayments)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_GET_PARTNERS", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Path("/filter")
    @RolesAllowed({"Administrator"})
    public Response getAthleteFilter(FilterUserDTO filterUserDTO) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            Set<Partner> filter = partnerBean.filter(filterUserDTO.getUsername());
            try {
                return Response.status(Response.Status.OK).entity(toDTOs(filter)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_GET_ATHLETES", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrator"})
    public Response createNewAthlete (PartnerDTO partnerDTO) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            partnerBean.create(partnerDTO.getUsername(), partnerDTO.getPassword(), partnerDTO.getName(), partnerDTO.getEmail());
            try {
                return Response.status(Response.Status.CREATED).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_CREATING_PARTNER", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PUT
    @Path("/{username}")
    @RolesAllowed({"Administrator"})
    public Response updateAthlete (PartnerDTO partnerDTO) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            Partner partner = partnerBean.update(partnerDTO.getUsername(), partnerDTO.getName(), partnerDTO.getEmail());
            try {
                return Response.status(Response.Status.CREATED).entity(toDTO(partner)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_UPDATING_PARTNER", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @DELETE
    @Path("/{username}")
    @RolesAllowed({"Administrator"})
    public Response deleteAthlete(@PathParam("username") String username) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            partnerBean.delete(username);
            try {
                return Response.status(Response.Status.OK).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_DELETING_PARTNER", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
