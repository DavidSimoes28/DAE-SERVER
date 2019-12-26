package ws;

import dtos.EmailDTO;
import dtos.PartnerDTO;
import ejbs.EmailBean;
import ejbs.PartnerBean;
import entities.Athlete;
import entities.Modality;
import entities.Partner;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    private EmailBean emailBean;
    public static PartnerDTO toDTO(Partner partner) {
        PartnerDTO partnerDTO = new PartnerDTO(
                partner.getUsername(),
                partner.getPassword(),
                partner.getName(),
                partner.getEmail()
        );
        return partnerDTO;
    }

    public static Set<PartnerDTO> toDTOs(List<Partner> administrators) {
        return administrators.stream().map(PartnerController::toDTO).collect(Collectors.toSet());
    }

    @GET
    @Path("/")
    public Set<PartnerDTO> all() {
        try {
            return toDTOs(partnerBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_PARTNERS", e);
        }
    }

    @GET
    @Path("{username}")
    public Response getAdministratorDetails(@PathParam("username") String username) throws Exception {
        Partner partner = partnerBean.find(username);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(partner/*,null*/)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_PARTNERS", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewAthlete (PartnerDTO partnerDTO) throws Exception {
        partnerBean.create(partnerDTO.getUsername(), partnerDTO.getPassword(), partnerDTO.getName(), partnerDTO.getEmail(),0.0);
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_PARTNER", e);
        }
    }
    @POST
    @Path("{username}/email/send")
    public Response sendEmail(@PathParam("username") String username, EmailDTO emailDTO) throws MyEntityNotFoundException, MessagingException {
        Partner partner = partnerBean.find(username);
        if(partner == null){
            throw new MyEntityNotFoundException("student not found");
        }
        emailBean.send(partner.getUsername(),emailDTO.getSubject(),emailDTO.getMessage());
        return Response.status(Response.Status.OK).entity("E-mail sent").build();

    }
    @PUT
    @Path("/{username}")
    public Response updateAthlete (PartnerDTO partnerDTO) throws Exception {
        Partner partner = partnerBean.update(partnerDTO.getUsername(),partnerDTO.getPassword(),partnerDTO.getName(),partnerDTO.getEmail());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(partner)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_PARTNER", e);
        }
    }

    @DELETE
    @Path("/{username}")
    public Response deleteAthlete(@PathParam("username") String username) throws Exception {
        partnerBean.delete(username);
        try{
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_PARTNER", e);
        }
    }
}
