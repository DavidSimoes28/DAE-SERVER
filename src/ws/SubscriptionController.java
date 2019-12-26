package ws;

import dtos.SubscriptionDTO;
import ejbs.SubscriptionBean;
import entities.Subscription;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/subscriptions")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class SubscriptionController {
    @EJB
    private SubscriptionBean subscriptionBean;

    public static SubscriptionDTO toDTO(Subscription subscription) {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO(
                subscription.getId(),
                subscription.getAthlete().getUsername(),
                subscription.getModality().getId(),
                subscription.getSchedule().getId(),
                subscription.getEchelon().getId(),
                subscription.getGraduations().getId(),
                subscription.getSubscriptionDate(),
                subscription.getSubscriptionPrice()
        );
        return subscriptionDTO;
    }

    public static List<SubscriptionDTO> toDTOs(List<Subscription> administrators) {
        return administrators.stream().map(SubscriptionController::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<SubscriptionDTO> all() {
        try {
            return toDTOs(subscriptionBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ADMINISTRATORS", e);
        }
    }

    @GET
    @Path("{id}")
    public Response getSubscriptionDetails(@PathParam("id") int id) throws Exception {
        Subscription subscription = subscriptionBean.find(id);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(subscription)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ADMINISTRATORS", e);
        }
    }

    /*@POST
    @Path("/")
    public Response createNewAdministrator (AdministratorDTO administratorDTO) throws Exception {
        administratorBean.create(administratorDTO.getUsername(), administratorDTO.getPassword(), administratorDTO.getName(), administratorDTO.getEmail());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_ADMINISTRATOR", e);
        }
    }

    @PUT
    @Path("/{username}")
    public Response updateAdministrator (AdministratorDTO administratorDTO) throws Exception {
        Administrator administrator = administratorBean.update(administratorDTO.getUsername(),administratorDTO.getPassword(),administratorDTO.getName(),administratorDTO.getEmail());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(administrator)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_ADMINISTRATOR", e);
        }
    }*/

}
