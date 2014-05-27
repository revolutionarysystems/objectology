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
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;
import uk.co.revsys.objectology.service.OlogyInstanceService;

@Path("/")
public class InstanceRestService {

	private final OlogyInstanceService<OlogyInstance> service;
	private final ObjectMapper xmlObjectMapper;
	private final ObjectMapper jsonObjectMapper;
	private final HashMap<String, Class> viewMap;

	public InstanceRestService(OlogyInstanceService service, ObjectMapper xmlObjectMapper, ObjectMapper jsonObjectMapper, HashMap<String, Class> viewMap) {
		this.service = service;
		this.xmlObjectMapper = xmlObjectMapper;
		this.jsonObjectMapper = jsonObjectMapper;
		this.viewMap = viewMap;
	}

	@GET
	@Path("/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@PathParam("type") String type, @QueryParam("view") String view) {
		try {
			if(view == null){
				view = "default";
			}
			List<OlogyInstance> results = service.findAll(type, viewMap.get(view));
			return buildResponse(results);
		} catch (DaoException ex) {
			ex.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/{type}/{property}/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findMatches(@PathParam("type") String type, @PathParam("property") String property, @PathParam("value") String value) {
		try {
			List<OlogyInstance> results = service.findMatches(type, property, value);
			return buildResponse(results);
		} catch (DaoException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
			OlogyInstance object = jsonObjectMapper.deserialise(json, OlogyInstance.class);
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
			OlogyInstance object = jsonObjectMapper.deserialise(json, OlogyInstance.class);
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

	private Response buildResponse(Object entity) {
		try {
			return Response.ok(jsonObjectMapper.serialise(entity)).build();
		} catch (SerialiserException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
