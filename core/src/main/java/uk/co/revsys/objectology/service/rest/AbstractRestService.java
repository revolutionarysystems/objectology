package uk.co.revsys.objectology.service.rest;

import java.util.HashMap;
import javax.ws.rs.core.Response;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.mapping.SerialiserException;
import uk.co.revsys.objectology.view.ViewNotFoundException;

public class AbstractRestService {

	private final ObjectMapper jsonObjectMapper;
	private final HashMap<String, Class> viewMap;

	public AbstractRestService(ObjectMapper jsonObjectMapper, HashMap<String, Class> viewMap) {
		this.jsonObjectMapper = jsonObjectMapper;
		this.viewMap = viewMap;
	}

	public ObjectMapper getJsonObjectMapper() {
		return jsonObjectMapper;
	}

	public HashMap<String, Class> getViewMap() {
		return viewMap;
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
		try {
			return Response.ok(jsonObjectMapper.serialise(entity)).build();
		} catch (SerialiserException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
