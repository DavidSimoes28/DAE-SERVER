package ws;

import dtos.ModalityDTO;
import ejbs.ModalityBean;
import entities.Modality;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;


@Path("/modalities")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ModalityController {
    @EJB
    private ModalityBean modalityBean;
    ModalityDTO toDTO(Modality modality) {
        return new ModalityDTO(
                modality.getId(),
                modality.getName()
        );
    }

    List<ModalityDTO> toDTOs(List<Modality> modalities) {
        return modalities.stream().map(this::toDTO).collect(Collectors.toList());
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
            return Response.status(Response.Status.OK).entity(toDTO(modality)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_MODALITIES", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewAdministrator (ModalityDTO modalityDTO) throws Exception {
        modalityBean.create( modalityDTO.getName());
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
