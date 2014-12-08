package uk.co.revsys.objectology.camel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.camel.Exchange;
import uk.co.revsys.esb.component.HttpProxyProcessor;

public class QueryProcessor extends HttpProxyProcessor {

    private String type;
    private String view;
    
    public QueryProcessor(String baseUrl) {
        super(baseUrl);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    @Override
    public String getHttpMethod() {
        return POST;
    }

    @Override
    public String getUrlPath(Exchange exchange) {
        return "/" + getType() + "/query";
    }

    @Override
    public String getQueryString() {
        if(view!=null){
            try {
                return "view=" + URLEncoder.encode(view, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                return "view=" + view;
            }
        }else{
            return null;
        }
    }

    @Override
    public String getContentType() {
        return APPLICATION_JSON;
    }

}
