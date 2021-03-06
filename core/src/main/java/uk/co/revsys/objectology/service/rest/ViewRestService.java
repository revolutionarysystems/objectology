package uk.co.revsys.objectology.service.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.security.AuthorisationHandler;
import uk.co.revsys.objectology.service.ViewDefinitionLoader;
import uk.co.revsys.objectology.service.ViewService;
import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.view.definition.parser.ViewDefinitionParsingException;

@Path("/view")
public class ViewRestService extends AbstractRestService {

    private final ViewService service;
    private final ViewDefinitionLoader loader;

    public ViewRestService(ViewService service, ViewDefinitionLoader loader, ObjectMapper jsonObjectMapper, AuthorisationHandler authorisationHandler) {
        super(jsonObjectMapper, authorisationHandler);
        this.service = service;
        this.loader = loader;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(String json) {
        try {
            if(!isAdministrator()){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            ViewDefinition definition = loader.loadViewDefinition(json);
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
