package ws;

import dtos.FilterModalityDTO;
import dtos.ModalityDTO;
import dtos.ScheduleDTO;
import ejbs.AthleteBean;
import ejbs.CoachBean;
import ejbs.ModalityBean;
import ejbs.ScheduleBean;
import entities.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;


@Path("/modalities")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ModalityController {
    @EJB
    private ModalityBean modalityBean;
    @EJB
    private AthleteBean athleteBean;
    @EJB
    private CoachBean coachBean;
    @EJB
    private ScheduleBean scheduleBean;
    @Context
    private SecurityContext securityContext;

    public static ModalityDTO toDTO(Modality modality) {
        ModalityDTO modalityDTO = new ModalityDTO(
                modality.getId(),
                modality.getName(),
                modality.getSportYear(),
                modality.isActive()
        );
        return modalityDTO;
    }

    public static ModalityDTO toDTODetails(Modality modality) {
        ModalityDTO modalityDTO = new ModalityDTO(
                modality.getId(),
                modality.getName(),
                modality.getSportYear(),
                modality.isActive()
        );
        modalityDTO.setSchedules(ScheduleController.toDTOs(modality.getSchedules()));
        modalityDTO.setEchelons(EchelonController.toDTOs(modality.getEchelons()));
        modalityDTO.setGraduations(GraduationsController.toDTOs(modality.getGraduations()));
        modalityDTO.setAthletes(AthleteController.toDTOs(modality.getModalityAthletes()));
        modalityDTO.setCoaches(CoachController.toDTOs(modality.getModalityCoaches()));
        return modalityDTO;
    }

    public static List<ModalityDTO> toDTOs(Collection<Modality> modalities) {
        return modalities.stream().map(ModalityController::toDTO).collect(Collectors.toList());
    }

    public static List<ModalityDTO> toDTOsDetails(Collection<Modality> modalities) {
        return modalities.stream().map(ModalityController::toDTODetails).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    @RolesAllowed({"Administrator"})
    public List<ModalityDTO> all() {
        try {
            if(securityContext.isUserInRole("Administrator")) {
                return toDTOs(modalityBean.all());
            }
            return null;
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_MODALITIES", e);
        }
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"Administrator"})
    public Response getAdministratorDetails(@PathParam("id") int id) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            Modality modality = modalityBean.find(id);
            try {
                return Response.status(Response.Status.OK).entity(toDTODetails(modality)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_GET_MODALITIES", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("{id}/{username}/schedules")
    @RolesAllowed({"Administrator","Athlete"})
    public Response getPracticedDetails(@PathParam("id") int id,@PathParam("username") String username) throws Exception {
        Principal principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Administrator") || principal.getName().equals(username)) {
            Modality modality = modalityBean.find(id);
            Athlete athlete = athleteBean.find(username);
            List<Schedule> schedules = new ArrayList<>();
            for (PracticedModality practicedModality : athlete.getPracticedModalities()) {
                if (practicedModality.getModality().getId() == modality.getId()) {
                    for (Schedule schedule : practicedModality.getSchedules()) {
                        schedules.add(schedule);
                    }
                }
            }
            try {
                return Response.status(Response.Status.OK).entity(ScheduleController.toDTOs(schedules)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_GET_COACHES", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("{id}/coach/{username}/schedules")
    @RolesAllowed({"Administrator","Coach"})
    public Response getTeachedDetails(@PathParam("id") int id,@PathParam("username") String username) throws Exception {
        Principal principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Administrator") || principal.getName().equals(username)) {
            Modality modality = modalityBean.find(id);
            Coach coach = coachBean.find(username);
            List<Schedule> schedules = new ArrayList<>();
            for (TeachedModality teachedModality : coach.getTeachedModalities()) {
                if (teachedModality.getModality().getId() == modality.getId()) {
                    for (Schedule schedule : teachedModality.getSchedules()) {
                        schedules.add(schedule);
                    }
                }
            }
            try {
                return Response.status(Response.Status.OK).entity(ScheduleController.toDTOs(schedules)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_GET_COACHES", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("{id}/unassigned/schedules")
    @RolesAllowed({"Administrator","Athlete"})
    public Response getUnassignedSchedules(@PathParam("id") int id) throws Exception {
        if(securityContext.isUserInRole("Administrator") || securityContext.isUserInRole("Athlete")) {
            List<Schedule> schedules = modalityBean.missingScheduleFromModality(id);
            try {
                return Response.status(Response.Status.OK).entity(ScheduleController.toDTOs(schedules)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_GET_COACHES", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Path("/filter")
    @RolesAllowed({"Administrator"})
    public Response getAthleteFilter(FilterModalityDTO filterModalityDTO) throws Exception {
        if(securityContext.isUserInRole("Administrator")){
            Set<Modality> filter = modalityBean.filter(filterModalityDTO.getName());

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
    public Response createNewAdministrator (ModalityDTO modalityDTO) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            modalityBean.create(modalityDTO.getName(), modalityDTO.getSportYear(), modalityDTO.isActive());
            try {
                return Response.status(Response.Status.OK).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_CREATING_MODALITY", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PUT
    @Path("/{modalityId}/add/schedule/{scheduleId}")
    @RolesAllowed({"Administrator"})
    public Response addSchedule (@PathParam("modalityId") int modalityId, @PathParam("scheduleId") int scheduleId) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            Modality modality = modalityBean.addScheduleToModality(modalityId, scheduleId);
            try {
                return Response.status(Response.Status.OK).entity(toDTO(modality)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_UPDATING_MODALITY", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PUT
    @Path("/{modalityId}/remove/schedule/{scheduleId}")
    @RolesAllowed({"Administrator"})
    public Response removeSchedule (@PathParam("modalityId") int modalityId, @PathParam("scheduleId") int scheduleId) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            Modality modality = modalityBean.removeScheduleToModality(modalityId, scheduleId);
            try {
                return Response.status(Response.Status.OK).entity(toDTO(modality)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_UPDATING_MODALITY", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Administrator"})
    public Response updateAdministrator (ModalityDTO modalityDTO) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            Modality modality = modalityBean.update(modalityDTO.getId(), modalityDTO.getName());
            try {
                return Response.status(Response.Status.OK).entity(toDTO(modality)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_UPDATING_MODALITY", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Administrator"})
    public Response deleteAdministrator(@PathParam("id") int id) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            modalityBean.delete(id);
            try {
                return Response.status(Response.Status.OK).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_DELETING_MODALITY", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
