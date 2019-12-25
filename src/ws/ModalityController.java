package ws;

import dtos.ModalityDTO;
import ejbs.ModalityBean;
import entities.Modality;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Path("/modalities")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ModalityController {
    @EJB
    private ModalityBean modalityBean;
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
        modalityDTO.setPracticedModalities(PracticedModalityController.toDTOs(modality.getPracticedModalities()));
        modalityDTO.setTeachedModalities(TeachedModalityController.toDTOs(modality.getTeachedModalities()));
        return modalityDTO;
    }

    public static List<ModalityDTO> toDTOs(Collection<Modality> modalities) {
        return modalities.stream().map(ModalityController::toDTO).collect(Collectors.toList());
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

    @POST
    @Path("/")
    public Response createNewAdministrator (ModalityDTO modalityDTO) throws Exception {
        modalityBean.create(modalityDTO.getName(),modalityDTO.getSportYear(),modalityDTO.isActive());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_MODALITY", e);
        }
    }

    @PUT
    @Path("/{username}")
    public Response updateAdministrator (ModalityDTO modalityDTO) throws Exception {
        Modality modality = modalityBean.update(modalityDTO.getId(), modalityDTO.getName());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(modality)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_MODALITY", e);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAdministrator(@PathParam("id") int id) throws Exception {
        modalityBean.delete(id);
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_MODALITY", e);
        }
    }
}
