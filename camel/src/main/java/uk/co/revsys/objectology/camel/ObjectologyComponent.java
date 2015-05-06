package uk.co.revsys.objectology.camel;

import uk.co.revsys.esb.component.HttpProxyComponent;
import java.util.Map;

import org.apache.camel.Processor;

public class ObjectologyComponent extends HttpProxyComponent{

    @Override
    protected void populateMappings(Map<String, Class<? extends Processor>> mappings) {
        mappings.put("findById", FindByIdProcessor.class);
        mappings.put("create", CreateInstanceProcessor.class);
        mappings.put("update", UpdateInstanceProcessor.class);
        mappings.put("delete", DeleteInstanceProcessor.class);
        mappings.put("query", QueryProcessor.class);
        mappings.put("action", InvokeActionProcessor.class);
        mappings.put("findByName", FindByNameProcessor.class);
        mappings.put("findAll", FindAllProcessor.class);
    }
    
}
