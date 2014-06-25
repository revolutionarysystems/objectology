package uk.co.revsys.objectology.service.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
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
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.query.JSONQuery;
import uk.co.revsys.objectology.query.Query;
import uk.co.revsys.objectology.query.QueryImpl;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.mapping.SerialiserException;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.view.ViewNotFoundException;

@Path("/")
public class InstanceRestService extends AbstractRestService {

    private static final Logger LOG = LoggerFactory.getLogger(InstanceRestService.class);
    
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
    public Response query(@PathParam("type") String type, @QueryParam("view") String viewName, String json) {
        try {
            Class view = getView(viewName);
            Query query = new QueryImpl(json);
            List<OlogyInstance> results = service.find(type, query, view);
            return buildResponse(results);
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
    public Response query(@PathParam("type") String type, @QueryParam("view") String viewName, @Context UriInfo ui) {
        MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        queryParams.remove("view");
        if (queryParams.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        JSONQuery query = new JSONQuery();
        for (Entry<String, List<String>> queryParam : queryParams.entrySet()) {
            query.put(queryParam.getKey(), queryParam.getValue().get(0));
        }
        try {
            Class view = getView(viewName);
            List<OlogyInstance> results = service.find(type, query, view);
            return buildResponse(results);
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
    public Response findById(@PathParam("type") String type, @PathParam("id") String id) {
        try {
            OlogyInstance result = service.findById(type, id);
            if (result == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return buildResponse(result);
        } catch (DaoException ex) {
            LOG.error("Error finding instance " + type + ":" + id, ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @POST
    @Path("/{type}/{id}")
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFromXML(@PathParam("type") String type, @PathParam("id") String id, String xml) {
        try {
            OlogyInstance existingObject = service.findById(type, id);
            Node existingObjectXML = DocumentHelper.parseText(xmlObjectMapper.serialise(existingObject)).getRootElement();
            Node newObjectXML = DocumentHelper.parseText(xml).getRootElement();
            Node combinedXML = mergeXML(existingObjectXML, newObjectXML);
            OlogyInstance object = xmlObjectMapper.deserialise(combinedXML.asXML(), OlogyInstance.class);
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
        } catch (DocumentException ex) {
            LOG.error("Error updating instance " + type + ":" + id, ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @POST
    @Path("/{type}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFromJSON(@PathParam("type") String type, @PathParam("id") String id, String json) {
        try {
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

    @DELETE
    @Path("/{type}/{id}")
    public Response delete(@PathParam("type") String type, @PathParam("id") String id) {
        try {
            OlogyInstance result = service.findById(type, id);
            service.delete(result);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (DaoException ex) {
            LOG.error("Error deleting instance " + type + ":" + id, ex);
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
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

    protected Node mergeXML(Node xml1, Node xml2) {
        List<Node> children = xml2.selectNodes("*");
        if (children.isEmpty()) {
            return xml2;
        }
        String action = null;
        Node actionNode = ((Element)xml2).attribute("action");
        if (actionNode != null) {
            action = actionNode.getText();
        }
        for (Node xml2Child : children) {
            String path = xml2Child.getName();
            Node xml1Child = xml1.selectSingleNode(path);
            if (xml1Child == null || (action!=null && action.equals("add"))) {
                xml2Child.detach();
                ((Element) xml1).add(xml2Child);
            } else {
                xml2Child.detach();
                xml1Child.detach();
                ((Element) xml1).add(mergeXML(xml1Child, xml2Child));

            }
        }
        return xml1;
    }

}
