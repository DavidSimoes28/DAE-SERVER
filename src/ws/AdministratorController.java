package ws;

import dtos.AdministratorDTO;
import dtos.EmailDTO;
import dtos.FilterUserDTO;
import dtos.UserDTO;
import ejbs.AdministratorBean;
import ejbs.EmailBean;
import entities.Administrator;
import entities.Partner;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/administrators")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AdministratorController {
    @EJB
    private AdministratorBean administratorBean;
    @EJB
    private EmailBean emailBean;
    AdministratorDTO toDTO(Administrator administrator) {
        return new AdministratorDTO(
                administrator.getUsername(),
                administrator.getPassword(),
                administrator.getName(),
                administrator.getEmail()
        );
    }

    List<AdministratorDTO> toDTOs(Collection<Administrator> administrators) {
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
    @Path("/filter")
    public Response getAthleteFilter(FilterUserDTO filterUserDTO) throws Exception {

        Set<Administrator> filter = administratorBean.filter(filterUserDTO.getUsername());

        try{
            return Response.status(Response.Status.OK).entity(toDTOs(filter)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ADMINISTRATOR", e);
        }
    }

    @POST
    @Path("/email/send")
    public Response sendEmail(EmailDTO emailDTO) throws MyEntityNotFoundException, MessagingException {
        for (UserDTO user : emailDTO.getUsers()) {
            if(user == null){
                throw new MyEntityNotFoundException("student not found");
            }
            emailBean.send(user.getEmail(),emailDTO.getSubject(),emailDTO.getMessage());
        }

        return Response.status(Response.Status.OK).entity("E-mail sent").build();
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
