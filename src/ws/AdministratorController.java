package ws;

import dtos.*;
import ejbs.AdministratorBean;
import ejbs.EmailBean;
import entities.Administrator;
import entities.Partner;
import entities.User;
import exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
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

    UserDTO userToDTO(User user) {
        return new AdministratorDTO(
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getEmail()
        );
    }
    List<UserDTO> userToDTOs(Collection<User> users) {
        return users.stream().map(this::userToDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    @RolesAllowed({"Administrator"})
    public List<AdministratorDTO> all() {
        try {
            return toDTOs(administratorBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ADMINISTRATORS", e);
        }
    }

    @GET
    @Path("/email/send/users")
    @RolesAllowed({"Administrator"})
    public Response getAllSendEmail() throws Exception {
        Set<User> users = emailBean.allUsers();
        try{
            return Response.status(Response.Status.OK).entity(userToDTOs(users)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ADMINISTRATOR", e);
        }
    }

    @GET
    @Path("{username}")
    @RolesAllowed({"Administrator"})
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
    @RolesAllowed({"Administrator"})
    public Response getAthleteFilter(FilterUserDTO filterUserDTO) throws Exception {

        Set<Administrator> filter = administratorBean.filter(filterUserDTO.getUsername());

        try{
            return Response.status(Response.Status.OK).entity(toDTOs(filter)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ADMINISTRATOR", e);
        }
    }

    @POST
    @Path("/email/send/filter")
    @RolesAllowed({"Administrator"})
    public Response getSendEmailFilter(FilterSendEmailDTO filterSendEmailDTO) throws Exception {

        Set<User> filter = emailBean.filter(filterSendEmailDTO.getModalityId(),filterSendEmailDTO.getEchelonId());

        try{
            return Response.status(Response.Status.OK).entity(userToDTOs(filter)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ADMINISTRATOR", e);
        }
    }

    @POST
    @Path("/email/send")
    @RolesAllowed({"Administrator"})
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
    @RolesAllowed({"Administrator"})
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
    @RolesAllowed({"Administrator"})
    public Response updateAdministrator (AdministratorDTO administratorDTO) throws Exception {
        Administrator administrator = administratorBean.update(administratorDTO.getUsername(),administratorDTO.getName(),administratorDTO.getEmail());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(administrator)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_ADMINISTRATOR", e);
        }
    }

    @DELETE
    @Path("/{username}")
    @RolesAllowed({"Administrator"})
    public Response deleteAdministrator(@PathParam("username") String username) throws Exception {
        administratorBean.delete(username);
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_ADMINISTRATOR", e);
        }
    }
}
