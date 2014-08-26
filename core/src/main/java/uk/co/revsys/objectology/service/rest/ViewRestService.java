package uk.co.revsys.objectology.service.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.dao.DuplicateKeyException;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.security.AuthorisationHandler;
import uk.co.revsys.objectology.service.ViewService;
import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.view.definition.parser.ViewDefinitionParser;
import uk.co.revsys.objectology.view.definition.parser.ViewDefinitionParsingException;

@Path("/view")
public class ViewRestService extends AbstractRestService {

    private final ViewService service;
    private final ViewDefinitionParser parser;

    public ViewRestService(ViewService service, ViewDefinitionParser parser, ObjectMapper jsonObjectMapper, AuthorisationHandler authorisationHandler) {
        super(jsonObjectMapper, authorisationHandler);
        this.service = service;
        this.parser = parser;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String json) {
        try {
            if(!isAdministrator()){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            ViewDefinition definition = parser.parse(json);
            service.create(definition);
            return Response.ok().build();
        }catch(DuplicateKeyException ex){
            return buildErrorResponse(Response.Status.CONFLICT, ex);
        }catch (DaoException ex) {
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        } catch (ViewDefinitionParsingException ex) {
            return buildErrorResponse(Response.Status.BAD_REQUEST, ex);
        }
    }
    
    @POST
    @Path("/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("name") String name, String json) {
        try {
            if(!isAdministrator()){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            ViewDefinition definition = parser.parse(json);
            service.update(definition);
            return Response.ok().build();
        } catch (DaoException ex) {
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        } catch (ViewDefinitionParsingException ex) {
            return buildErrorResponse(Response.Status.BAD_REQUEST, ex);
        }
    }
    
    @DELETE
    @Path("/{name}")
    public Response delete(@PathParam("name") String name){
        try {
            if(!isAdministrator()){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            ViewDefinition definition = service.findByName(name);
            service.delete(definition);
            return Response.noContent().build();
        } catch (DaoException ex) {
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }

}
