package uk.co.revsys.objectology.serialiser.json;

import java.util.Map.Entry;
import org.json.JSONObject;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class JSONOlogyInstanceSerialiser extends JSONAttributeSerialiser<OlogyInstance> {

    @Override
    public Object serialiseJSON(ObjectMapper objectMapper, OlogyInstance object, Object... args) throws SerialiserException {
        JSONObject json = new JSONObject();
        OlogyTemplate template = object.getTemplate();
        json.put("id", object.getId());
        json.put("template", template.getId());
        for (Entry<String, Attribute> attribute : object.getAllAttributes().entrySet()) {
            String attributeName = attribute.getKey();
            AttributeTemplate attributeTemplate = template.getAttributeTemplate(attributeName);
            if (attributeTemplate != null) {
                JSONAttributeSerialiser instanceSerialiser = (JSONAttributeSerialiser) objectMapper.getSerialiser(attributeTemplate.getAttributeType());
                json.put(attributeName, instanceSerialiser.serialiseJSON(objectMapper, attribute.getValue()));
            }
        }
        return json;
    }
}
