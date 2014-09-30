package uk.co.revsys.objectology.camel;

import uk.co.revsys.esb.component.HttpProxyComponent;
import java.util.Map;

import org.apache.camel.Processor;

public class ObjectologyComponent extends HttpProxyComponent{

    @Override
    protected void populateMappings(Map<String, Class<? extends Processor>> mappings) {
        mappings.put("create", CreateInstanceProcessor.class);
    }
    
}
