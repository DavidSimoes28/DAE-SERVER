package ws;


import dtos.ClassesDTO;
import ejbs.ClassBean;
import entities.Athlete;
import entities.Classes;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/classes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class ClassController {

    @EJB
    private ClassBean classBean;

    public static ClassesDTO toDTO(Classes classe) {
        ClassesDTO classesDTO = new ClassesDTO(
                classe.getId(),
                classe.getCoach().getUsername(),
                classe.getSchedule().getId(),
                classe.getModality().getId(),
                classe.getDate()
        );
        return classesDTO;

    }

    public static ClassesDTO toDTODetails(Classes classe) {
        ClassesDTO classesDTO = new ClassesDTO(
                classe.getId(),
                classe.getCoach().getUsername(),
                classe.getSchedule().getId(),
                classe.getModality().getId(),
                classe.getDate()
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
    public List<ClassesDTO> all() {
        try {
            return toDTOs(classBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_CLASSES", e);
        }
    }

    @GET
    @Path("{id}")
    public Response getClassDetails(@PathParam("id") int id) throws Exception {
        Classes classes = classBean.find(id);
        try{
            return Response.status(Response.Status.OK).entity(toDTODetails(classes)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_CLASS", e);
        }
    }

    @GET
    @Path("{id}/notPresent")
    public Response getNotPresentAthletes(@PathParam("id") int id) throws Exception {
        List<Athlete> notPresentAthletes = classBean.findNotPresentAthletes(id);
        try{
            return Response.status(Response.Status.OK).entity(AthleteController.toDTOs(notPresentAthletes)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_CLASS", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewClass (ClassesDTO classesDTO) throws Exception {
        classBean.create(classesDTO.getCoachUsername(),classesDTO.getScheduleID(),classesDTO.getModalityID(),classesDTO.getDate());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_CLASS", e);
        }
    }

    @PUT
    @Path("/{id}/present/{username}")
    public Response addPresentAthlete(@PathParam("id") int id,@PathParam("username") String username) throws Exception {
        classBean.addPresentAthlete(id,username);
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_CLASS", e);
        }
    }

    @PUT
    @Path("/{id}/notPresent/{username}")
    public Response removePresentAthlete(@PathParam("id") int id,@PathParam("username") String username) throws Exception {
        classBean.removePresentAthlete(id,username);
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_CLASS", e);
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateAthlete (ClassesDTO classesDTO) throws Exception {
        Classes classes = classBean.update(classesDTO.getId(),classesDTO.getCoachUsername(),classesDTO.getScheduleID(),classesDTO.getDate());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(classes)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_ATHLETE", e);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAthlete(@PathParam("id") int id) throws Exception {
        classBean.delete(id);
        try{
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_COACH", e);
        }

  }
}





