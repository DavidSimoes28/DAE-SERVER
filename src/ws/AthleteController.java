package ws;

import dtos.AthleteDTO;
import ejbs.AthleteBean;
import entities.Athlete;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/athletes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})


public class AthleteController {
    @EJB
    private AthleteBean athleteBean;
    AthleteDTO toDTO(Athlete athlete) {
        return new AthleteDTO(
                athlete.getUsername(),
                athlete.getPassword(),
                athlete.getName(),
                athlete.getEmail()
        );
    }

    List<AthleteDTO> toDTOs(List<Athlete> administrators) {
        return administrators.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<AthleteDTO> all() {
        try {
            return toDTOs(athleteBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_COACHES", e);
        }
    }

    @GET
    @Path("{username}")
    public Response getAdministratorDetails(@PathParam("username") String username) throws Exception {
        Athlete athlete = athleteBean.find(username);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(athlete/*,null*/)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ATHLETES", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewAthlete (AthleteDTO athleteDTO) throws Exception {
        athleteBean.create(athleteDTO.getUsername(), athleteDTO.getPassword(), athleteDTO.getName(), athleteDTO.getEmail());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_ATHLETE", e);
        }
    }

    @PUT
    @Path("/{username}")
    public Response updateAthlete (AthleteDTO athleteDTO) throws Exception {
        Athlete athlete = athleteBean.update(athleteDTO.getUsername(),athleteDTO.getPassword(),athleteDTO.getName(),athleteDTO.getEmail());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(athlete)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_ATHLETE", e);
        }
    }

    @PUT
    @Path("/{username}/enroll/{id}")
    public Response enrollAthleteModality (@PathParam("username") String username, @PathParam("id") int modalityId) throws Exception {
        Athlete athlete = athleteBean.enroll(modalityId,username);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(athlete)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_ATHLETE", e);
        }
    }

    @PUT
    @Path("/{username}/unroll/{id}")
    public Response unrollAthleteModality (@PathParam("username") String username, @PathParam("id") int modalityId) throws Exception {
        Athlete athlete = athleteBean.unroll(modalityId,username);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(athlete)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_ATHLETE", e);
        }
    }

    @DELETE
    @Path("/{username}")
    public Response deleteAthlete(@PathParam("username") String username) throws Exception {
        athleteBean.delete(username);
        try{
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_COACH", e);
        }
    }
}
