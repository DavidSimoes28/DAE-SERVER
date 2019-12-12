package ws;


import dtos.CoachDTO;
import ejbs.CoachBean;
import entities.Coach;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/coaches")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class CoachController {
    @EJB
    private CoachBean coachBean;
    public static CoachDTO toDTO(Coach coach) {
        CoachDTO coachDTO = new CoachDTO(
                coach.getUsername(),
                coach.getPassword(),
                coach.getName(),
                coach.getEmail()
        );
        coachDTO.setEchelons(EchelonController.toDTOs(coach.getEchelons()));
        //coachDTO.setModalities(ModalityController.toDTOs(coach.getModalities()));
        return coachDTO;
    }

    public static List<CoachDTO> toDTOs(Collection<Coach> administrators) {
        return administrators.stream().map(CoachController::toDTO).collect(Collectors.toList());
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
    public Response createNewCoach (CoachDTO coachDTO) throws Exception {
        coachBean.create(coachDTO.getUsername(), coachDTO.getPassword(), coachDTO.getName(), coachDTO.getEmail());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_COACH", e);
        }
    }

    @PUT
    @Path("/{username}")
    public Response updateCoach (CoachDTO coachDTO) throws Exception {
        Coach coach = coachBean.update(coachDTO.getUsername(),coachDTO.getPassword(),coachDTO.getName(),coachDTO.getEmail());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(coach)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_COACH", e);
        }
    }

    /*@PUT
    @Path("/{username}/enroll/{id}")
    public Response enrollCoachModality (@PathParam("username") String username, @PathParam("id") int modalityId) throws Exception {
        Coach coach = coachBean.enroll(modalityId,username);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(coach)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_COACH", e);
        }
    }

    @PUT
    @Path("/{username}/unroll/{id}")
    public Response unrollCoachModality (@PathParam("username") String username, @PathParam("id") int modalityId) throws Exception {
        Coach coach = coachBean.unroll(modalityId,username);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(coach)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_COACH", e);
        }
    }*/

    @DELETE
    @Path("/{username}")
    public Response deleteCoach(@PathParam("username") String username) throws Exception {
        coachBean.delete(username);
        try{
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_COACH", e);
        }
    }
}
