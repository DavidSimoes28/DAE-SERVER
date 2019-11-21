package ws;


import dtos.CoachDTO;
import ejbs.CoachBean;
import entities.Coach;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/coaches")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class CoachController {
    @EJB
    private CoachBean coachBean;
    CoachDTO toDTO(Coach coach) {
        return new CoachDTO(
                coach.getUsername(),
                coach.getPassword(),
                coach.getName(),
                coach.getEmail()
        );
    }

    List<CoachDTO> toDTOs(List<Coach> administrators) {
        return administrators.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<CoachDTO> all() {
        try {
            return toDTOs(coachBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_COACHES", e);
        }
    }

    @GET
    @Path("{username}")
    public Response getAdministratorDetails(@PathParam("username") String username) throws Exception {
        Coach coach = coachBean.find(username);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(coach/*,null*/)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_COACHES", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewAdministrator (CoachDTO coachDTO) throws Exception {
        coachBean.create(coachDTO.getUsername(), coachDTO.getPassword(), coachDTO.getName(), coachDTO.getEmail());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_COACH", e);
        }
    }

    @PUT
    @Path("/{username}")
    public Response updateAdministrator (CoachDTO coachDTO) throws Exception {
        Coach coach = coachBean.update(coachDTO.getUsername(),coachDTO.getPassword(),coachDTO.getName(),coachDTO.getEmail());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(coach)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_COACH", e);
        }
    }

    @DELETE
    @Path("/{username}")
    public Response deleteAdministrator(@PathParam("username") String username) throws Exception {
        coachBean.delete(username);
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_COACH", e);
        }
    }
}
