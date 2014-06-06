package uk.co.revsys.objectology.serialiser.json;

import org.apache.commons.collections4.BidiMap;
import org.json.JSONObject;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class JSONOlogyTemplateDeserialiser extends JSONAttributeTemplateDeserialiser<OlogyTemplate> {

    public JSONOlogyTemplateDeserialiser(BidiMap<String, Class<? extends AttributeTemplate>> templateNatureMap) {
        super(templateNatureMap);
    }

    @Override
    public OlogyTemplate doDeserialiseJSON(OlogyTemplate object, ObjectMapper objectMapper, JSONObject json, Object... args) throws DeserialiserException {
        System.out.println("json = " + json.toString());
        String id = json.optString("id");
        if (id != null && !id.isEmpty()) {
            object.setId(id);
        }
        String type = json.optString("type");
        if (type != null && !type.isEmpty()) {
            object.setType(type);
        }
        String name = json.optString("name");
        if (name != null && !name.isEmpty()) {
            object.setName(name);
        }
        JSONObject attributes = json.getJSONObject("attributes");
        for (Object attributeKey : attributes.keySet()) {
            String attributeName = (String) attributeKey;
            JSONObject attribute = attributes.getJSONObject(attributeName);
            JSONDeserialiser attributeDeserialiser = (JSONDeserialiser) objectMapper.getDeserialiser(getTemplateNatureMap().get(attribute.getString("nature")));
            object.getAttributeTemplates().put(attributeName, (AttributeTemplate) attributeDeserialiser.deserialiseJSON(objectMapper, attribute, args));
        }
        return object;
    }

}
