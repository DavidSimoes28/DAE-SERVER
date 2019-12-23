package ws;

import dtos.PracticedModalityDTO;
import ejbs.PracticedModalityBean;
import entities.Modality;
import entities.PracticedModality;
import entities.Schedule;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/PracticedModality")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PracticedModalityController {
    @EJB
    private PracticedModalityBean practicedModalityBean;

    PracticedModalityDTO toDTO(PracticedModality practicedModality) {
        PracticedModalityDTO practicedModalityDTO = new PracticedModalityDTO(
                practicedModality.getId(),
                ModalityController.toDTO(practicedModality.getModality()),
                EchelonController.toDTO(practicedModality.getEchelon()),
                GraduationsController.toDTO(practicedModality.getGraduations()),
                AthleteController.toDTO(practicedModality.getAthlete())
        );
        practicedModalityDTO.setSchedules(ScheduleController.toDTOs(practicedModality.getSchedules()));
        return practicedModalityDTO;
    }

    List<PracticedModalityDTO> toDTOs(List<PracticedModality> practicedModalities) {
        return practicedModalities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<PracticedModalityDTO> all() {
        try {
            return toDTOs(practicedModalityBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_COACHES", e);
        }
    }

    @GET
    @Path("{id}")
    public Response getPracticedDetails(@PathParam("id") int id) throws Exception {
        PracticedModality practicedModality = practicedModalityBean.find(id);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(practicedModality)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_COACHES", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewCoach (PracticedModalityDTO practicedModalityDTO) throws Exception {
        practicedModalityBean.create(practicedModalityDTO.getModality().getId(),practicedModalityDTO.getEchelon().getId(),practicedModalityDTO.getGraduations().getId(),practicedModalityDTO.getAthlete().getUsername());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_COACH", e);
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateCoach (PracticedModalityDTO practicedModalityDTO) throws Exception {
        PracticedModality practicedModality = practicedModalityBean.update(practicedModalityDTO.getId(),practicedModalityDTO.getEchelon().getId(),practicedModalityDTO.getGraduations().getId());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(practicedModality)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_COACH", e);
        }
    }

    /*@DELETE
    @Path("/{username}")
    public Response deleteCoach(@PathParam("username") String username) throws Exception {
        practicedModalityBean.delete(username);
        try{
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_COACH", e);
        }
    }*/
}
