package uk.co.revsys.objectology.service.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.revsys.objectology.action.model.Action;
import uk.co.revsys.objectology.action.handler.ActionInvocationException;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.handler.ActionHandler;
import uk.co.revsys.objectology.action.handler.ActionHandlerFactory;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.query.JSONQuery;
import uk.co.revsys.objectology.query.Query;
import uk.co.revsys.objectology.query.QueryImpl;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.mapping.SerialiserException;
import uk.co.revsys.objectology.security.AuthorisationHandler;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.view.ViewNotFoundException;

@Path("/")
public class InstanceRestService extends AbstractRestService {

    private static final Logger LOG = LoggerFactory.getLogger(InstanceRestService.class);

    private final OlogyInstanceService<OlogyInstance> service;
    private final ObjectMapper xmlObjectMapper;
    private final ActionHandlerFactory actionHandlerFactory;

    public InstanceRestService(OlogyInstanceService service, ObjectMapper xmlObjectMapper, ObjectMapper jsonObjectMapper, HashMap<String, Class> viewMap, AuthorisationHandler authorisationHandler, ActionHandlerFactory actionHandlerFactory) {
        super(jsonObjectMapper, viewMap, authorisationHandler);
        this.service = service;
        this.xmlObjectMapper = xmlObjectMapper;
        this.actionHandlerFactory = actionHandlerFactory;
    }

    @GET
    @Path("/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@PathParam("type") String type, @QueryParam("view") String viewName, @QueryParam("depth") int depth) {
        try {
            if (!isAdministrator()) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            Class view = getView(viewName);
            List<OlogyInstance> results = service.findAll(type, view);
            return buildResponse(results, depth);
        } catch (DaoException ex) {
            LOG.error("Error retrieving all instances of type " + type, ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        } catch (ViewNotFoundException ex) {
            return buildErrorResponse(Response.Status.BAD_REQUEST, ex);
        }
    }

    @POST
    @Path("/{type}/query")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response query(@PathParam("type") String type, @QueryParam("view") String viewName, @QueryParam("depth") int depth, String json) {
        try {
            Class view = getView(viewName);
            Query query = new QueryImpl(json);
            List<OlogyInstance> results = service.find(type, query, view);
            if (!isAuthorisedToView(results)) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            return buildResponse(results, depth);
        } catch (DaoException ex) {
            LOG.error("Error querying instances of type " + type, ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        } catch (ViewNotFoundException ex) {
            LOG.error("Error querying instances of type " + type, ex);
            return buildErrorResponse(Response.Status.BAD_REQUEST, ex);
        }
    }

    @GET
    @Path("/{type}/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response query(@PathParam("type") String type, @QueryParam("view") String viewName, @QueryParam("depth") int depth, @Context UriInfo ui) {
        MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        queryParams.remove("view");
        queryParams.remove("depth");
        if (queryParams.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        JSONQuery query = new JSONQuery();
        for (Entry<String, List<String>> queryParam : queryParams.entrySet()) {
            query.put(queryParam.getKey(), queryParam.getValue().get(0).replace("+", " "));
        }
        try {
            Class view = getView(viewName);
            List<OlogyInstance> results = service.find(type, query, view);
            if (!isAuthorisedToView(results)) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            return buildResponse(results, depth);
        } catch (DaoException ex) {
            LOG.error("Error querying instances of type " + type, ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        } catch (ViewNotFoundException ex) {
            LOG.error("Error querying instances of type " + type, ex);
            return buildErrorResponse(Response.Status.BAD_REQUEST, ex);
        }
    }

    @POST
    @Path("/{type}")
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFromXML(String xml) {
        try {
            if (!isAdministrator()) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            OlogyInstance object = xmlObjectMapper.deserialise(xml, OlogyInstance.class);
            object = service.create(object);
            return buildResponse(object);
        } catch (DeserialiserException ex) {
            LOG.error("Error creating instance", ex);
            return buildErrorResponse(Response.Status.BAD_REQUEST, ex);
        } catch (DaoException ex) {
            LOG.error("Error creating instance", ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @POST
    @Path("/{type}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFromJSON(String json) {
        try {
            if (!isAdministrator()) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            OlogyInstance object = getJsonObjectMapper().deserialise(json, OlogyInstance.class);
            object = service.create(object);
            return buildResponse(object);
        } catch (DeserialiserException ex) {
            LOG.error("Error creating instance", ex);
            return buildErrorResponse(Response.Status.BAD_REQUEST, ex);
        } catch (DaoException ex) {
            LOG.error("Error creating instance", ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @GET
    @Path("/{type}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("type") String type, @PathParam("id") String id, @QueryParam("depth") int depth) {
        try {
            OlogyInstance result = service.findById(type, id);
            if (result == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            if (!isAuthorisedToView(result)) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            return buildResponse(result, depth);
        } catch (DaoException ex) {
            LOG.error("Error finding instance " + type + ":" + id, ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @POST
    @Path("/{type}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFromJSON(@PathParam("type") String type, @PathParam("id") String id, String json) {
        try {
            if (!isAdministrator()) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            OlogyInstance existingObject = service.findById(type, id);
            JSONObject existingObjectJSON = new JSONObject(getJsonObjectMapper().serialise(existingObject));
            JSONObject newObjectJSON = new JSONObject(json);
            JSONObject combinedJSON = mergeJSON(existingObjectJSON, newObjectJSON);
            OlogyInstance object = getJsonObjectMapper().deserialise(combinedJSON.toString(), OlogyInstance.class);
            object.setId(id);
            object = service.update(object);
            return buildResponse(object);
        } catch (DeserialiserException ex) {
            LOG.error("Error updating instance " + type + ":" + id, ex);
            return buildErrorResponse(Response.Status.BAD_REQUEST, ex);
        } catch (DaoException ex) {
            LOG.error("Error updating instance " + type + ":" + id, ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        } catch (SerialiserException ex) {
            LOG.error("Error updating instance " + type + ":" + id, ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    // TODO: Get this working as a POST. When using POST the formParameters map is empty
    @PUT
    @Path("/{type}/{id}/action/{action}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response invokeActionPut(@PathParam("type") String type, @PathParam("id") String id, @PathParam("action") String actionName, MultivaluedMap<String, String> formParameters) {
        try {
            OlogyInstance entity = service.findById(type, id);
            if (entity == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Action action = entity.getTemplate().getActions().get(actionName);
            if (action == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            if (!isAuthorisedToInvokeAction(entity, action)) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            ActionRequest request = new ActionRequest();
            Map<String, String> parameters = new HashMap<String, String>();
            for (String key : formParameters.keySet()) {
                System.out.println("key = " + key);
                String parameter = formParameters.getFirst(key);
                System.out.println("parameter = " + parameter);
                if (parameter != null) {
                    try {
                        parameter = URLDecoder.decode(parameter, "UTF-8");
                    } catch (UnsupportedEncodingException ex) {
                        LOG.error("UTF-8 encoding not supported", ex);
                        return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
                    }
                }
                System.out.println("parameter = " + parameter);
                parameters.put(key, parameter);
            }
            request.setParameters(parameters);
            ActionHandler actionHandler = actionHandlerFactory.getHandler(action);
            entity = actionHandler.invoke(entity, action, request);
            return buildResponse(entity);
        } catch (DaoException ex) {
            LOG.error("Error invoking action " + actionName + " on " + id, ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        } catch (ActionInvocationException ex) {
            LOG.error("Error invoking action " + actionName + " on " + id, ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @DELETE
    @Path("/{type}/{id}")
    public Response delete(@PathParam("type") String type, @PathParam("id") String id) {
        try {
            if (!isAdministrator()) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            OlogyInstance result = service.findById(type, id);
            service.delete(result);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (DaoException ex) {
            LOG.error("Error deleting instance " + type + ":" + id, ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private boolean isAuthorisedToView(OlogyInstance instance) {
        return getAuthorisationHandler().isAuthorised(instance, instance.getTemplate().getSecurityConstraints());
    }

    private boolean isAuthorisedToView(List<OlogyInstance> instances) {
        Iterator<OlogyInstance> iterator = instances.iterator();
        while (iterator.hasNext()) {
            OlogyInstance instance = iterator.next();
            if (!isAuthorisedToView(instance)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAuthorisedToInvokeAction(OlogyInstance instance, Action action) {
        return getAuthorisationHandler().isAuthorised(instance, action.getSecurityConstraints());
    }

    protected JSONObject mergeJSON(JSONObject json1, JSONObject json2) {
        for (Object key : json2.keySet()) {
            String keyString = (String) key;
            Object json2Part = json2.get(keyString);
            if (json2Part instanceof JSONObject) {
                Object json1Part = json1.get(keyString);
                JSONObject json2PartObject = (JSONObject) json2Part;
                if (json1Part instanceof JSONArray && json2PartObject.has("$add")) {
                    JSONArray json1PartArray = (JSONArray) json1Part;
                    JSONArray json2PartArray = json2PartObject.getJSONArray("$add");
                    for (int i = 0; i < json2PartArray.length(); i++) {
                        json1PartArray.put(json2PartArray.get(i));
                    }
                    json1.put(keyString, json1PartArray);
                } else {
                    json1.put(keyString, mergeJSON((JSONObject) json1Part, (JSONObject) json2Part));
                }
            } else {
                json1.put(keyString, json2Part);
            }
        }
        return json1;
    }

}
