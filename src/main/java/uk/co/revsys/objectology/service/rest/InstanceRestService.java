package uk.co.revsys.objectology.service.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.query.JSONQuery;
import uk.co.revsys.objectology.query.Query;
import uk.co.revsys.objectology.query.QueryImpl;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.view.ViewNotFoundException;

@Path("/")
public class InstanceRestService extends AbstractRestService{

	private final OlogyInstanceService<OlogyInstance> service;
	private final ObjectMapper xmlObjectMapper;

	public InstanceRestService(OlogyInstanceService service, ObjectMapper xmlObjectMapper, ObjectMapper jsonObjectMapper, HashMap<String, Class> viewMap) {
		super(jsonObjectMapper, viewMap);
		this.service = service;
		this.xmlObjectMapper = xmlObjectMapper;
	}

	@GET
	@Path("/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@PathParam("type") String type, @QueryParam("view") String viewName) {
		try {
			Class view = getView(viewName);
			List<OlogyInstance> results = service.findAll(type, view);
			return buildResponse(results);
		} catch (DaoException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (ViewNotFoundException ex) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@POST
	@Path("/{type}/query")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response query(@PathParam("type") String type, @QueryParam("view") String viewName, String json){
		try {
			Class view = getView(viewName);
			Query query = new QueryImpl(json);
			List<OlogyInstance> results = service.find(type, query, view);
			return buildResponse(results);
		} catch (DaoException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (ViewNotFoundException ex) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/{type}/query")
	@Produces(MediaType.APPLICATION_JSON)
	public Response query(@PathParam("type") String type, @QueryParam("view") String viewName, @Context UriInfo ui){
		MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
		queryParams.remove("view");
		if(queryParams.isEmpty()){
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		JSONQuery query = new JSONQuery();
		for(Entry<String, List<String>> queryParam: queryParams.entrySet()){
			query.put(queryParam.getKey(), queryParam.getValue().get(0));
		}
		try {
			Class view = getView(viewName);
			List<OlogyInstance> results = service.find(type, query, view);
			return buildResponse(results);
		} catch (DaoException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (ViewNotFoundException ex) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@POST
	@Path("/{type}")
	@Consumes(MediaType.TEXT_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createFromXML(String xml) {
		try {
			OlogyInstance object = xmlObjectMapper.deserialise(xml, OlogyInstance.class);
			object = service.create(object);
			return buildResponse(object);
		} catch (DeserialiserException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (DaoException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Path("/{type}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createFromJSON(String json) {
		try {
			OlogyInstance object = getJsonObjectMapper().deserialise(json, OlogyInstance.class);
			object = service.create(object);
			return buildResponse(object);
		} catch (DeserialiserException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (DaoException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/{type}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("type") String type, @PathParam("id") String id) {
		try {
			OlogyInstance result = service.findById(type, id);
			if(result==null){
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			return buildResponse(result);
		} catch (DaoException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("/{type}/{id}")
	@Consumes(MediaType.TEXT_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateFromXML(@PathParam("id") String id, String xml) {
		try {
			OlogyInstance object = xmlObjectMapper.deserialise(xml, OlogyInstance.class);
			object.setId(id);
			object = service.update(object);
			return buildResponse(object);
		} catch (DeserialiserException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (DaoException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Path("/{type}/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateFromJSON(@PathParam("id") String id, String json) {
		try {
			OlogyInstance object = getJsonObjectMapper().deserialise(json, OlogyInstance.class);
			object.setId(id);
			object = service.update(object);
			return buildResponse(object);
		} catch (DeserialiserException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (DaoException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DELETE
	@Path("/{type}/{id}")
	public Response delete(@PathParam("type") String type, @PathParam("id") String id) {
		try {
			OlogyInstance result = service.findById(type, id);
			service.delete(result);
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (DaoException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	

}
