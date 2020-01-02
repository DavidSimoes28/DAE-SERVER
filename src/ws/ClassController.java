package ws;


import dtos.ClassesDTO;
import ejbs.ClassBean;
import entities.Athlete;
import entities.Classes;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/classes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class ClassController {

    @EJB
    private ClassBean classBean;
    @Context
    private SecurityContext securityContext;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static ClassesDTO toDTO(Classes classe) {
        ClassesDTO classesDTO = new ClassesDTO(
                classe.getId(),
                classe.getCoach().getUsername(),
                classe.getSchedule().getId(),
                classe.getModality().getId(),
                format.format(classe.getDate())
        );
        return classesDTO;

    }

    public static ClassesDTO toDTODetails(Classes classe) {
        ClassesDTO classesDTO = new ClassesDTO(
                classe.getId(),
                classe.getCoach().getUsername(),
                classe.getSchedule().getId(),
                classe.getModality().getId(),
                format.format(classe.getDate())
        );


        Set<Athlete> athletes = classe.getAthletesPresent();
        if(athletes != null){
            classesDTO.setAthletesPresent(AthleteController.toDTOs(athletes));
        }
        return classesDTO;

    }

    public static List<ClassesDTO> toDTOs(Collection<Classes> classes) {
        return classes.stream().map(ClassController::toDTO).collect(Collectors.toList());
    }


    @GET
    @Path("/")
    @RolesAllowed({"Administrator"})
    public List<ClassesDTO> all() {
        try {
            if(securityContext.isUserInRole("Administrator")) {
                return toDTOs(classBean.all());
            }
            return null;
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_CLASSES", e);
        }
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"Administrator","Coach"})
    public Response getClassDetails(@PathParam("id") int id) throws Exception {
        if(securityContext.isUserInRole("Administrator") || securityContext.isUserInRole("Coach")) {
            Classes classes = classBean.find(id);
            try {
                return Response.status(Response.Status.OK).entity(toDTODetails(classes)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_GET_CLASS", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("{id}/notPresent")
    @RolesAllowed({"Administrator","Coach"})
    public Response getNotPresentAthletes(@PathParam("id") int id) throws Exception {
        if(securityContext.isUserInRole("Administrator") || securityContext.isUserInRole("Coach")) {
            List<Athlete> notPresentAthletes = classBean.findNotPresentAthletes(id);
            try {
                return Response.status(Response.Status.OK).entity(AthleteController.toDTOs(notPresentAthletes)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_GET_CLASS", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrator"})
    public Response createNewClass (ClassesDTO classesDTO) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            classBean.create(classesDTO.getCoachUsername(), classesDTO.getScheduleID(), classesDTO.getModalityID(), classesDTO.getDate());
            try {
                return Response.status(Response.Status.CREATED).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_CREATING_CLASS", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PUT
    @Path("/{id}/present/{username}")
    @RolesAllowed({"Administrator","Coach"})
    public Response addPresentAthlete(@PathParam("id") int id,@PathParam("username") String username) throws Exception {
        if(securityContext.isUserInRole("Administrator") || securityContext.isUserInRole("Coach")) {
            classBean.addPresentAthlete(id, username);
            try {
                return Response.status(Response.Status.CREATED).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_CREATING_CLASS", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PUT
    @Path("/{id}/notPresent/{username}")
    @RolesAllowed({"Administrator","Coach"})
    public Response removePresentAthlete(@PathParam("id") int id,@PathParam("username") String username) throws Exception {
        if(securityContext.isUserInRole("Administrator") || securityContext.isUserInRole("Coach")) {
            classBean.removePresentAthlete(id, username);
            try {
                return Response.status(Response.Status.CREATED).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_CREATING_CLASS", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Administrator"})
    public Response updateAthlete (ClassesDTO classesDTO) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            Classes classes = classBean.update(classesDTO.getId(), classesDTO.getCoachUsername(), classesDTO.getScheduleID());
            try {
                return Response.status(Response.Status.CREATED).entity(toDTO(classes)).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_UPDATING_ATHLETE", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Administrator"})
    public Response deleteAthlete(@PathParam("id") int id) throws Exception {
        if(securityContext.isUserInRole("Administrator")) {
            classBean.delete(id);
            try {
                return Response.status(Response.Status.OK).build();
            } catch (Exception e) {
                throw new EJBException("ERROR_DELETING_COACH", e);
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
  }
}





