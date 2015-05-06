package uk.co.revsys.objectology.camel;

import org.apache.camel.Exchange;
import uk.co.revsys.esb.component.HttpProxyProcessor;

public class DeleteInstanceProcessor extends HttpProxyProcessor {

    private String type;
    private String id;
    
    public DeleteInstanceProcessor(String baseUrl) {
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

    @Override
    public String getHttpMethod() {
        return DELETE;
    }

    @Override
    public String getUrlPath(Exchange exchange) {
        return "/" + type + "/" + id;
    }

}
