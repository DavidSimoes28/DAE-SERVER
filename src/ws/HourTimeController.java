package ws;

import dtos.HourTimeDTO;
import ejbs.HourTimeBean;
import entities.HourTime;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("/hourTime")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class HourTimeController {
    @EJB
    private HourTimeBean hourTimeBean;
    public static HourTimeDTO toDTO(HourTime hourTime) {
        return new HourTimeDTO(
                hourTime.getId(),
                hourTime.getHour(),
                hourTime.getMinutes()
        );
    }

    public static List<HourTimeDTO> toDTOs(Collection<HourTime> hourTimes) {
        return hourTimes.stream().map(HourTimeController::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    @RolesAllowed({"Administrator"})
    public List<HourTimeDTO> all() {
        try {
            return toDTOs(hourTimeBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_COACHES", e);
        }
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"Administrator"})
    public Response getAdministratorDetails(@PathParam("id") int id) throws Exception {
        HourTime hourTime = hourTimeBean.find(id);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(hourTime)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ATHLETES", e);
        }
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrator"})
    public Response createNewAthlete (HourTimeDTO hourTimeDTO) throws Exception {
        hourTimeBean.create(hourTimeDTO.getHour(),hourTimeDTO.getMinutes());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_ATHLETE", e);
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Administrator"})
    public Response deleteAthlete(@PathParam("id") int id) throws Exception {
        hourTimeBean.delete(id);
        try{
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_COACH", e);
        }
    }
}
