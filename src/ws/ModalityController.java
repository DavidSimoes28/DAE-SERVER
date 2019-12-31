package ws;

import dtos.ModalityDTO;
import dtos.ScheduleDTO;
import ejbs.AthleteBean;
import ejbs.ModalityBean;
import ejbs.ScheduleBean;
import entities.Athlete;
import entities.Modality;
import entities.PracticedModality;
import entities.Schedule;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    private ScheduleBean scheduleBean;
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
    public List<ModalityDTO> all() {
        try {
            return toDTOs(modalityBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_MODALITIES", e);
        }
    }

    @GET
    @Path("{id}")
    public Response getAdministratorDetails(@PathParam("id") int id) throws Exception {
        Modality modality = modalityBean.find(id);
        try{
            return Response.status(Response.Status.OK).entity(toDTODetails(modality)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_MODALITIES", e);
        }
    }

    @GET
    @Path("{id}/{username}/schedules")
    public Response getPracticedDetails(@PathParam("id") int id,@PathParam("username") String username) throws Exception {
        Modality modality = modalityBean.find(id);
        Athlete athlete = athleteBean.find(username);
        List<Schedule> schedules = new ArrayList<>();
        for (PracticedModality practicedModality : athlete.getPracticedModalities()) {
            if(practicedModality.getModality().getId() == modality.getId()){
                for (Schedule schedule : practicedModality.getSchedules()) {
                    schedules.add(schedule);
                }
            }
        }
        try{
            return Response.status(Response.Status.OK).entity(ScheduleController.toDTOs(schedules)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_COACHES", e);
        }
    }

    @GET
    @Path("{id}/unassigned/schedules")
    public Response getUnassignedSchedules(@PathParam("id") int id) throws Exception {
        List<Schedule> schedules = modalityBean.missingScheduleFromModality(id);
        try{
            return Response.status(Response.Status.OK).entity(ScheduleController.toDTOs(schedules)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_COACHES", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewAdministrator (ModalityDTO modalityDTO) throws Exception {
        modalityBean.create(modalityDTO.getName(),modalityDTO.getSportYear(),modalityDTO.isActive());
        try{
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_MODALITY", e);
        }
    }

    @PUT
    @Path("/{id}/add/schedule/")
    public Response addSchedule (ModalityDTO modalityDTO, ScheduleDTO scheduleDTO) throws Exception {
        Modality modality = modalityBean.addScheduleToModality(modalityDTO.getId(),scheduleDTO.getId());
        try{
            return Response.status(Response.Status.OK).entity(toDTO(modality)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_MODALITY", e);
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateAdministrator (ModalityDTO modalityDTO) throws Exception {
        Modality modality = modalityBean.update(modalityDTO.getId(), modalityDTO.getName());
        try{
            return Response.status(Response.Status.OK).entity(toDTO(modality)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_MODALITY", e);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAdministrator(@PathParam("id") int id) throws Exception {
        modalityBean.delete(id);
        try{
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_MODALITY", e);
        }
    }
}
