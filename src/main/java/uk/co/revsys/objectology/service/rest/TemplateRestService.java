package uk.co.revsys.objectology.service.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.query.JSONQuery;
import uk.co.revsys.objectology.query.Query;
import uk.co.revsys.objectology.query.QueryImpl;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.service.OlogyTemplateService;
import uk.co.revsys.objectology.service.TemplateLoader;
import uk.co.revsys.objectology.view.ViewNotFoundException;

@Path("/template")
public class TemplateRestService extends AbstractRestService {

	private static final Log LOG = LogFactory.getLog(TemplateRestService.class);

	private final OlogyTemplateService<OlogyTemplate> service;
	private final ObjectMapper xmlObjectMapper;
    private final TemplateLoader templateLoader;

	public TemplateRestService(OlogyTemplateService service, TemplateLoader templateLoader, ObjectMapper xmlObjectMapper, ObjectMapper jsonObjectMapper, HashMap<String, Class> viewMap) {
		super(jsonObjectMapper, viewMap);
		this.service = service;
		this.xmlObjectMapper = xmlObjectMapper;
        this.templateLoader = templateLoader;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@QueryParam("view") String viewName) {
		try {
			Class view = getView(viewName);
			List<OlogyTemplate> results = service.findAll(view);
			return buildResponse(results);
		} catch (DaoException ex) {
			LOG.error("Error retrieving all templates", ex);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (ViewNotFoundException ex) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@POST
	@Path("/query")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response query(@QueryParam("view") String viewName, String json){
		try {
			Class view = getView(viewName);
			Query query = new QueryImpl(json);
			List<OlogyTemplate> results = service.find(query, view);
			return buildResponse(results);
		} catch (DaoException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (ViewNotFoundException ex) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/query")
	@Produces(MediaType.APPLICATION_JSON)
	public Response query(@QueryParam("view") String viewName, @Context UriInfo ui){
		MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
		queryParams.remove("view");
		if(queryParams.isEmpty()){
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		JSONQuery query = new JSONQuery();
		for(Map.Entry<String, List<String>> queryParam: queryParams.entrySet()){
			query.put(queryParam.getKey(), queryParam.getValue().get(0));
		}
		try {
			Class view = getView(viewName);
			List<OlogyTemplate> results = service.find(query, view);
			return buildResponse(results);
		} catch (DaoException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (ViewNotFoundException ex) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Consumes(MediaType.TEXT_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(String xml) {
		try {
			OlogyTemplate object = templateLoader.loadTemplateFromXML(xml);
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
	public Response update(@PathParam("id") String id, String xml) {
		try {
			OlogyTemplate object = xmlObjectMapper.deserialise(xml, OlogyTemplate.class);
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
	public Response updateByName(@PathParam("name") String name, String xml) {
		try {
			OlogyTemplate previous = service.findByName(name);
			OlogyTemplate object = xmlObjectMapper.deserialise(xml, OlogyTemplate.class);
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
}
