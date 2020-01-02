package ws;

import dtos.ScheduleDTO;
import ejbs.ScheduleBean;
import entities.Schedule;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("/schedules")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ScheduleController {
    @EJB
    private ScheduleBean scheduleBean;
    public static ScheduleDTO toDTO(Schedule schedule) {
        return new ScheduleDTO(
                schedule.getId(),
                schedule.getDayOfWeek().getValue(),
                HourTimeController.toDTO(schedule.getStartDate()),
                HourTimeController.toDTO(schedule.getEndDate())
        );
    }

    public static List<ScheduleDTO> toDTOs(Collection<Schedule> schedules) {
        return schedules.stream().map(ScheduleController::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    @RolesAllowed({"Administrator"})
    public List<ScheduleDTO> all() {
        try {
            return toDTOs(scheduleBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_COACHES", e);
        }
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"Administrator"})
    public Response getAdministratorDetails(@PathParam("id") int id) throws Exception {
        Schedule schedule = scheduleBean.find(id);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(schedule)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ATHLETES", e);
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Administrator"})
    public Response updateAthlete (ScheduleDTO scheduleDTO) throws Exception {
        Schedule schedule = scheduleBean.update(scheduleDTO.getId(),scheduleDTO.getDayOfWeek(),scheduleDTO.getStartDate().getId(),scheduleDTO.getEndDate().getId());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(schedule)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_ATHLETE", e);
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Administrator"})
    public Response deleteAthlete(@PathParam("id") int id) throws Exception {
        scheduleBean.delete(id);
        try{
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_COACH", e);
        }
    }
}
