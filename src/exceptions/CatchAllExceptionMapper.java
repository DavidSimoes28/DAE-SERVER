package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

//@Provider
public class CatchAllExceptionMapper /*implements ExceptionMapper<Exception> */{

    /*private static final Logger logger = Logger.getLogger("exceptions.CatchAllExceptionMapper");

    @Override
    public Response toResponse(Exception e) {
        logger.warning("ERROR: " + e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }*/
}
