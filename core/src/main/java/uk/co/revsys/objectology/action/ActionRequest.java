package uk.co.revsys.objectology.action;

import java.util.HashMap;
import java.util.Map;

public class ActionRequest {

    private Map<String, String> parameters = new HashMap<String, String>();

    public ActionRequest() {
    }

    public ActionRequest(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
    
}
