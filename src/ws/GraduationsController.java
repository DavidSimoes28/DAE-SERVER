package ws;

import dtos.GraduationsDTO;
import ejbs.GraduationsBean;
import entities.Graduations;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/graduations")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class GraduationsController {
    @EJB
    private GraduationsBean graduationsBean;
    GraduationsDTO toDTO(Graduations graduations) {
        return new GraduationsDTO(
                graduations.getId(),
                graduations.getName()
        );
    }

    List<GraduationsDTO> toDTOs(List<Graduations> graduations) {
        return graduations.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<GraduationsDTO> all() {
        try {
            return toDTOs(graduationsBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_MODALITIES", e);
        }
    }

    @GET
    @Path("{id}")
    public Response getAdministratorDetails(@PathParam("id") int id) throws Exception {
        Graduations graduations = graduationsBean.find(id);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(graduations)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_MODALITIES", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewAdministrator (GraduationsDTO graduationsDTO) throws Exception {
        graduationsBean.create(graduationsDTO.getName());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_MODALITY", e);
        }
    }

    @PUT
    @Path("/{username}")
    public Response updateAdministrator (GraduationsDTO graduationsDTO) throws Exception {
        Graduations modality = graduationsBean.update(graduationsDTO.getId(), graduationsDTO.getName());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(modality)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_MODALITY", e);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAdministrator(@PathParam("id") int id) throws Exception {
        graduationsBean.delete(id);
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_MODALITY", e);
        }
    }
}
