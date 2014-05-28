package uk.co.revsys.objectology.serialiser.json;

import org.apache.commons.collections4.BidiMap;
import org.json.JSONObject;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class JSONCollectionTemplateDeserialiser extends JSONAttributeTemplateDeserialiser<CollectionTemplate> {

    public JSONCollectionTemplateDeserialiser(BidiMap<String, Class<? extends AttributeTemplate>> templateNatureMap) {
        super(templateNatureMap);
    }

    @Override
    public CollectionTemplate doDeserialiseJSON(CollectionTemplate object, ObjectMapper objectMapper, JSONObject json, Object... args) throws DeserialiserException {
        JSONObject memberJSON = json.getJSONObject("memberTemplate");
        String memberNature = memberJSON.getString("nature");
        JSONDeserialiser memberTemplateDeserialiser = (JSONDeserialiser) objectMapper.getDeserialiser(getTemplateNatureMap().get(memberNature));
        AttributeTemplate memberTemplate = (AttributeTemplate) memberTemplateDeserialiser.deserialiseJSON(objectMapper, memberJSON, args);
        object.setMemberTemplate(memberTemplate);
        return object;
    }

}
