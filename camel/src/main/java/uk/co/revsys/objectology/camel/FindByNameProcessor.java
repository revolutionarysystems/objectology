package uk.co.revsys.objectology.camel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.camel.Exchange;
import uk.co.revsys.esb.component.HttpProxyProcessor;

public class FindByNameProcessor extends HttpProxyProcessor{

    private String type;
    private String name;
    
    public FindByNameProcessor(String baseUrl) {
        super(baseUrl);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getHttpMethod() {
        return GET;
    }

    @Override
    public String getUrlPath(Exchange exchange) {
        String encodedName = name;
        try{
            encodedName = URLEncoder.encode(name, "UTF-8");
        }catch(UnsupportedEncodingException ex){
            // ignore
        }
        return "/" + type + "/name/" + encodedName.replace("+", "%20");
    }

}
