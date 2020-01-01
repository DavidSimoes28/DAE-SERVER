package ws;

import dtos.AthleteDTO;
import dtos.FilterAthleteDTO;
import ejbs.AthleteBean;
import entities.*;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.naming.ldap.PagedResultsControl;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/athletes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})


public class AthleteController {
    @EJB
    private AthleteBean athleteBean;
    public static AthleteDTO toDTO(Athlete athlete) {
         AthleteDTO athleteDTO = new AthleteDTO(
                athlete.getUsername(),
                athlete.getPassword(),
                athlete.getName(),
                athlete.getEmail()
        );
         return athleteDTO;
    }

    public static AthleteDTO toDTODetails(Athlete athlete) {
        AthleteDTO athleteDTO = new AthleteDTO(
                athlete.getUsername(),
                athlete.getPassword(),
                athlete.getName(),
                athlete.getEmail()
        );

        Set<Modality> modalities = athlete.getModalities();
        if(modalities != null){
            athleteDTO.setModalities(ModalityController.toDTOs(modalities));
        }
        Set<Echelon> echelons = athlete.getEchelons();
        if(echelons != null){
            athleteDTO.setEchelons(EchelonController.toDTOs(echelons));
        }
        Set<Graduations> graduations = athlete.getGraduations();
        if(graduations != null){
            athleteDTO.setGraduations(GraduationsController.toDTOs(graduations));
        }
        athleteDTO.setClasses(ClassController.toDTOs(athlete.getClasses()));
        return athleteDTO;
    }

    public static List<AthleteDTO> toDTOs(Collection<Athlete> athletes) {
        return athletes.stream().map(AthleteController::toDTO).collect(Collectors.toList());
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
            return Response.status(Response.Status.OK).entity(toDTODetails(athlete)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ATHLETES", e);
        }
    }

    @POST
    @Path("/filter")
    public Response getAthleteFilter(FilterAthleteDTO filterAthleteDTO) throws Exception {
        //Athlete athlete = athleteBean.find(username);
        Set<Athlete> filter = athleteBean.filter(filterAthleteDTO.getUsername(), filterAthleteDTO.getModalityId(), filterAthleteDTO.getEchelonId(), filterAthleteDTO.getGraduationId());

        try{
            return Response.status(Response.Status.OK).entity(toDTOs(filter)).build();
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
