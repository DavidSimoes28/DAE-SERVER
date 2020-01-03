package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class MyEntityCantBeDeletedExceptionMapper implements ExceptionMapper<MyEntityCantBeDeletedException> {
    @Override
    public Response toResponse(MyEntityCantBeDeletedException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
