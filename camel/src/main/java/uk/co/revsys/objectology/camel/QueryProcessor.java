package uk.co.revsys.objectology.camel;

import uk.co.revsys.esb.component.HttpProxyProcessor;

public class QueryProcessor extends HttpProxyProcessor {

    private String type;
    
    public QueryProcessor(String baseUrl) {
        super(baseUrl);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getHttpMethod() {
        return POST;
    }

    @Override
    public String getUrlPath() {
        return "/" + getType() + "/query";
    }

    @Override
    public String getContentType() {
        return APPLICATION_JSON;
    }

}
