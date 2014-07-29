package uk.co.revsys.objectology.service.rest;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.mapping.SerialiserException;
import uk.co.revsys.objectology.security.AuthorisationHandler;
import uk.co.revsys.objectology.view.ViewNotFoundException;

public class AbstractRestService {
    
    private static final Logger LOG = LoggerFactory.getLogger(AbstractRestService.class);

	private final ObjectMapper jsonObjectMapper;
	private final HashMap<String, Class> viewMap;
    private final AuthorisationHandler authorisationHandler;

	public AbstractRestService(ObjectMapper jsonObjectMapper, HashMap<String, Class> viewMap, AuthorisationHandler authorisationHandler) {
		this.jsonObjectMapper = jsonObjectMapper;
		this.viewMap = viewMap;
        this.authorisationHandler = authorisationHandler;
	}

	public ObjectMapper getJsonObjectMapper() {
		return jsonObjectMapper;
	}

	public HashMap<String, Class> getViewMap() {
		return viewMap;
	}

    public AuthorisationHandler getAuthorisationHandler() {
        return authorisationHandler;
    }
    
    protected boolean isAdministrator(){
        return authorisationHandler.isAdministrator();
    }

	protected Class getView(String viewName) throws ViewNotFoundException {
		if (viewName == null) {
			viewName = "default";
		}
		Class view = viewMap.get(viewName);
		if (view == null) {
			throw new ViewNotFoundException(viewName);
		}
		return view;
	}

    protected Response buildResponse(Object entity) {
        return buildResponse(entity, 1);
    }
    
	protected Response buildResponse(Object entity, int depth) {
		try {
            Map<String, Object> serialisationParameters = new HashMap<String, Object>();
            serialisationParameters.put("depth", depth);
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
