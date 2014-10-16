package uk.co.revsys.objectology.camel;

import org.apache.camel.Exchange;
import uk.co.revsys.esb.component.HttpProxyProcessor;

public class InvokeActionProcessor extends HttpProxyProcessor{

    private String type;
    private String id;
    private String action;
    
    public InvokeActionProcessor(String baseUrl) {
        super(baseUrl);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String getHttpMethod() {
        return PUT;
    }

    @Override
    public String getUrlPath(Exchange exchange) {
        return "/" + getType() + "/" + getId() + "/action/" + getAction();
    }

    @Override
    public String getContentType() {
        return APPLICATION_FORM_URLENCODED;
    }

}
