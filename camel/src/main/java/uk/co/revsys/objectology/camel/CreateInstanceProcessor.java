package uk.co.revsys.objectology.camel;

import org.apache.camel.Exchange;
import uk.co.revsys.esb.component.HttpProxyProcessor;

public class CreateInstanceProcessor extends HttpProxyProcessor {

    public CreateInstanceProcessor(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public String getHttpMethod() {
        return POST;
    }

    @Override
    public String getContentType() {
        return APPLICATION_JSON;
    }

    @Override
    public String getUrlPath(Exchange exchange) {
        return "/instance";
    }

}
