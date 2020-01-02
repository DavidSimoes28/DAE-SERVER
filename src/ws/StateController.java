package ws;

import dtos.StateDTO;
import ejbs.StateBean;
import entities.State;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/states")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class StateController {
    @EJB
    private StateBean stateBean;

    StateDTO toDTO(State state) {
        return new StateDTO(
                state.getId(),
                state.getName()
        );
    }

    List<StateDTO> toDTOs(List<State> states) {
        return states.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    @RolesAllowed({"Administrator"})
    public List<StateDTO> all() {
        try {
            return toDTOs(stateBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_STATES", e);
        }
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"Administrator"})
    public Response getAdministratorDetails(@PathParam("id") int id) throws Exception {
        State state = stateBean.find(id);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(state)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_STATES", e);
        }
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrator"})
    public Response createNewAdministrator (StateDTO stateDTO) throws Exception {
        stateBean.create(stateDTO.getName());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_STATE", e);
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Administrator"})
    public Response deleteAdministrator(@PathParam("id")int id) throws Exception {
        stateBean.delete(id);
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_STATE", e);
        }
    }
}
