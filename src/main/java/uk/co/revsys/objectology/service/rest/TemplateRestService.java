package uk.co.revsys.objectology.service.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;
import uk.co.revsys.objectology.service.OlogyTemplateService;

@Path("/templates")
public class TemplateRestService {

	private final OlogyTemplateService<OlogyTemplate> service;
	private final ObjectMapper xmlObjectMapper;
	private final ObjectMapper jsonObjectMapper;

	public TemplateRestService(OlogyTemplateService service, ObjectMapper xmlObjectMapper, ObjectMapper jsonObjectMapper) {
		this.service = service;
		this.xmlObjectMapper = xmlObjectMapper;
		this.jsonObjectMapper = jsonObjectMapper;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		try {
			List<OlogyTemplate> results = service.findAll();
			return buildResponse(results);
		} catch (DaoException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Consumes(MediaType.TEXT_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(String json) {
		try {
			OlogyTemplate object = xmlObjectMapper.deserialise(json, OlogyTemplate.class);
			object = service.create(object);
			return buildResponse(object);
		} catch (DeserialiserException ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (DaoException ex) {
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
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (DaoException ex) {
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
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private Response buildResponse(Object entity) {
		try {
			return Response.ok(jsonObjectMapper.serialise(entity)).build();
		} catch (SerialiserException ex) {
			ex.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
