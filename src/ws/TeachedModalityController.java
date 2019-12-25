package ws;

import dtos.TeachedModalityDTO;
import ejbs.TeachedModalityBean;
import entities.TeachedModality;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("/teachedModalities")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class TeachedModalityController {
    @EJB
    private TeachedModalityBean teachedModalityBean;
    public static TeachedModalityDTO toDTO(TeachedModality teachedModality) {
        TeachedModalityDTO teachedModalityDTO = new TeachedModalityDTO(
                teachedModality.getId(),
                teachedModality.getModality().getId(),
                teachedModality.getCoach().getUsername()
        );
        teachedModalityDTO.setSchedules(ScheduleController.toDTOs(teachedModality.getSchedules()));
        return teachedModalityDTO;
    }

    public static List<TeachedModalityDTO> toDTOs(Collection<TeachedModality> teachedModalities) {
        return teachedModalities.stream().map(TeachedModalityController::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<TeachedModalityDTO> all() {
        try {
            return toDTOs(teachedModalityBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_COACHES", e);
        }
    }
}
