package uk.co.revsys.objectology.service.rest;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.mapping.SerialiserException;
import uk.co.revsys.objectology.security.AuthorisationHandler;

public class AbstractRestService {
    
    private static final Logger LOG = LoggerFactory.getLogger(AbstractRestService.class);

	private final ObjectMapper jsonObjectMapper;
    private final AuthorisationHandler authorisationHandler;

	public AbstractRestService(ObjectMapper jsonObjectMapper, AuthorisationHandler authorisationHandler) {
		this.jsonObjectMapper = jsonObjectMapper;
        this.authorisationHandler = authorisationHandler;
	}

	public ObjectMapper getJsonObjectMapper() {
		return jsonObjectMapper;
	}

    public AuthorisationHandler getAuthorisationHandler() {
        return authorisationHandler;
    }
    
    protected boolean isAdministrator(){
        return authorisationHandler.isAdministrator();
    }
    
	protected Response buildResponse(Object entity) {
		try {
            Map<String, Object> serialisationParameters = new HashMap<String, Object>();
			return Response.ok(jsonObjectMapper.serialise(entity, serialisationParameters)).build();
		} catch (SerialiserException ex) {
            LOG.error("Error building response", ex);
			return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
		}
	}
    
    protected Response buildErrorResponse(Response.Status status, Throwable ex){
        return buildErrorResponse(status, ex.getMessage());
    }
    
    protected Response buildErrorResponse(Response.Status status, String message){
        return Response.status(status).entity(message).build();
    }

}
