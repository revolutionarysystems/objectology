package uk.co.revsys.objectology.service.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.query.JSONQuery;
import uk.co.revsys.objectology.query.Query;
import uk.co.revsys.objectology.query.QueryImpl;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.security.AuthorisationHandler;
import uk.co.revsys.objectology.service.OlogyTemplateService;
import uk.co.revsys.objectology.service.TemplateLoader;
import uk.co.revsys.objectology.view.ViewNotFoundException;

@Path("/template")
public class TemplateRestService extends AbstractRestService {

    private static final Logger LOG = LoggerFactory.getLogger(TemplateRestService.class);

    private final OlogyTemplateService<OlogyTemplate> service;
    private final ObjectMapper xmlObjectMapper;
    private final TemplateLoader templateLoader;

    public TemplateRestService(OlogyTemplateService service, TemplateLoader templateLoader, ObjectMapper xmlObjectMapper, ObjectMapper jsonObjectMapper, AuthorisationHandler authorisationHandler) {
        super(jsonObjectMapper, authorisationHandler);
        this.service = service;
        this.xmlObjectMapper = xmlObjectMapper;
        this.templateLoader = templateLoader;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            if (!isAdministrator()) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            List<OlogyTemplate> results = service.findAll();
            return buildResponse(results);
        } catch (DaoException ex) {
            LOG.error("Error retrieving all templates", ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @POST
    @Path("/query")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response query(String json) {
        try {
            if (!isAdministrator()) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            Query query = new QueryImpl(json);
            List<OlogyTemplate> results = service.find(query);
            return buildResponse(results);
        } catch (DaoException ex) {
            LOG.error("Error querying templates", ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response query(@QueryParam("view") String viewName, @Context UriInfo ui) {
        if (!isAdministrator()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        queryParams.remove("view");
        if (queryParams.isEmpty()) {
            return buildErrorResponse(Response.Status.BAD_REQUEST, "No query parameters specified");
        }
        JSONQuery query = new JSONQuery();
        for (Map.Entry<String, List<String>> queryParam : queryParams.entrySet()) {
            query.put(queryParam.getKey(), queryParam.getValue().get(0));
        }
        try {
            List<OlogyTemplate> results = service.find(query);
            return buildResponse(results);
        } catch (DaoException ex) {
            LOG.error("Error querying templates", ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @POST
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String xml) {
        try {
            if(!isAdministrator()){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            OlogyTemplate object = templateLoader.loadTemplateFromXML(xml);
            return buildResponse(object);
        } catch (DeserialiserException ex) {
            LOG.error("Error creating template", ex);
            return buildErrorResponse(Response.Status.BAD_REQUEST, ex);
        } catch (DaoException ex) {
            LOG.error("Error creating template", ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFromParameter(@FormParam("data") String xml) {
        return create(xml);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") String id) {
        try {
            if(!isAdministrator()){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            OlogyTemplate result = service.findById(id);
            if (result == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return buildResponse(result);
        } catch (DaoException ex) {
            LOG.error("Error retrieving template " + id, ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByName(@PathParam("name") String name) {
        try {
            if(!isAdministrator()){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            OlogyTemplate result = service.findByName(name);
            if (result == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return buildResponse(result);
        } catch (DaoException ex) {
            LOG.error("Error retrieving template by name", ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, String xml) {
        try {
            if(!isAdministrator()){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            OlogyTemplate object = xmlObjectMapper.deserialise(xml, OlogyTemplate.class);
            object.setId(id);
            object = service.update(object);
            return buildResponse(object);
        } catch (DeserialiserException ex) {
            LOG.error("Error updating template", ex);
            return buildErrorResponse(Response.Status.BAD_REQUEST, ex);
        } catch (DaoException ex) {
            LOG.error("Error updating template", ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @POST
    @Path("/name/{name}")
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateByName(@PathParam("name") String name, String xml) {
        try {
            if(!isAdministrator()){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            OlogyTemplate previous = service.findByName(name);
            OlogyTemplate object = xmlObjectMapper.deserialise(xml, OlogyTemplate.class);
            object.setId(previous.getId());
            object = service.update(object);
            return buildResponse(object);
        } catch (DeserialiserException ex) {
            LOG.error("Error updating template", ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        } catch (DaoException ex) {
            LOG.error("Error updating template", ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            if(!isAdministrator()){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            OlogyTemplate result = service.findById(id);
            service.delete(result);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (DaoException ex) {
            LOG.error("Error deleting template", ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @DELETE
    @Path("/name/{name}")
    public Response deleteByName(@PathParam("name") String name) {
        try {
            if(!isAdministrator()){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            OlogyTemplate result = service.findByName(name);
            service.delete(result);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (DaoException ex) {
            LOG.error("Error deleting template", ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
