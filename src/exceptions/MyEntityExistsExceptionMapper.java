package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyEntityExistsExceptionMapper implements ExceptionMapper<MyEntityExistsException> {
    @Override
    public Response toResponse(MyEntityExistsException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
