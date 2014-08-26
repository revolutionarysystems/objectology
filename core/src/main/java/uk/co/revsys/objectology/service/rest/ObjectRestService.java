package uk.co.revsys.objectology.service.rest;

import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.security.AuthorisationHandler;
import uk.co.revsys.objectology.transform.path.PathEvaluator;

public abstract class ObjectRestService extends AbstractRestService{

    private PathEvaluator pathEvaluator;
    
    public ObjectRestService(ObjectMapper jsonObjectMapper, AuthorisationHandler authorisationHandler, PathEvaluator pathEvaluator) {
        super(jsonObjectMapper, authorisationHandler);
        this.pathEvaluator = pathEvaluator;
    }

    public PathEvaluator getPathEvaluator() {
        return pathEvaluator;
    }

}
