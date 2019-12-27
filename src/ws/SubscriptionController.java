package ws;

import dtos.SubscriptionDTO;
import ejbs.PaymentBean;
import ejbs.ProductBean;
import ejbs.SubscriptionBean;
import entities.Product;
import entities.Subscription;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("/subscriptions")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class SubscriptionController {
    @EJB
    private SubscriptionBean subscriptionBean;

    public static SubscriptionDTO toDTO(Subscription subscription) {
        SubscriptionDTO subscriptionDTO = null;
        if(subscription.getEchelon() == null || subscription.getEchelon().getId() == -1){
            subscriptionDTO = new SubscriptionDTO(
                    subscription.getId(),
                    subscription.getAthlete().getUsername(),
                    subscription.getModality().getId(),
                    subscription.getSchedule().getId(),
                    -1,
                    subscription.getGraduations().getId(),
                    subscription.getSubscriptionDate(),
                    subscription.getSubscriptionPrice()
            );
        }else if(subscription.getGraduations() == null || subscription.getGraduations().getId() == -1){
            subscriptionDTO = new SubscriptionDTO(
                    subscription.getId(),
                    subscription.getAthlete().getUsername(),
                    subscription.getModality().getId(),
                    subscription.getSchedule().getId(),
                    subscription.getEchelon().getId(),
                    -1,
                    subscription.getSubscriptionDate(),
                    subscription.getSubscriptionPrice()
            );
        }else{
            subscriptionDTO = new SubscriptionDTO(
                    subscription.getId(),
                    subscription.getAthlete().getUsername(),
                    subscription.getModality().getId(),
                    subscription.getSchedule().getId(),
                    subscription.getEchelon().getId(),
                    subscription.getGraduations().getId(),
                    subscription.getSubscriptionDate(),
                    subscription.getSubscriptionPrice()
            );
        }
        return subscriptionDTO;
    }

    public static List<SubscriptionDTO> toDTOs(List<Subscription> subscriptions) {
        return subscriptions.stream().map(SubscriptionController::toDTO).collect(Collectors.toList());
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

    @POST
    @Path("/")
    public Response createNewSubscription (SubscriptionDTO subscriptionDTO) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        subscriptionDTO.setSubscriptionDate(date);

        Subscription subscription = subscriptionBean.create(subscriptionDTO.getAthleteUsername(), subscriptionDTO.getModalityId(), subscriptionDTO.getScheduleId(), subscriptionDTO.getEchelonId(), subscriptionDTO.getGraduationId(),subscriptionDTO.getSubscriptionDate(), subscriptionDTO.getSubscriptionPrice(), subscriptionDTO.getStateId());

        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_ADMINISTRATOR", e);
        }
    }
}
