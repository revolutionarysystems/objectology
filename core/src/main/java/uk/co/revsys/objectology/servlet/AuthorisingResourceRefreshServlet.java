package uk.co.revsys.objectology.servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import uk.co.revsys.objectology.security.AuthorisationHandler;
import uk.co.revsys.resource.repository.provider.servlet.ResourceRefreshServlet;

public class AuthorisingResourceRefreshServlet extends ResourceRefreshServlet{

    private AuthorisationHandler authorisationHandler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        authorisationHandler = applicationContext.getBean("authorisationHandler", AuthorisationHandler.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!authorisationHandler.isAdministrator()){
            resp.sendError(403);
        }else{
            super.doGet(req, resp);
        }
    }
    
}
