package uk.co.revsys.objectology.service.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import uk.co.revsys.objectology.dao.AdminDao;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.security.AuthorisationHandler;

@Path("/admin")
public class AdminRestService extends AbstractRestService{

    private AdminDao adminDao;

    public AdminRestService(AdminDao adminDao, ObjectMapper jsonObjectMapper, AuthorisationHandler authorisationHandler) {
        super(jsonObjectMapper, authorisationHandler);
        this.adminDao = adminDao;
    }
    
    @Path("/clear")
    @GET
    public Response clear(){
        try {
            if(!isAdministrator()){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            adminDao.clear();
            return Response.ok().build();
        } catch (DaoException ex) {
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, ex);
        }
    }
    
}
