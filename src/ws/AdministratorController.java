package ws;

import dtos.AdministratorDTO;
import ejbs.AdministratorBean;
import entities.Administrator;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/administrators")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AdministratorController {
    @EJB
    private AdministratorBean administratorBean;
    AdministratorDTO toDTO(Administrator administrator) {
        return new AdministratorDTO(
                administrator.getUsername(),
                administrator.getPassword(),
                administrator.getName(),
                administrator.getEmail()
        );
    }

    List<AdministratorDTO> toDTOs(List<Administrator> administrators) {
        return administrators.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<AdministratorDTO> all() {
        try {
            return toDTOs(administratorBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ADMINISTRATORS", e);
        }
    }

    @GET
    @Path("{username}")
    public Response getAdministratorDetails(@PathParam("username") String username) throws Exception {
        Administrator administrator = administratorBean.find(username);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(administrator/*,null*/)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ADMINISTRATORS", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewAdministrator (AdministratorDTO administratorDTO) throws Exception {
        administratorBean.create(administratorDTO.getUsername(), administratorDTO.getPassword(), administratorDTO.getName(), administratorDTO.getEmail());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_ADMINISTRATOR", e);
        }
    }

    @PUT
    @Path("/{username}")
    public Response updateAdministrator (AdministratorDTO administratorDTO) throws Exception {
        Administrator administrator = administratorBean.update(administratorDTO.getUsername(),administratorDTO.getPassword(),administratorDTO.getName(),administratorDTO.getEmail());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(administrator)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_ADMINISTRATOR", e);
        }
    }

    @DELETE
    @Path("/{username}")
    public Response deleteAdministrator(@PathParam("username") String username) throws Exception {
        administratorBean.delete(username);
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_ADMINISTRATOR", e);
        }
    }
}
