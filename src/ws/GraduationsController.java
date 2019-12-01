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
                graduations.getCode(),
                graduations.getName(),
                graduations.getMinimumAge()
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
            throw new EJBException("ERROR_GET_GRADUATIONS", e);
        }
    }

    @GET
    @Path("{code}")
    public Response getAdministratorDetails(@PathParam("code") String code) throws Exception {
        Graduations graduations = graduationsBean.find(code);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(graduations)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_GRADUATIONS", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewAdministrator (GraduationsDTO graduationsDTO) throws Exception {
        graduationsBean.create(graduationsDTO.getCode(),graduationsDTO.getName(),graduationsDTO.getMinimumAge());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_GRADUATION", e);
        }
    }

    @PUT
    @Path("/{code}")
    public Response updateAdministrator (GraduationsDTO graduationsDTO) throws Exception {
        Graduations modality = graduationsBean.update(graduationsDTO.getCode(), graduationsDTO.getName(),graduationsDTO.getMinimumAge());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(modality)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_GRADUATION", e);
        }
    }

    @DELETE
    @Path("/{code}")
    public Response deleteAdministrator(@PathParam("code") String code) throws Exception {
        graduationsBean.delete(code);
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_GRADUATION", e);
        }
    }
}
