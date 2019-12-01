package ws;

import dtos.EchelonDTO;
import ejbs.EchelonBean;
import entities.Echelon;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/echelons")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class EchelonController {
    @EJB
    EchelonBean echelonBean;
    EchelonDTO toDTO(Echelon echelon) {
        return new EchelonDTO(
                echelon.getName(),
                echelon.getInitialAge(),
                echelon.getFinalAge()
        );
    }

    List<EchelonDTO> toDTOs(List<Echelon> echelons) {
        return echelons.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<EchelonDTO> all() {
        try {
            return toDTOs(echelonBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ECHELONS", e);
        }
    }

    @GET
    @Path("{echelon}")
    public Response getAdministratorDetails(@PathParam("echelon") String echelon) throws Exception {
        Echelon echelon1 = echelonBean.find(echelon);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(echelon1)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ECHELONS", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewAdministrator (EchelonDTO echelonDTO) throws Exception {
        echelonBean.create(echelonDTO.getName(),echelonDTO.getInitialAge(),echelonDTO.getFinalAge());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_ECHELON", e);
        }
    }

    @PUT
    @Path("/{echelon}")
    public Response updateAdministrator (EchelonDTO echelonDTO) throws Exception {
        Echelon echelon = echelonBean.update(echelonDTO.getName(),echelonDTO.getInitialAge(),echelonDTO.getFinalAge());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(echelon)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_ECHELON", e);
        }
    }

    @DELETE
    @Path("/{echelon}")
    public Response deleteAdministrator(@PathParam("echelon") String echelon) throws Exception {
        echelonBean.delete(echelon);
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_DELETING_ECHELON", e);
        }
    }
}