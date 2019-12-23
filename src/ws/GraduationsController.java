package ws;

import dtos.GraduationsDTO;
import ejbs.GraduationsBean;
import entities.Graduations;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/graduations")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class GraduationsController {
    @EJB
    private GraduationsBean graduationsBean;

    public static GraduationsDTO toDTO(Graduations graduations) {
        GraduationsDTO graduationsDTO = new GraduationsDTO(
                graduations.getId(),
                graduations.getCode(),
                graduations.getName(),
                graduations.getMinimumAge(),
                ModalityController.toDTO(graduations.getModality())
        );
        return graduationsDTO;
    }

    public static List<GraduationsDTO> toDTOs(Collection<Graduations> graduations) {
        return graduations.stream().map(GraduationsController::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<GraduationsDTO> all() {
        try {
            return toDTOs(graduationsBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_GRADUATIONS", e);
        }
    }

    @GET
    @Path("{id}")
    public Response getAdministratorDetails(@PathParam("id") int id) throws Exception {
        Graduations graduations = graduationsBean.find(id);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(graduations)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_GRADUATIONS", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewAdministrator (GraduationsDTO graduationsDTO) throws Exception {
        graduationsBean.create(graduationsDTO.getCode(),graduationsDTO.getName(),graduationsDTO.getMinimumAge(),graduationsDTO.getModalityDTO().getId());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_GRADUATION", e);
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateAdministrator (GraduationsDTO graduationsDTO) throws Exception {
        Graduations graduations = graduationsBean.update(graduationsDTO.getId(),graduationsDTO.getCode(), graduationsDTO.getName(),graduationsDTO.getMinimumAge());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(graduations)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_GRADUATION", e);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAdministrator(@PathParam("id") int id) throws Exception {
        graduationsBean.delete(id);
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_GRADUATION", e);
        }
    }
}
