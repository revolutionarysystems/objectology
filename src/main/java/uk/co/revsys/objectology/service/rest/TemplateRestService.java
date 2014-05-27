package uk.co.revsys.objectology.service.rest;

import java.util.HashMap;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;
import uk.co.revsys.objectology.service.OlogyTemplateService;

@Path("/templates")
public class TemplateRestService {
	
	private static final Log LOG = LogFactory.getLog(TemplateRestService.class);

	private final OlogyTemplateService<OlogyTemplate> service;
	private final ObjectMapper xmlObjectMapper;
	private final ObjectMapper jsonObjectMapper;
	private final HashMap<String, Class> viewMap;

	public TemplateRestService(OlogyTemplateService service, ObjectMapper xmlObjectMapper, ObjectMapper jsonObjectMapper, HashMap<String, Class> viewMap) {
		this.service = service;
		this.xmlObjectMapper = xmlObjectMapper;
		this.jsonObjectMapper = jsonObjectMapper;
		this.viewMap = viewMap;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@QueryParam("view") String view) {
		try {
			if(view == null){
				view = "default";
			}
			List<OlogyTemplate> results = service.findAll(viewMap.get(view));
			return buildResponse(results);
		} catch (DaoException ex) {
			LOG.error("Error retrieving all templates", ex);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Consumes(MediaType.TEXT_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(String json) {
		try {
			OlogyTemplate object = xmlObjectMapper.deserialise(json, OlogyTemplate.class);
                        OlogyTemplate previous = null;
                        String name = object.getName();
                        if (name!=null){
                            previous = service.findByName(name);
                        }
                        if (previous!=null){
                            object.setId(previous.getId());
                            object = service.update(object);
                        } else {
                            object = service.create(object);
                        }
			return buildResponse(object);
		} catch (DeserialiserException ex) {
			LOG.error("Error creating template", ex);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (DaoException ex) {
			LOG.error("Error creating template", ex);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") String id) {
		try {
			OlogyTemplate result = service.findById(id);
			if (result == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			return buildResponse(result);
		} catch (DaoException ex) {
			LOG.error("Error retrieving template by id", ex);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByName(@PathParam("name") String name) {
		try {
			OlogyTemplate result = service.findByName(name);
			if (result == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			return buildResponse(result);
		} catch (DaoException ex) {
			LOG.error("Error retrieving template by name", ex);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("/{id}")
	@Consumes(MediaType.TEXT_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") String id, String json) {
		try {
			OlogyTemplate object = xmlObjectMapper.deserialise(json, OlogyTemplate.class);
			object.setId(id);
			object = service.update(object);
			return buildResponse(object);
		} catch (DeserialiserException ex) {
			LOG.error("Error updating template", ex);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (DaoException ex) {
			LOG.error("Error updating template", ex);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("/name/{name}")
	@Consumes(MediaType.TEXT_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateByName(@PathParam("name") String name, String json) {
		try {
			OlogyTemplate previous = service.findByName(name);
			OlogyTemplate object = xmlObjectMapper.deserialise(json, OlogyTemplate.class);
			object.setId(previous.getId());
			object = service.update(object);
			return buildResponse(object);
		} catch (DeserialiserException ex) {
			LOG.error("Error updating template", ex);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (DaoException ex) {
			LOG.error("Error updating template", ex);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") String id) {
		try {
			OlogyTemplate result = service.findById(id);
			service.delete(result);
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (DaoException ex) {
			LOG.error("Error deleting template", ex);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DELETE
	@Path("/name/{name}")
	public Response deleteByName(@PathParam("name") String name) {
		try {
			OlogyTemplate result = service.findByName(name);
			service.delete(result);
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (DaoException ex) {
			LOG.error("Error deleting template", ex);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private Response buildResponse(Object entity) {
		try {
			return Response.ok(jsonObjectMapper.serialise(entity)).build();
		} catch (SerialiserException ex) {
			LOG.error("Error building response", ex);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
