package fr.pham.vinh.portail.api.exception;

import javax.annotation.Priority;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Custom exception mapper to handle errors messages to the response.
 * Created by Vinh PHAM on 04/04/2017.
 */
@Provider
@Priority(Priorities.USER)
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        // Function that extract all messages from a set of constraint violation
        Function<ConstraintViolationException, List<String>> f = constraintViolations -> {
            final List<String> messages = new ArrayList<>();
            constraintViolations
                    .getConstraintViolations()
                    .forEach(violation -> messages.add(violation.getMessage()));
            return messages;
        };

        // Build the response
        ExceptionResponse response = new ExceptionResponse();
        response.setHttpStatus(Response.Status.BAD_REQUEST.getStatusCode());
        response.setReason(Response.Status.BAD_REQUEST.getReasonPhrase());
        response.setErrorMessages(f.apply(exception));

        return Response
                .status(response.getHttpStatus())
                .entity(response)
                .build();
    }

}