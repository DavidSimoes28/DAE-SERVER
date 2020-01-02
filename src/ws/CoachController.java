package ws;


import dtos.CoachDTO;
import dtos.EmailDTO;
import dtos.FilterCoachDTO;
import dtos.UserDTO;
import ejbs.CoachBean;
import ejbs.EmailBean;
import entities.Athlete;
import entities.Coach;
import exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
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
    @EJB
    private EmailBean emailBean;
    @Context
    private SecurityContext securityContext;

    public static CoachDTO toDTO(Coach coach) {
        CoachDTO coachDTO = new CoachDTO(
                coach.getUsername(),
                coach.getPassword(),
                coach.getName(),
                coach.getEmail()
        );
        return coachDTO;
    }

    public static CoachDTO toDTODetails(Coach coach) {
        CoachDTO coachDTO = new CoachDTO(
                coach.getUsername(),
                coach.getPassword(),
                coach.getName(),
                coach.getEmail()
        );
        coachDTO.setEchelons(EchelonController.toDTOs(coach.getEchelons()));
        coachDTO.setModalities(ModalityController.toDTOs(coach.getModalities()));
        coachDTO.setClasses(ClassController.toDTOs(coach.getClasses()));
        return coachDTO;
    }

    public static List<CoachDTO> toDTOs(Collection<Coach> administrators) {
        return administrators.stream().map(CoachController::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    @RolesAllowed({"Administrator"})
    public List<CoachDTO> all() {
        try {
            return toDTOs(coachBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_COACHES", e);
        }
    }

    @GET
    @Path("{username}")
    @RolesAllowed({"Administrator","Coach"})
    public Response getAdministratorDetails(@PathParam("username") String username) throws Exception {
        Principal principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Administrator") || principal.getName().equals(username)){
            Coach coach = coachBean.find(username);
            try{
                return Response.status(Response.Status.OK).entity(toDTODetails(coach)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_GET_COACHES", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("{username}/athletes")
    @RolesAllowed({"Administrator","Coach"})
    public Response getCoachAthletes(@PathParam("username") String username) throws Exception {
        Principal principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Administrator") || principal.getName().equals(username)){
            Set<Athlete> coachAthletes = coachBean.findCoachAthletes(username);
            try{
                return Response.status(Response.Status.OK).entity(AthleteController.toDTOs(coachAthletes)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_GET_COACHES", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Path("/filter")
    @RolesAllowed({"Administrator"})
    public Response getAthleteFilter(FilterCoachDTO filterCoachDTO) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            Set<Coach> filter = coachBean.filter(filterCoachDTO.getUsername(), filterCoachDTO.getModalityId());
            try {
                return Response.status(Response.Status.OK).entity(toDTOs(filter)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_GET_ATHLETES", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrator"})
    public Response createNewCoach (CoachDTO coachDTO) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            coachBean.create(coachDTO.getUsername(), coachDTO.getPassword(), coachDTO.getName(), coachDTO.getEmail());
            try {
                return Response.status(Response.Status.CREATED).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_CREATING_COACH", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Path("/email/send")
    @RolesAllowed({"Administrator","Coach"})
    public Response sendEmail(EmailDTO emailDTO) throws MyEntityNotFoundException, MessagingException {
        if(securityContext.isUserInRole("Administrator") || securityContext.isUserInRole("Coach")) {
            for (UserDTO user : emailDTO.getUsers()) {
                if (user == null) {
                    throw new MyEntityNotFoundException("student not found");
                }
                emailBean.send(user.getEmail(), emailDTO.getSubject(), emailDTO.getMessage());
            }
            return Response.status(Response.Status.OK).entity("E-mail sent").build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PUT
    @Path("/{username}")
    @RolesAllowed({"Administrator"})
    public Response updateCoach (CoachDTO coachDTO) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            Coach coach = coachBean.update(coachDTO.getUsername(), coachDTO.getName(), coachDTO.getEmail());
            try {
                return Response.status(Response.Status.CREATED).entity(toDTO(coach)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_UPDATING_COACH", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @DELETE
    @Path("/{username}")
    @RolesAllowed({"Administrator"})
    public Response deleteCoach(@PathParam("username") String username) throws Exception {
        if(securityContext.isUserInRole("Administrator")){
            coachBean.delete(username);
            try {
                return Response.status(Response.Status.OK).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_DELETING_COACH", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
